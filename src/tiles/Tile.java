package tiles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import something.ItemSpawner;

public class Tile {
	private int Row;
	protected Vector2f position;
	protected ItemSpawner isp;

	protected Image tileImage;
	protected boolean loaded;
	protected int tileStrength;
	protected int type;
	public Tile(float x, float y, int row, ItemSpawner isps) throws SlickException
	{
		isp = isps;
		Row = row;
		//tileImage = new Image("res/t_grass");
		position = new Vector2f(0,0);
		position.x = x;
		position.y = y;

	}
	public Image tileImage()
	{
		return isp.getGame().terrain.returnImage(type);
	}
	public int returnHeight()
	{
		return isp.getGame().terrain.returnImage(type).getHeight();
	}
	public int returnWidth()
	{
		
		 return isp.getGame().terrain.returnImage(type).getWidth();

	}
	public void init(GameContainer gc) throws SlickException
	{
		
	}
	public void render(GameContainer gc, Graphics g)
	{
		if(loaded)
		{
			//tileImage.draw(position.x-isp.getGame().cam.camPosX(),position.y-isp.getGame().cam.camPosY());
			isp.getGame().terrain.returnImage(type).draw(position.x-isp.getGame().cam.camPosX(),position.y-isp.getGame().cam.camPosY());
		}
	}
	public int returnStrength()
	{
		return tileStrength;
	}
	public void update(GameContainer gc)
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
	}
	public int getType()
	{
		return type;
	}
	public boolean getLoaded()
	{
		return loaded;
	}
	public Vector2f getPos()
	{
		return position;
	}
	public int returnRow()
	{
		return Row;
	}
	public void drop() throws SlickException
	{
		isp.spawnItem(type,position.x + 16, position.y);
	}
}
