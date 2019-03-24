package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_grass extends Tile {

    public t_grass(float x, float y, int row, ItemSpawner isp) throws SlickException {
        super(x, y, row, isp);

        type = 2;
        tileStrength = 0;
    }

    public void render(GameContainer gc, Graphics g) {
        if (loaded) {
            //tileImage.draw(position.x-isp.getGame().cam.camPosX(),position.y-5-isp.getGame().cam.camPosY());
            isp.getGame().terrain.returnImage(type).draw(position.x - isp.getGame().cam.camPosX(), position.y - 5 - isp.getGame().cam.camPosY());

        }
    }

    public int returnHeight() {
        return isp.getGame().terrain.returnImage(type).getHeight() - 5;
    }
}
