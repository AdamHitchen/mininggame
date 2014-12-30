package something;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;










import entities.*;
import items.*;
import something.*;
import tiles.*;
public class Game extends BasicGame {
	private ArrayList tarpeys;
	public ArrayList players;
	private ArrayList bullets;
	private float mouseX, mouseY;
	private Rectangle rect;
	private float offset;
	public Inventory inv;
	public int tileSize = 16;
	private boolean drawRect = false;
	private int activeTool;
	ItemSpawner isp; 
	public Random random;
	public TerrainGenerator terrain;
	public int maxX = 0;
	int maxY = 0;
	Crafting craft;
	public Camera cam;
	private int width = 1280, height = 720;
	private int dwheel = 0;
	ArrayList<ArrayList> myList = new ArrayList<ArrayList>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try { 
		    AppGameContainer container = new AppGameContainer(new Game("Game")); 
		    container.setDisplayMode(1280,720,false); 
		    container.start(); 
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
		
	}

	public Game(String title) throws SlickException {
		super(title);
		dwheel = 0;
		isp = new ItemSpawner(this);
		random = new Random();
		tarpeys = new ArrayList();
		players = new ArrayList();
		bullets = new ArrayList();
		activeTool = 1;


	}
	
	public int getHeight()
	{
		return height;
	}
	public int getMaxY()
	{
		return maxY;
	}
	public int getWidth()
	{
		return width;
	}
/*	public void addToArrayList(int x, int y)
	{
		ArrayList arrayList1 = (ArrayList) myList.get(x);
		arrayList1.get(y);
	}
	public void removeFromArrayList(int x, int y)
	{
		ArrayList arrayList1 = (ArrayList) myList.get(x);
		arrayList1.get(y) = null;
	}*/
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.translate(-cam.camPosX(), -cam.camPosY());
		//g.translate(50,50);
		Player player = (Player) players.get(0);
		player.render(g);
		g.setColor(Color.green);
		if(drawRect)
		{
			g.draw(rect);
		}
		
		isp.render(g);
		terrain.render(gc, g);
		inv.render(g);
		craft.render(g, gc);
		/*for(int i = 0; i < tarpeys.size(); i ++)
		{
			tarpey Tarpey = (tarpey) tarpeys.get(i);
			Tarpey.render(g);
		}*/

