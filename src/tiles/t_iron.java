package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_iron extends Tile {
    public t_iron(float f, float g, int row, ItemSpawner isp) throws SlickException {
        super(f, g, row, isp);
        type = 4;
        //tileImage = new Image(image);
        tileStrength = 7;
    }

    public void update(GameContainer gc, int arg1) {

    }
}
	