package tiles;

import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_silver extends Tile {
    public t_silver(float x, float y, int row, ItemSpawner isp) throws SlickException {
        super(x, y, row, isp);
        type = 5;
        tileStrength = 7;
    }
}