		for(int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = (Bullet) bullets.get(i);
			bullet.render(g);
		}
		


		
	}
	
	public int randomInt(int min, int max)
	{
		
		return random.nextInt((max - min) + 1) + min;
	}


	@Override
	public void init(GameContainer gc) throws SlickException {
		cam = new Camera(this);
		inv = new Inventory(this, cam);
		try {
			terrain = new TerrainGenerator(this, inv, isp, tileSize, cam);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		maxX = terrain.returnMaxX();
		maxY = terrain.returnMaxY();
		cam.setMaxX();
		cam.setMaxY();
		
		craft = new Crafting(this, inv);
		rect = new Rectangle(20,20,30,30);
		//gc.setTargetFrameRate(60);
		Player player = new Player(100,0,this,terrain);
		players.add(player);
		activeTool = 1;

	
	/*	for(int i = 0; i < 5; i++)
		{
			float randNum = (float) (Math.random() * 1000);
			tarpey Tarpey = new tarpey(randNum,randNum, this);
			tarpeys.add(Tarpey);
			Tarpey.setOffset(offset);
			offset+=20;
			
		}*/


	}
	public int returnDwheel()
	{
		return dwheel;
	}
	public float calcDistance(float x1,float y1,float x2,float y2)
	{
		float result;
		result = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2));
		return result;
	}
	public int getSelection()
	{
		return activeTool;
	}
	@Override
	public void update(GameContainer gc, int arg1) throws SlickException 
	{	
/*		if(cam.camPosX() + width > worldRows * 32)
		{
			newX = worldRows * 32;
			worldRow[worldRows] = worldRow[worldRows - 1] + randomInt(-1,1);
			if(worldRow[worldRows] <= 0)
			{
				worldRow[worldRows] = 0;
			}
			else if(worldRow[worldRows] >= 18)
			{
				worldRow[worldRows] = 18;
			}

			for(int h = 1; h < worldRow[worldRows]; h++)
			{
				createTile(newX,height - 32 * h, randomInt(0,1), h, worldRows);
				
			}
			worldRows ++;
			
		}*///
		

		isp.update(gc, arg1);
		try {
			terrain.update(gc, arg1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(gc.getInput().isKeyDown(Input.KEY_1))
		{
			activeTool = 1;
		}
		else if (gc.getInput().isKeyDown(Input.KEY_2))
		{
			activeTool = 2;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_3))
		{
			activeTool = 3;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_4))
		{
			activeTool = 4;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_5))
		{
			activeTool = 5;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_6))
		{
			activeTool = 6;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_7))
		{
			activeTool = 7;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_8))
		{
			activeTool = 8;
		}
		else if(gc.getInput().isKeyDown(Input.KEY_9))
		{
			activeTool = 9;
		}
		dwheel = Mouse.getDWheel();
		if(dwheel < 0 && !craft.returnUI())
		{
			activeTool +=1;
			if(activeTool > 9)
			{
				activeTool = 1;
			}
		}
		else if(dwheel > 0 && !craft.returnUI())
		{
			activeTool-=1;
			if(activeTool < 1)
			{
				activeTool = 9;
			}
		}
		int activeID = inv.activeID(activeTool-1);
		Player player = (Player) players.get(0);
		float nextGravityPos = player.nextGravityPos(arg1) + player.getPosition().y;
		boolean move = true;
			player.gravity(arg1);

		player.update(gc, arg1);
		inv.update(gc, arg1);
			for(int i = 0; i < tarpeys.size(); i++)
			{
				tarpey Tarpey = (tarpey) tarpeys.get(i);
				Tarpey.setPPos(player.getPosition());
				Tarpey.update(gc, arg1);

			}
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
		/*	if(activeTool == 1)
			{
				Bullet bullet = new Bullet(player.getPosition().x,player.getPosition().y,gc.getInput().getAbsoluteMouseX() + cam.camPosX(),gc.getInput().getAbsoluteMouseY() + cam.camPosY(), this);
				//Bullet bullet = new Bullet(300,300,gc.getInput().getAbsoluteMouseX(),gc.getInput().getAbsoluteMouseY());
				bullets.add(bullet);
			}*/
			if (inv.returnMiningTools().contains(activeID))
			{
				int currentChunk = 0;
				Chunk[] chunk = terrain.returnChunks();
				boolean canMine = false;
				int chunkSize = terrain.returnChunkSize();
				int tileSize = terrain.returnTileSize();
				int tempInt = (int)((gc.getInput().getAbsoluteMouseX() + cam.camPosX() - ((gc.getInput().getAbsoluteMouseX() + cam.camPosX()) % (chunkSize * tileSize))) / (chunkSize * tileSize));
				
				int relativeX =(int)(gc.getInput().getAbsoluteMouseX() + (cam.camPosX() - cam.camPosX() % tileSize) - tempInt * tileSize * chunkSize)/tileSize;
				int yloc1 =  (int) (maxY - ((gc.getInput().getAbsoluteMouseY()+ cam.camPosY()) - (gc.getInput().getAbsoluteMouseY() + cam.camPosY()) % tileSize) / tileSize);
				System.out.println("tempInt  = " + tempInt);
				for(int i = 0; i < terrain.returnChunks().length; i++)
				{
					if(tempInt == chunk[i].returnID() )
					{
						currentChunk = i;
					}
				}
				System.out.println("currentChunk = " + currentChunk);
				Tile[][] tiles = chunk[currentChunk].getTiles();
				
				/*for(int i = 0; i < chunk[currentChunk].getTiles().size(); i++)
				{
				
					Tile tile = (Tile) chunk[currentChunk].getTiles().get(i);
					
					if(gc.getInput().getAbsoluteMouseX() + cam.camPosX() > tile.getPos().x && gc.getInput().getAbsoluteMouseX() + cam.camPosX()< tile.getPos().x + tile.returnWidth()
							&& gc.getInput().getAbsoluteMouseY()+ cam.camPosY() > tile.getPos().y && gc.getInput().getAbsoluteMouseY() + cam.camPosY()< tile.getPos().y + tile.returnHeight())
					{//No longer needed, location is already detected now*/
						ArrayList<Integer> miningTools = (ArrayList<Integer>) inv.returnMiningTools();
						ArrayList<Integer> miningStrength = (ArrayList<Integer>) inv.returnMiningStrength();
						for(int z = 0; z < miningTools.size() && z < miningStrength.size(); z ++)
						{
							System.out.println("Chunk: " + currentChunk + " " + relativeX + ", " + yloc1);

							if( tiles[relativeX][yloc1] != null && miningTools.get(z) == activeID && miningStrength.get(z) >= tiles[relativeX][yloc1].returnStrength())
							{
								canMine = true;
								System.out.println("CanMine = true");
							}
						}
						if(canMine)
						{
							float triX = player.getPosition().x - tiles[relativeX][yloc1].getPos().x;
							float triY = player.getPosition().x - tiles[relativeX][yloc1].getPos().y;
							float xloc =  (gc.getInput().getAbsoluteMouseX() + cam.camPosX()) - ((gc.getInput().getAbsoluteMouseX()+ cam.camPosX()) % tileSize);
							float yloc =  (gc.getInput().getAbsoluteMouseY()+ cam.camPosY()) - (gc.getInput().getAbsoluteMouseY() + cam.camPosY()) % tileSize;
							int x = (int) xloc / tileSize;
							int y = (int) (((maxY*tileSize) - yloc) / tileSize);
							if(calcDistance(player.getPosition().x + player.playerImage.getWidth()/2,player.getPosition().y + player.playerImage.getHeight()/2,tiles[relativeX][yloc1].getPos().x,tiles[relativeX][yloc1].getPos().y) < tileSize*8)
							{
							
								tiles[relativeX][yloc1].drop();
								tiles[relativeX][yloc1] = null;
								terrain.removeTile(relativeX, yloc1, chunk[currentChunk]);
								System.out.println("trying to remove chunk");
								terrain.setWorldArray(x, y, 0,chunk[currentChunk], false,xloc,yloc);
								System.out.println("Trying to update array");
							}
						}
					
				
			}
			else if(terrain.returnTileTypes().contains(activeID) && !craft.returnUI())
			{
				
				float xloc;
				float yloc;
				xloc =  (gc.getInput().getAbsoluteMouseX() + cam.camPosX()) - ((gc.getInput().getAbsoluteMouseX()+ cam.camPosX()) % tileSize);
				yloc =  (gc.getInput().getAbsoluteMouseY()+ cam.camPosY()) - (gc.getInput().getAbsoluteMouseY() + cam.camPosY()) % tileSize;
				int x = (int) xloc / tileSize;
				int y = (int) (((maxY*tileSize) - yloc) / tileSize);
				boolean canPlace = true;
				int currentChunk = 0;
				Chunk[] chunk = terrain.returnChunks();
				boolean canMine = false;
				int chunkSize = terrain.returnChunkSize();
				int tileSize = terrain.returnTileSize();
				int tempInt = (int)((gc.getInput().getAbsoluteMouseX() + cam.camPosX() - ((gc.getInput().getAbsoluteMouseX() + cam.camPosX()) % (chunkSize * tileSize))) / (chunkSize * tileSize));
				System.out.println("tempInt  = " + tempInt);
				for(int i = 0; i < terrain.returnChunks().length; i++)
				{
					if(tempInt == chunk[i].returnID() )
					{
						currentChunk = i;
					}
				}
				System.out.println("currentChunk = " + currentChunk);
				int xx = x - chunk[currentChunk].returnID() * chunkSize;

				if(chunk[currentChunk].returnRelativeTiles()[xx][y] == 0)
				{
					if((gc.getInput().getAbsoluteMouseX() + cam.camPosX() > player.getPosition().x &&  gc.getInput().getAbsoluteMouseX() + cam.camPosX() < player.getPosition().x + player.getWidth() 
						&& gc.getInput().getAbsoluteMouseY() +cam.camPosY() >  player.getPosition().y && gc.getInput().getAbsoluteMouseY() + cam.camPosY() < player.getPosition().y + player.getHeight())) {canPlace = false;}
					
					if(canPlace){
						if(inv.haveItem(activeID))
						{
						//	terrain.createTile(x*tileSize, maxY*tileSize - tileSize * y, activeID, x);
							inv.removeItem(activeID);
							terrain.setWorldArray(x,y, activeID, chunk[currentChunk], true,xloc,yloc);
							
						}
					}
				}
			}
		}
		

		
		for(int i = 0; i < bullets.size(); i++)
		{
			Bullet bullet = (Bullet) bullets.get(i);
			if(bullet.returnPosition().x > cam.camPosX() - 64 && bullet.returnPosition().x < cam.camPosX() + getWidth() + 64 && 
				bullet.returnPosition().y > cam.camPosY() - 64 && bullet.returnPosition().y < cam.camPosY() + getHeight())
			{
				bullet.update(gc,arg1);
			}
			else
			{
				bullet = null;
				bullets.remove(i);
				i--;
			}
			for(int ii = 0; ii < tarpeys.size(); ii++)
			{
				tarpey Tarpey = (tarpey) tarpeys.get(ii);
				if(bullet != null && bullet.returnPosition().x > Tarpey.getPosition().x && bullet.returnPosition().x < Tarpey.getPosition().x + Tarpey.getWidth()
						&& bullet.returnPosition().y > Tarpey.getPosition().y + 30 && bullet.returnPosition().y < Tarpey.getPosition().y + Tarpey.getHeight() + 20)
				{
					//Tarpey.die();
					Tarpey = null;
					tarpeys.remove(ii);
					ii--;
					bullet = null;
					bullets.remove(i);
						i--;
						break;
					
				}
			}
			
		}
	
		cam.update();
		craft.update(gc);
	}

	int[][] GenerateWhiteNoise(int width, int height)
	{
	    Random random = new Random(30); //Seed to 0 for testing
	    float[][] noise = new float[width][height];
	    int[][] noises = new int[width][height];
	    for (int i = 0; i < width; i++)
	    {
	        for (int j = 0; j < height; j++)
	        {
	            noise[i][j] = (float)random.nextDouble() % 1;
	        }
	    }
	    for (int i = 0; i < width; i ++)
	    {
	    	for(int y = 0; y < height; y ++)
	    	{
	    		noises[i][y] = Math.round(noise[i][y] * 10);
	    	}
	    	
	    }
	    return noises;
	}

	
	
	
}
