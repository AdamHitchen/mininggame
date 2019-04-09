package tiles;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_Workbench extends Tile {
    public String image = "res/tiles/t_Workbench.png";

    public t_Workbench(float x, float y, int row, ItemSpawner isp) throws SlickException {
        super(x, y, row, isp);
        tileImage = new Image(image);
        type = 9;
        tileStrength = 5;
    }
}

