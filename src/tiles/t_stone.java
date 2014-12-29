package tiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import something.ItemSpawner;

public class t_stone extends Tile {
	private float alpha;
	private Rectangle rekt;
	public t_stone(float x, float y, int row, ItemSpawner isp) throws SlickException
	{
		super(x, y, row, isp);
		type = 3;
		rekt = new Rectangle(x, y, 32, 32);
		tileStrength = 3;
	}
/*	public void render(GameContainer gc, Graphics g)
	{
		//tileImage.setAlpha(alpha);
		//tileImage.draw(position.x, position.y);
	//	g.setColor(color);
		//g.drawString("0",position.x,position.y);
	//	tileImage.setImageColor(3, 50, 150,alpha);
		//tileImage.draw(position.x,position.y,color);
		//g.drawImage(tileImage, position.x, position.y, new Color(55, 14, 255));
		g.setDrawMode(g.MODE_NORMAL);
		
		tileImage.draw(position.x-isp.getGame().cam.camPosX(),position.y-isp.getGame().cam.camPosY());


	}	
	public void setAlpha(float a)
	{
		alpha = a;
	}*/
}
