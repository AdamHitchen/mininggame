package tiles;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_Wood extends Tile{
	public t_Wood(float x, float y, int row, ItemSpawner isp) throws SlickException {
		super(x, y, row, isp);
		type = 6;
		tileStrength = 4;
	}
}

