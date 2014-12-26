package items;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import something.ItemSpawner;
import something.Game;

public class i_stone extends Item{
	public String image = "res/i/stone.png";

	public i_stone(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException
	{
		super(iGame, x, y, ispi);
		itemImg = new Image("res/i/stone.png");
		type = 3;
		
	}



}
