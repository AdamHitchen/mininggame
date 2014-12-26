package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import something.ItemSpawner;
import something.Game;

public class t_dirt extends Tile{
	private String image = "res/tiles/t_dirt.png";
	protected Image tileImage;
	
	public t_dirt(float f, float g, int row, ItemSpawner isp) throws SlickException {
		super(f, g, row, isp);

		type = 1;
		tileImage = new Image(image);
		tileStrength = 0;
	}


	public void update(GameContainer gc, int arg1) throws SlickException
	{
		if(position.x > isp.getGame().cam.camPosX() - 64 && position.x < isp.getGame().cam.camPosX() + isp.getGame().getWidth() + 64 && 
		position.y > isp.getGame().cam.camPosY() - 64 && position.y < isp.getGame().cam.camPosY() + isp.getGame().getHeight() + 64)
		{
			loaded = true;
		}
		else
		{
			loaded = false;
		}
		if(loaded)
		{
			int i = isp.getGame().random.nextInt(100);
			if(i == 1)
			{
				int xloc =  (int) (position.x - position.x) % 32;
				int yloc =  (int) (position.y - position.y) % 32;
				int x = (int) xloc / 32;
				int y = (int) (((isp.getGame().maxX*32) - yloc) / 32);
				isp.getGame().terrain.replaceTile(this, 2, x, y);
			}
		}

	}

}
	