package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import something.Chunk;
import something.Game;
import something.TerrainGenerator;
import tiles.Tile;

public class Player {
	private Vector2f position;
	private Vector2f velocity;
	private boolean dead = false, jumping = false, initJ = true;
	public Image playerImage;
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
	Animation anim;
	TerrainGenerator terrain;
	int animtim;
	SpriteSheet sprites;
	Image[] frames;
	public Player(float x, float y, Game gameWorld, TerrainGenerator terrain) throws SlickException 
	{
		game = gameWorld;
		this.position = new Vector2f(x,y);
		velocity = new Vector2f(0,0);
		playerImage = new Image("res/player.png");
		width = playerImage.getWidth();
		height = playerImage.getHeight() - 2;
		speed = 0.3f;
		this.terrain = terrain;
	/*	frames = new Image[9];
		frames[0] = new Image("");
		frames[1] = new Image("");
		frames[2] = new Image("");
		frames[3] = new Image("");
		frames[4] = new Image("");
		frames[5] = new Image("");
		frames[6] = new Image("");
		frames[7] = new Image("");
		frames[8] = new Image("");
		anim = new Animation(frames, 5);*/
		sprites = new SpriteSheet("res/sprites/player_walk.png",32,64);
		
	}
	public void render(Graphics g)
	{
		g.drawString(""+position.x + ", " + position.y, 100, 100);
/*
		if(animtim < 5)
		{
			sprites.getFlippedCopy(facingLeft,false).getSubImage(1, 1,32,64).draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
		}
		else if(animtim < 10)
		{
			sprites.getFlippedCopy(facingLeft,false).getSubImage(2, 1,32,64).draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
		}
		else if (animtim<15)
		{
			sprites.getFlippedCopy(facingLeft,false).getSubImage(3, 1,32,64).draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
			animtim = 0;
		}
			animtim ++;*///FIX THIS
		
		
		playerImage.draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY()-2);
	}
	public void setMovingUp(boolean move)
	{
		movingUp = move;
	}
	public int returnWidth()
	{
		return width;
	}
	public int returnHeight()
	{
		return height;
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
		Chunk[] chunk = game.terrain.returnChunks();
		int toIterateFrom = 0;
		int toIterateTo = 0;

		int currentChunk = 0;
		int chunkSize = game.terrain.returnChunkSize();
		int tileSize = game.terrain.returnTileSize();
		int tempInt = (int)((position.x - position.x % (chunkSize * tileSize)) / (chunkSize * tileSize));
		for(int i = 0; i < game.terrain.returnChunks().length; i++)
		{
			if(tempInt == chunk[i].returnID() )
			{
				currentChunk = i;
			}
		}
		if(currentChunk == 0)
		{
			toIterateFrom = 0;
			toIterateTo = 2;
		}
		else if(currentChunk == 4)
		{
			toIterateFrom = 2;
			toIterateTo = 4;
		}
		else if(position.x < (tempInt)*game.terrain.returnChunkSize()*game.terrain.returnTileSize() +  ((tempInt)*game.terrain.returnChunkSize()*game.terrain.returnTileSize()) / 2)
		{
			toIterateFrom = currentChunk - 1;
			toIterateTo = currentChunk+1;
		}
		else
		{
			toIterateFrom = currentChunk-1;
			toIterateTo = currentChunk+1;
		}
		//int c = (int) ( position.x - (position.x % (40 * 16))) / (40 * 16);

		//System.out.println("c = " + c);
		
			for(int c = toIterateFrom; c < toIterateTo; c++)
			{
				Tile[][] tiles = chunk[c].getTiles();
				for(int i = 0; i <= terrain.returnChunkSize()-1 ; i++)
				{
					for(int y = 0; y <= terrain.returnMaxY()-1; y++)
					{
						Tile tile = tiles[i][y];
				
						if(tile != null && game.calcDistance(position.x, position.y, tile.getPos().x, tile.getPos().y) < 160)//Check if the object is within 160 pixels of the player for efficiency. expensive operation. would like to remove.
						{
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
					}
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
/*		if(position.y > 2000)// game.getMaxY()* game.tileSize)
		{
			position.y = 1;
		}*/
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
		
		if(position.y > 2000)
		{
			position.y = 0;
		}

		setNextPos();
		//position.x+= velocity.x;
		//position.y+= velocity.y;

		velocity.x = 0;
		velocity.y = 0;
	}
	
	public void rotateTarpey(float rot)
	{
		playerImage.rotate(rot);
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
	
	
/*    public boolean LinesIntersect(float x1, float y1, Tile tile)
    {
        Vector2f A, B, C, D;
        
        A = new Vector2f(position.x,position.y);
        B = new Vector2f(x1,y1);
		Vector2f r = new Vector2f(B.x - A.x, B.y - A.y);

        C = new Vector2f(tile.getPos().x,tile.getPos().y);
        D = new Vector2f(tile.getPos().x+tile.returnWidth, tile.getPos().y);
        return LinesIntersect(A, B, C, D, r);
        
    }
 
    // Determines if the lines AB and CD intersect.
	static boolean LinesIntersect(Vector2f A, Vector2f B, Vector2f C, Vector2f D, Vector2f r)
	{
		Vector2f CmP = new Vector2f(C.x - A.x, C.y - A.y);
		Vector2f s = new Vector2f(D.x - C.x, D.y - C.y);
 
		float CmPxr = CmP.x * r.y - CmP.y * r.x;
		float CmPxs = CmP.x * s.y - CmP.y * s.x;
		float rxs = r.x * s.y - r.y * s.x;
 
		if (CmPxr == 0f)
		{
			// Lines are collinear, and so intersect if they have any overlap
 
			return ((C.x - A.x < 0f) != (C.x - B.x < 0f))
				|| ((C.x - A.x < 0f) != (C.x - B.x < 0f));
		}
 
		if (rxs == 0f)
			return false; // Lines are parallel.
 
		float rxsr = 1f / rxs;
		float t = CmPxs * rxsr;
		float u = CmPxr * rxsr;
 
		return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
	}*/
}

