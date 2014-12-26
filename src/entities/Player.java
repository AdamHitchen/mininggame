package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import something.Game;
import tiles.Tile;

public class Player {
	private Vector2f position;
	private Vector2f velocity;
	private boolean dead = false, jumping = false, initJ = true;
	public Image tarpeyFace;
	private int width, height;
	private float offset;
	private boolean falling;
	private float tempGravityBoost;
	private float maxDownwardsVelocity = 0.7f;
	private float gravity = 0.3f, jumpVelocity = 0f;
	float speed;
	private Game game;
	private boolean movingUp, movingDown, movingLeft, movingRight;
	private boolean facingLeft;
	public Player(float x, float y, Game gameWorld) throws SlickException 
	{
		game = gameWorld;
		this.position = new Vector2f(x,y);
		velocity = new Vector2f(0,0);
		tarpeyFace = new Image("res/player.png");
		width = tarpeyFace.getWidth();
		height = tarpeyFace.getHeight() - 1;
		speed = 0.3f;
	}
	public void render(Graphics g)
	{
		tarpeyFace.getFlippedCopy(facingLeft,false).draw(position.x - game.cam.camPosX(), position.y - game.cam.camPosY());
	}
	public void setMovingUp(boolean move)
	{
		movingUp = move;
	}
	public float nextGravityPos(int arg1)
	{
		
		float newPosY;
		if(falling && tempGravityBoost < maxDownwardsVelocity)
		{
			tempGravityBoost += 0.01f * arg1;
		}
		else if (!falling)
		{
			falling = true;
			tempGravityBoost = 0;
		}
		
		
		newPosY = (gravity * arg1 + tempGravityBoost);
		return newPosY;
	}
	public void setNextPos()
	{
		float nextXpos = position.x + velocity.x;
		float nextYpos = position.y + velocity.y;
		boolean canMoveX = true, canMoveY = true;
		for(int i = 0; i < game.terrain.getSize(); i++)
		{
			
			Tile tile = (Tile) game.terrain.getWorld(i);
			/*	if((nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + 32 && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y+ 32)
						|| (nextXpos + 28>= tile.getPos().x  && nextXpos + 28 <= tile.getPos().x + 32 && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y + 32)
						||	(nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + 32 && nextYpos+32 >= tile.getPos().y && nextYpos+32 <= tile.getPos().y +32)
						|| (nextXpos + 28>= tile.getPos().x  && nextXpos + 28 <= tile.getPos().x + 32 && nextYpos+32 >= tile.getPos().y && nextYpos+32 <= tile.getPos().y + 32) )
				{
					canMoveY = false;
					canMoveX = false;
					Player playa = (Player) game.players.get(0);
					velocity.x = 0;
					velocity.y = 0;
				}*/
			
				//check to see if the next position would land the player inside of an object
				if((		nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + tile.returnWidth() && position.y >= tile.getPos().y && position.y <= tile.getPos().y+ tile.returnHeight())
						|| (nextXpos + width>= tile.getPos().x  && nextXpos + width <= tile.getPos().x + tile.returnWidth() && position.y >= tile.getPos().y && position.y <= tile.getPos().y + tile.returnHeight())
						||	(nextXpos >= tile.getPos().x && nextXpos <= tile.getPos().x + tile.returnWidth() && position.y+height >= tile.getPos().y && position.y+height <= tile.getPos().y +tile.returnHeight())
						|| (nextXpos + width>= tile.getPos().x  && nextXpos + width <= tile.getPos().x + tile.returnHeight() && position.y+height >= tile.getPos().y && position.y+height <= tile.getPos().y + tile.returnHeight())
						|| (tile.getPos().x >= nextXpos && tile.getPos().x <= nextXpos + width && tile.getPos().y >= position.y && tile.getPos().y <= position.y + height)
						|| (tile.getPos().x + tile.returnWidth() >= nextXpos && tile.getPos().x + tile.returnWidth() <= nextXpos + width && tile.getPos().y >= position.y && tile.getPos().y <= position.y + height)
						|| (tile.getPos().x >= nextXpos && tile.getPos().x <= nextXpos + width && tile.getPos().y + tile.returnWidth() >= position.y && tile.getPos().y + tile.returnWidth() <= position.y + height)
						|| (tile.getPos().x + tile.returnWidth() >= nextXpos && tile.getPos().x + tile.returnWidth() <= nextXpos + width && tile.getPos().y + tile.returnWidth() >= position.y && tile.getPos().y + tile.returnWidth() <= position.y + height))
				{
					canMoveX = false;

					if(velocity.x > 0) //if the next position would land the player inside of an object, move the player to the closest possible position
					{
						position.x += tile.getPos().x - (position.x + width)- 0.05f; 
					}
					else if(velocity.x < 0)
					{
						position.x += tile.getPos().x + tile.tileImage().getWidth() - (position.x) + 0.05f;
					}
					velocity.x = 0;
				}
			
				if((position.x >= tile.getPos().x && position.x <= tile.getPos().x + tile.returnWidth() && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y+ tile.returnHeight())
						|| (position.x + width>= tile.getPos().x  && position.x + width <= tile.getPos().x + tile.returnWidth() && nextYpos >= tile.getPos().y && nextYpos <= tile.getPos().y + tile.returnHeight())
						||	(position.x >= tile.getPos().x && position.x <= tile.getPos().x + tile.returnWidth() && nextYpos+height >= tile.getPos().y && nextYpos+height <= tile.getPos().y +tile.returnHeight())
						|| (position.x + width>= tile.getPos().x  && position.x + width <= tile.getPos().x + tile.returnWidth() && nextYpos+height >= tile.getPos().y && nextYpos+height <= tile.getPos().y + tile.returnHeight()) )
				{
					canMoveY = false;
					jumping = false;
					falling = false;
					if(velocity.y > 0)
					{
						position.y += tile.getPos().y - (position.y + height) - 0.05f;
					}
					/*else if(velocity.y < 0)
					{
						position.y += (tile.getPos().x + 32) - (position.x) + 0.0001f;  
					}*/
					velocity.y = 0;
				}
				if((position.x >= tile.getPos().x && position.x <= tile.getPos().x + tile.tileImage().getWidth() && nextYpos+height >= tile.getPos().y && nextYpos+height <= tile.getPos().y + tile.tileImage().getHeight())
				|| (position.x + width>= tile.getPos().x  && position.x + width <= tile.getPos().x + tile.tileImage().getWidth() && nextYpos+height >= tile.getPos().y && nextYpos+height <= tile.getPos().y + tile.tileImage().getHeight()))
				
				{
					initJ = true;
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
	
	public void gravity(int arg1)
	{
		velocity.y = nextGravityPos(arg1);
	}
	public void canJump()
	{
		initJ= true;
	}

	public void update(GameContainer gc, int arg0)
	{
		if(position.y > game.getMaxY()* game.tileSize)
		{
			position.y = 1;
		}
		movingUp = gc.getInput().isKeyDown(Input.KEY_W);
		movingRight = gc.getInput().isKeyDown(Input.KEY_D);
		movingLeft = gc.getInput().isKeyDown(Input.KEY_A);
		movingDown = gc.getInput().isKeyDown(Input.KEY_S);
		
		if(movingUp)
		{
			if(initJ)
			{
				jumping= true;
			}

		}
		if(jumping && initJ)
		{
			jumpVelocity = -1f;
			initJ = false;
			
		}
		if(jumping)
		{
			
			velocity.y += jumpVelocity * arg0;
			jumpVelocity +=0.002f * arg0;
			if(jumpVelocity >= 0)
			{
				jumping = false;
			}
		}
		if (movingDown)
		{
			velocity.y += speed*arg0;
		}
		if(movingLeft)
		{
			velocity.x -= speed*arg0;
			facingLeft = true;
		}
		if(movingRight)
		{
			velocity.x+=speed*arg0;
			facingLeft = false;
		}		
		setNextPos();
		//position.x+= velocity.x;
		//position.y+= velocity.y;
		velocity.x = 0;
		velocity.y = 0;
	}
	
	public void rotateTarpey(float rot)
	{
		tarpeyFace.rotate(rot);
	}
	public void setOffset(float in)
	{
		offset = in;
	}
	public float getOffset()
	{
		return offset;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean d) {
		dead = d;
	}
}
