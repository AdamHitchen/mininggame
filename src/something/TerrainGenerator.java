package something;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import tiles.*;

public class TerrainGenerator {
	Game game;
	private int maxX, maxY;
	private int[][] worldArray;
	private int[] worldRow;
	private int worldRows;
	private double dicks;
	Random random = new Random();
	public ArrayList<Integer> tileTypes;
	public ArrayList world;
	public int[][] noiseTest;
	double generate;
	Music sound1;
	String filename;
	ItemSpawner isp;
	Image tempImage;
	public TerrainGenerator(Game gm, Inventory inv, ItemSpawner iisp, int maxX, int maxY) throws SlickException, IOException
	{
		sound1 = new Music("res/sounds/mega goat.wav");
		this.maxX = maxX;
		this.maxY = maxY;
		worldArray = new int[maxX][maxY];
		noiseTest = new int[maxX][maxY];
		worldRow = new int[maxX];
		tileTypes = new ArrayList<Integer>();
		tileTypes.add(1);
		tileTypes.add(2);
		tileTypes.add(3);
		tileTypes.add(4);
		tileTypes.add(5);
		tileTypes.add(6);
		tileTypes.add(9);
		game = gm;
		world = new ArrayList();
		isp = iisp;
		worldRow[0] = game.randomInt(maxY-40,maxY - 10);
		int[][] noises = new int[maxX][maxY];
		generate = 8.678106215752825;
		System.out.print(generate);
		System.out.println("");
		filename = Double.toString(generate);
		System.out.println("Filename = "+filename);
		generateNoise(maxX, maxY, 5 + generate);

		/*	for(int w = 0; w < maxX; w++)
		{
			worldRows++;
			newX = w * 32;
			if(w > 0)
			{
				worldRow[w] = worldRow[w-1] + randomInt(-1,1);
				if(worldRow[w] < 1)
				{
					worldRow[w] = 1;
				}
				else if(worldRow[w] > 99)
				{
					worldRow[w] = 99;
				}
			}
			for(int h = 1; h < worldRow[w]; h++)
			{
				createTile(newX,maxY*32 - 32 * h, randomInt(1,1), h, w);
				
			}
		}*/
		
		
		
		for(int i = 0; i < maxX; i++)
		{
			if(i > 0)
			{
				worldRow[i] = worldRow[i-1] + game.randomInt(-1,1);
				if(worldRow[i] < 1)
				{
					worldRow[i] = 1;
				}
				else if(worldRow[i] > maxY)
				{
					worldRow[i] = maxY - 1;
				}
			}
			for(int y = 0; y < worldRow[i]; y++)
			{
				if(y < worldRow[i] -1)
				{
					worldArray[i][y] = 1;
				}
				else
				{
					worldArray[i][y] = 2;
				}
			}
		}
	  
	    //noises = game.GenerateWhiteNoise(maxX,maxY);
		int caveSurfaces= 0, chestsGenerated = 0;
	    for(int i = 0; i < maxX; i ++)
	    {
	    	for(int y = 0; y < maxY; y ++)
	    	{
	/*    		if(y >= worldRow[i] - 4)
	    		{
	    			noises[i][y] = 0;
	    		}
	    		if(noises[i][y] == 2)
	    		{
	    			noises[i][y] = 1;
	    		}
	   		if(y < worldRow[y] - 4 && noises[i][y] != 0)
	    		{
	    			worldArray[i][y] = noises[i][y];
	    		}*/
	    		if(y < worldRow[i] - 4 && ( noiseTest[i][y] == 3|| noiseTest[i][y] == 4  ||noiseTest[i][y] == 5 ||noiseTest[i][y] == 6 || noiseTest[i][y] == 7 || noiseTest[i][y] == 8))
	    		{
	    			worldArray[i][y] = 0;
	    			//generate chests//
	    			if(i > 0 && y > 0)if(worldArray[i][y-1] == 3)
	    			{
	    				caveSurfaces++;
	    				 if(random.nextInt(100) == 90)
	    				 {
	    					 worldArray[i][y] = 6;
	    					 chestsGenerated++;
	    				 }
	    			}
	    		}
	    		else if(y < worldRow[i] - 4 && (noiseTest[i][y] == 1 || noiseTest[i][y] == 8 || noiseTest[i][y] == 9 || noiseTest[i][y]==10|| noiseTest[i][y] == 5 || noiseTest[i][y] == 6 || noiseTest[i][y] == 2))
	    		{

	    			worldArray[i][y] = 3;
	    			if(random.nextInt(100) == 90)
	    			{
	    				generateCluster(i,y,4);
	    			}
	    		}
	  /*  		else if(y < worldRow[i] - 4 && noiseTest[i][y] == -1)
	    		{
	    			worldArray[i][y] = 2;

	    		}*/
	    	}
	    }
	    System.out.println(caveSurfaces + "Open cave surfaces");
	    System.out.println(chestsGenerated + "Chests generated");
	    
		for(int i = 0; i < maxX ; i ++)
		{
			for(int y = 0; y < maxY ; y++)
			{
				createTile(i * game.tileSize, maxY*game.tileSize - game.tileSize * y, worldArray[i][y], y);
				
			}
		}
		BufferedImage image = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
		int rgb = 0;
		for(int x = 0; x < maxX; x ++)
		{
			for(int y = 0; y < maxY; y++)
			{
				if(worldArray[x][y] == 1) //Draw Dirt
				{
					 rgb = 0x402200; 
				}
				else if(worldArray[x][y] == 2)//Draw Grass
				{
					 rgb = 0x60c040;
				}
				else if(worldArray[x][y] == 3) // Draw Stone
				{
					 rgb = 0x4a4a4a;
				}
				else if(worldArray[x][y] == 0)//Draw Void
				{
					 rgb =0x000000;
				}
				else if(worldArray[x][y] == 4) //Draw Ore
				{
					rgb = 0xE2C670;
				}
				else if(worldArray[x][y] == 6) //Draw Chests
				{
					rgb = 0x62c7fb;
				}
				else
				{
					rgb = 0x000000;
				}
				image.setRGB(x, maxY - 1 - y, rgb);
			}
		}
		ImageIO.write(image, "png", new File(filename + " map.png"));
		  File f = new File(filename + " map.png");
		    Desktop dt = Desktop.getDesktop();
		    dt.open(f);
		    System.out.println("Done.");
		sound1.play();

	}
	public ArrayList<Integer> returnTileTypes()
	{
		return tileTypes;
	}
	private void generateCluster(int x, int y, int type)
	{
		int size = random.nextInt(15) + 4;
		int lastDirection;
		int x1 = x, y1 = y;
		worldArray[x][y] = type;
		for(int i = 0; i < size; i ++)
		{
			int direction = random.nextInt(3) + 1;
			if(direction == 1)
			{
				y -= 1;
			}
			else if(direction == 2)
			{
				y +=1;
			}
			else if(direction == 3)
			{
				x -= 1;
			}
			else if (direction == 4)
			{
				x += 1;
			}
			if(x<maxX && x >=0 && y < maxY && y >=0)
				{
					if(worldArray[x][y]!=0)worldArray[x][y] = type;
				}
		}
	}
	
