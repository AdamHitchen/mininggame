package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import something.ItemSpawner;
import something.Game;

public class t_iron extends Tile{
	public String image = "res/tiles/t_iron.png";	
	public t_iron(float f, float g, int row, ItemSpawner isp) throws SlickException {
		super(f, g, row, isp);

		type = 4;
		tileImage = new Image(image);
		tileStrength = 7;
	}
	
	public void update(GameContainer gc, int arg1)
	{
		
	}
}
	