package items;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;
import something.ItemSpawner;
import something.Game;
import tiles.Tile;

public class Item
{
	protected Image itemImg;
	private int itemID;
	protected String image = "res/i/grass.png";
	private boolean pickedUp = false;
	private float speed = 0.25f;
	private float gravity = 0.3f;
	protected Vector2f position;
	protected int type;
	protected Vector2f velocity;
	private int pickupArea = 48;
	private boolean loaded;
	private Game game;
	protected int Strength;
	ItemSpawner isp;
	public Item(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException
	{
	
		isp = ispi;
		game = iGame;
		velocity = new Vector2f(0,0);
		position = new Vector2f(x,y);
		itemImg = new Image(image);

	}
	public void init() throws SlickException
	{
		
	}
	

	
	public int getID()
	{
		return itemID;
	}
	
	public void setNextPos()
	{
		float nextXpos = position.x + velocity.x;
		float nextYpos = position.y + velocity.y;
		boolean canMoveX = true, canMoveY = true;
		
		for(int i = 0; i < game.terrain.getSize(); i++)
		{
			Tile tile = (Tile) game.terrain.getWorld(i);

			/*	if((nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + game.tileSize && position.y >= tile.getPos().y && position.y <= tile.getPos().y+ game.tileSize)
						|| (nextXpos + 28>= tile.getPos().x  && nextXpos + 28 <= tile.getPos().x + game.tileSize && position.y >= tile.getPos().y && position.y <= tile.getPos().y + game.tileSize)
						||	(nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + game.tileSize && position.y+game.tileSize >= tile.getPos().y && position.y+game.tileSize <= tile.getPos().y +game.tileSize)
						|| (nextXpos + 28>= tile.getPos().x  && nextXpos + 28 <= tile.getPos().x + game.tileSize && position.y+game.tileSize >= tile.getPos().y && position.y+game.tileSize <= tile.getPos().y + game.tileSize) )
				{
					canMoveX = false;
					velocity.x = 0;
				}*/

				if((position.x >= tile.getPos().x && position.x <= tile.getPos().x + game.tileSize && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y+ game.tileSize)
						|| (position.x + 12>= tile.getPos().x  && position.x + 12 <= tile.getPos().x + game.tileSize && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y + game.tileSize)
						||	(position.x >= tile.getPos().x && position.x <= tile.getPos().x + game.tileSize && nextYpos+12 >= tile.getPos().y && nextYpos+12 <= tile.getPos().y +game.tileSize)
						|| (position.x + 12>= tile.getPos().x  && position.x + 12 <= tile.getPos().x + game.tileSize && nextYpos+12 >= tile.getPos().y && nextYpos+12 <= tile.getPos().y + game.tileSize) )
				{
					canMoveY = false;
					velocity.y = 0;
				}


		}
		if(canMoveX)
		{
			position.x = nextXpos;

		}
		if(canMoveY)
		{
			position.y = nextYpos;
		}
	}
		

	public void moveToPlayer(int arg1)
	{
		Player player = (Player) game.players.get(0);
		//player.getPosition().x;
	    	position = new Vector2f(position.x,position.y);
	        
			Double angle = Math.atan2((player.getPosition().y + 16 - position.y), (player.getPosition().x + 8 - position.x));
			velocity.x = (float) (this.speed*Math.cos(angle));
			velocity.y = (float) (this.speed*Math.sin(angle));
			position.x = position.x + velocity.x*arg1;
			position.y = position.y + velocity.y*arg1;

	}
	
	public float nextGravityPos(int arg1)
	{
		float newPosY = gravity * arg1;
		return newPosY;
	}
	public void gravity(int arg1)
	{
		velocity.y = nextGravityPos(arg1);
	}
	public void die()
	{
		if(game.inv.haveSpace(type))
		{
			game.inv.addItem(type);
		}
		isp.destroyItem(this, itemID);
		
	}
	public void setID(int id)
	{
		itemID = id;
	}
	public void update(GameContainer gc, int arg1)
	{
		if(position.x > isp.getGame().cam.camPosX() - 64 && position.x < isp.getGame().cam.camPosX() + isp.getGame().getWidth() + 64 && 
				position.y > isp.getGame().cam.camPosY() - 64 && position.y < isp.getGame().cam.camPosY() + isp.getGame().getHeight())
		{
			loaded = true;
		}
		else
		{
			loaded = false;
		}
		if(loaded)
		{
			Player player = (Player) game.players.get(0);
			if(position.x > player.getPosition().x - pickupArea && position.x < player.getPosition().x + game.tileSize + pickupArea && 
					position.y > player.getPosition().y - pickupArea && position.y < player.getPosition().y + 28 + pickupArea)
			{
				pickedUp=true;
			}
			
			if(!pickedUp)
			{
				gravity(arg1);
				setNextPos();
			}
			else
			{
				moveToPlayer(arg1);
			}
			if(position.x > player.getPosition().x && position.x < player.getPosition().x + game.tileSize && 
					position.y > player.getPosition().y && position.y < player.getPosition().y + 28)
			{
				die();
			}
		}
	}
	public void render(Graphics g) 
	{
		if(loaded)
		{
			itemImg.draw(position.x-isp.getGame().cam.camPosX(),position.y-isp.getGame().cam.camPosY());
		}
	}
	
	
}