	public Tile getWorld(int i)
	{
		Tile tile = (Tile) world.get(i);
		return tile;
	}
	public int getSize()
	{
		return world.size();
	}
	public void addTile(Tile tile)
	{
		world.add(tile);
	}
	public void removeTile(int i)
	{
		world.remove(i);
	}
	public void removeTile(Tile t)
	{
		world.remove(t);
		t = null;
	}
	public void replaceTile(Tile t1, int type, int x, int y) throws SlickException
	{
		world.remove(t1);
		t1 = null;
	//	isp.getGame().terrain.createTile(x*32, y*32, 2, y);
	//	worldArray[x][y] = type; 
	}

	public int getWorldArray(int x,int y)
	{
		if(x<maxX && x >=0 && y < maxY && y >=0)return worldArray[x][y];
		else
			return 0;
	}
	public void setWorldArray(int x, int y, int value) throws SlickException
	{
		if(x<maxX && x >=0 && y < maxY && y >=0)worldArray[x][y] = value;
		
	}
	
	public void init() throws SlickException
	{

		
	}
	public void createTile(int tileX, int tileY, int tileType, int row) throws SlickException
	{
	//	if(h < worldRow[row] -1)
	//	{
			if(tileType == 1)
			{
				t_dirt tile = new t_dirt(tileX, tileY, row, isp);
				world.add(tile);
			}
			else if(tileType == 2)
			{
				t_grass tile = new t_grass(tileX, tileY, row, isp);
				world.add(tile);
			}
			else if( tileType == 3)
			{
				t_stone tile = new t_stone(tileX, tileY, row, isp);
				world.add(tile);
			}
			else if( tileType == 4)
			{
				t_iron tile = new t_iron(tileX, tileY, row, isp);
				world.add(tile);	
			}
			else if( tileType == 5)
			{
				t_silver tile = new t_silver(tileX, tileY, row, isp);
				world.add(tile);
			}
			else if(tileType == 6)
			{
				t_Wood tile = new t_Wood(tileX, tileY, row, isp);
				world.add(tile);
			}
			else if (tileType == 9)
			{
				t_Workbench tile = new t_Workbench(tileX, tileY, row, isp);
				world.add(tile);
			}

	
			tempImage = new Image("res/tiles/Sticks and pickaxes.png");

	}
	
