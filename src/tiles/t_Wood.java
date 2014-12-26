package tiles;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;

public class t_Wood extends Tile{
	public String image = "res/tiles/t_Wood.png";
	public t_Wood(float x, float y, int row, ItemSpawner isp) throws SlickException {
		super(x, y, row, isp);
		tileImage = new Image(image);
		type = 6;
		tileStrength = 4;
	}
}

