package items;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import something.ItemSpawner;
import something.Game;

public class i_grass extends Item{
	private Image itemImg;
	public i_grass(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException
	{
		super(iGame, x, y, ispi);
		type = 2;
		itemImg = new Image("res/i/grass.png");
	}
	/*public void render(Graphics g)
	{
		itemImg.draw(position.x, position.y);
	}*/

}