	public void render(GameContainer gc,Graphics g)
	{
		tempImage.draw(256 - game.cam.camPosX(),maxY * game.tileSize - worldRow[8]*game.tileSize - game.cam.camPosY());
		for(int i = 0; i < world.size(); i++)
		{
			Tile tile = (Tile) world.get(i);
			tile.render(gc, g);
		}
		//Tile tile = (Tile) world.get(0);
		//tile.render(gc, g);
		for(int i = 0; i < maxX ; i ++)
		{
			for(int y = 0; y < maxY ; y++)
			{
				//g.setColor(Color.green);
				//g.drawString(dicks + "", i * 32, maxY*32 - 32 * y);
			//	g.setColor(Color.red);
				//g.drawString(worldArray[i][y] + "" ,i * 32 + 20, maxY*32 - 32 * y);
			}
		}
	}
	public void generateNoise(float WIDTH, float HEIGHT, double FEATURE_SIZE) throws IOException
	{
		OpenSimplexNoise noise = new OpenSimplexNoise();
		BufferedImage image = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < maxX-1; x++)
		{
			
			for (int y = 0; y < maxY-1; y++)
			{
				double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE);//, 0.0);
				
				int rgb = 0x010101 * (int)((value + 1) * -120.0);
				image.setRGB(x, y, rgb);
				//System.out.print("("+x + ", " + y + ") " + dicks + " ");
				if(x<maxX && y<maxY)
				//	if(value < 0)
				//	{
						noiseTest[x][y] = (int) Math.round(value*10);
				/*	}
					else if (value > 0 && value < 10)
					{
						noiseTest[x][y] = (int) Math.round(value);
					}
					else if(value > 10)
					{
						noiseTest[x][y] = (int) Math.round(value/10);
					}*/
						//noiseTest2[x][y] = value;
			}
		}
		ImageIO.write(image, "png", new File("noiseasd.png"));

	}
	public void update(GameContainer gc, int arg1)
	{
		for(int i = 0; i < world.size(); i++)
		{
			Tile tile = (Tile) world.get(i);
			tile.update(gc);
		}
	}

}
