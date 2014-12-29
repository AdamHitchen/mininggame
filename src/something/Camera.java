package something;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;

public class Camera {
	private final int width = 1024;
	private final int height = 720;
	private Vector2f camPos;
	private int offsetMaxX = 100 * 32 - width;
	private int offsetMaxY = 100 * 32 - height;
	private final int offsetMinX = 0;
	private final int offsetMinY = 0;
	private Game game;
	public Camera(Game mGame)
	{
		game = mGame;
		 camPos = new Vector2f(0,0);
		
		
	}
	public float camPosX()
	{
		return camPos.x;
	}
	public float camPosY()
	{
		return camPos.y;
	}
	public void setMaxX()
	{
		 offsetMaxX = game.maxX * game.tileSize - width;
	}
	public void setMaxY()
	{
		 offsetMaxY = game.getMaxY()* game.tileSize- height;
	}
	
	public void update()
	{
		Player player = (Player) game.players.get(0);
		camPos.x = player.getPosition().x - width / 2;
		camPos.y = player.getPosition().y - height / 2;
		if(camPos.x > offsetMaxX - 256)
		{
			camPos.x = offsetMaxX - 256;
		}
		else if(camPos.x < offsetMinX)
		{
			camPos.x = offsetMinX;
		}
		
		if(camPos.y > offsetMaxY)
		{
			camPos.y = offsetMaxY;
		}
		else if(camPos.y < offsetMinY+game.tileSize)
		{
			camPos.y = offsetMinY+game.tileSize;
		}
	}
}
