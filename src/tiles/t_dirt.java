package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_dirt extends Tile {

    public t_dirt(float f, float g, int row, ItemSpawner isp) throws SlickException {
        super(f, g, row, isp);

        type = 1;
        tileStrength = 0;
    }


    public void update(GameContainer gc, int arg1) throws SlickException {
        loaded = position.x > isp.getGame().cam.camPosX() - 64 && position.x < isp.getGame().cam.camPosX() + isp.getGame().getWidth() + 64 &&
                position.y > isp.getGame().cam.camPosY() - 64 && position.y < isp.getGame().cam.camPosY() + isp.getGame().getHeight() + 64;
        if (loaded) {
            int i = isp.getGame().random.nextInt(100);
            if (i == 1) {
                int xloc = (int) (position.x - position.x) % 32;
                int yloc = (int) (position.y - position.y) % 32;
                int x = xloc / 32;
                int y = (((isp.getGame().terrain.returnMaxX() * 32) - yloc) / 32);
                isp.getGame().terrain.replaceTile(this, 2, x, y);
            }
        }

    }

}
	