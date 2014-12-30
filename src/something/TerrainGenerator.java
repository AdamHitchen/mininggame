package something;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	private int maxX = 1080, maxY = 1080;
	private int[][] worldArray;
	private int[] worldRow;
	private int worldRows;
	private double dicks;
	Random random = new Random();
	public ArrayList<Integer> tileTypes;
	public ArrayList world;
	public ArrayList chunks;
	public int[][] noiseTest;
	Chunk[] chunk;
	private boolean newGame = false;
	double generate;
	Music sound1;
	int loadedChunks;
	private int numChunks;
	Camera cam;
	private int chunkSize = 40;
	private int chunksToLoad = 5;
	String filename;
	private static String currentDirectory = new File("").getAbsolutePath();
	ItemSpawner isp;
	String mapName = "testMap2";
	Image tempImage;
	Image[] allImages;
	int tileSize;
	public TerrainGenerator(Game gm, Inventory inv, ItemSpawner iisp, int tileSize, Camera cam) throws SlickException, IOException
	{
		chunk = new Chunk[chunksToLoad];
		this.tileSize = tileSize;
		sound1 = new Music("res/sounds/mega goat.wav");		
		game = gm;
		chunks = new ArrayList();
		world = new ArrayList();
		isp = iisp;
		worldArray = new int[maxX][maxY];
		this.cam = cam;
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
		allImages = new Image[9];
		allImages[0] = new Image("res/tiles/t_dirt.png");
		allImages[1] = new Image("res/tiles/t_grass.png");
		allImages[2] = new Image("res/tiles/t_stone.png");
		allImages[3] = new Image("res/tiles/t_iron.png");
		allImages[4] = new Image("res/tiles/t_silver.png");
		allImages[5] = new Image("res/tiles/t_Wood.png");
		allImages[8] = new Image("res/tiles/t_Workbench.png");
		
			File Dimensions = new File(currentDirectory+"/maps/"+mapName+"/dimensions.dat");
			if(Files.exists(Paths.get(currentDirectory + "/maps/"+mapName)))
			{
				BufferedReader br = new BufferedReader(new FileReader(Dimensions));
				String sr = br.readLine();
				String sr2 = br.readLine();
				System.out.println(sr + " "+ sr2);
				if(sr != null && sr2 != null)
				{
				
					maxX = Integer.parseInt(sr);
					maxY = Integer.parseInt(sr2);
					
				}
				br.close();
			}
	
			System.out.println(maxX + ", " + maxY);
		
		if(maxX%chunkSize != 0) //Determine the number of chunks
		{
			numChunks = (maxX - maxX%chunkSize) / chunkSize + 1; 
		}
		else
		{
			numChunks = maxX/chunkSize; 
		}
		Path save = Paths.get(currentDirectory+"/maps/" + mapName + "/");
		if(!Files.exists(Paths.get(currentDirectory+"/maps/")))
		{
			File x = new File(currentDirectory+"/maps/");
			x.mkdir();
		}
		if (Files.exists(save)) {
		    System.out.println("map exists!");
		    initializeChunks();
		}
		else
		{
			File f = new File(currentDirectory+"/maps/" + mapName + "/");
			f.mkdir();
			FileWriter filestream = new FileWriter(Dimensions, false);
			BufferedWriter fw = new BufferedWriter(filestream);
			fw.write(maxX + "\n");
			fw.write(maxY + "\n");
			fw.flush();
			fw.close();
			System.out.println("map created!");
			generateWorld();
			initializeChunks();
		}
		System.out.println(numChunks+ " Chunks. ");

		
	}
	public void setCam(Camera cam)
	{
		this.cam = cam;
	}
	public void initializeChunks() throws FileNotFoundException, SlickException, IOException
	{
		if(((cam.camPosX() - cam.camPosX() % chunkSize) / chunkSize) - 1 >= 0)
		{
			for(int i = 0; i < chunksToLoad; i++)
			{
				Chunk chunks = new Chunk((int) ((cam.camPosX() - cam.camPosX() % chunkSize) / chunkSize) - 1,this,isp,filename, false);
				chunk[i] = chunks;
			}
		}
		else
		{
			for(int i = 0; i < chunksToLoad; i++)
			{
				Chunk chunks = new Chunk(i,this,isp,mapName,false);
				chunk[i] = chunks;
			}
		}
	}
	public int returnMaxX()
	{
		return maxX;
	}
	public int returnChunksToLoad()
	{
		return chunksToLoad;
	}
	public int returnTileSize()
	{
		return tileSize;
	}
	public void setLoadedChunks() throws FileNotFoundException, SlickException, IOException
	{
		int lastLoadedChunks = loadedChunks;
	/*	if(cam.camPosX() < 1*chunkSize*tileSize)
		{
			loadedChunks = 0;
		}
		else if(cam.camPosX() > (numChunks-1) * chunkSize * tileSize)
		{
			loadedChunks = numChunks - chunksToLoad;
		}
		else wd*/
		
			loadedChunks = (int)((cam.camPosX() - cam.camPosX() % (chunkSize * tileSize)) / (chunkSize * tileSize)-2);
			//< chunk[1].returnID() * chunkSize * tileSize;
			
		if(loadedChunks < 0)
		{
			loadedChunks = 0;
		}
		else if (loadedChunks > numChunks)
		{
			System.out.println("numChunks " + numChunks);
			loadedChunks = numChunks-chunksToLoad;
		}
		
		if(lastLoadedChunks!=loadedChunks)System.out.println("Loaded Chunks: " + loadedChunks);
		if(lastLoadedChunks == loadedChunks) //if nothing's changed...
		{
			//do nothing
		}
		else if(loadedChunks > lastLoadedChunks) 
		{
			System.out.println("This code has run");
			chunk[0].saveTiles();
			chunk[0] = null;
			for(int i = 0; i <= chunksToLoad; i++)
			{
				if(i < chunksToLoad && i + 1 < chunksToLoad)
				{
					chunk[i] = chunk[i+1];
					System.out.println("So has this");
				}
			}
			Chunk chunkz = new Chunk(loadedChunks+chunksToLoad-1, this, isp, mapName, false);
			chunk[chunksToLoad-1] = (Chunk) chunkz;
		}
		else if(loadedChunks < lastLoadedChunks)
		{/*
			System.out.println("THIS HAS RUN");
			chunk[4] = null;
			chunk[4] = chunk[3];
			chunk[3] = chunk[2];
			chunk[2] = chunk[1];
			chunk[1] = chunk[0];*/
			chunk[4].saveTiles();
			for(int i = chunksToLoad-1; i > 0; i--)
			{
				if(i - 1 >= 0)
				{
					chunk[i] = chunk[i-1];
				}
			}
			Chunk chunkz = new Chunk(loadedChunks, this, isp, mapName, false);
			chunk[0] = (Chunk) chunkz;
		}
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
	public void setWorldArray(int x, int y, int value, Chunk chun, boolean bool, float xloc, float yloc) throws SlickException
	{
		int xx = x - chun.returnID() * chunkSize;
		chun.setTile(xx,y,value, bool, xloc, yloc);
		//if(x<chunkSize && x >=0 && y < maxY && y >=0)chun.returnRelativeTiles()[xx][y] = value;
	
	}
	
	
	public void render(GameContainer gc,Graphics g)
	{
		//tempImage.draw(256 - cam.camPosX(),maxY * game.tileSize - worldRow[8]*game.tileSize - cam.camPosY());
		for(int i = 0; i < chunksToLoad; i++)
		{
			chunk[i].render(gc, g);
		}
		//Tile tile = (Tile) world.get(0);
		//tile.render(gc, g);
	}

	public void update(GameContainer gc, int arg1) throws FileNotFoundException, SlickException, IOException
	{
		setLoadedChunks();
		for(int i = 0; i < chunksToLoad; i++)
		{
			chunk[i].update(gc, arg1);
		}
	}
	private void generateWorld() throws IOException, SlickException
	{
		System.out.println(game.getWidth() + " width");
		worldRow[0] = game.randomInt(maxY-40,maxY - 10);
		
		//generate = 12.678106215752825;
		generate = 9.678106215752825;
		System.out.print(generate);
		System.out.println("");
		filename = Double.toString(generate);
		System.out.println("Filename = "+filename);
		generateNoise(maxX, maxY, 5 + generate);
	
		for(int i = 0; i < maxX; i++) //Generate surface height
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
	  	int caveSurfaces= 0, chestsGenerated = 0;
	    for(int i = 0; i < maxX; i ++)
	    {
	    	for(int y = 0; y < maxY; y ++)
	    	{
	    		if(y < worldRow[i] - 4 && ( noiseTest[i][y] == 5 ||noiseTest[i][y] == 6 || noiseTest[i][y] == 7 || noiseTest[i][y] == 8 || noiseTest[i][y] == 4 || noiseTest[i][y] == 5 || noiseTest[i][y] == 10))
	    		{//values to set to blank space ^^^^^^^^^^^^^^^^^^^^^^
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
	    		else if(y < worldRow[i] - 4 && (noiseTest[i][y] == 1 || noiseTest[i][y] == 8 || noiseTest[i][y] == 9 || noiseTest[i][y]==10
	    				|| noiseTest[i][y] == 5 || noiseTest[i][y] == 6 || noiseTest[i][y] == 2 ||noiseTest[i][y] == 3|| noiseTest[i][y] == 4 ||  noiseTest[i][y] == -5 ||noiseTest[i][y] == -6 || noiseTest[i][y] == -7 ))
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
	    for(int i = 0; i < 10; i++)
	    {
	    }
	    for(int c = 0; c <= numChunks-1; c++)
	    {
		    int[][] tileArray = new int[chunkSize][maxY]; 
			for(int i = 0; i <= chunkSize-1; i++)
			{
				for(int y = 0; y <= maxY-1; y++)
				{
					int temp = i+c*chunkSize;	
					if(temp < maxX)tileArray[i][y] = worldArray[temp][y];	
				}
			}

			Chunk chunk = new Chunk(tileArray, c, this, isp, mapName, true);
			chunks.add(chunk);
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
	public Image returnImage(int i)
	{
		return allImages[i-1];
	}
	public int returnMaxY()
	{
		return maxY;
	}
	public int returnChunkSize()
	{
		return chunkSize;
	}
	public ArrayList<Integer> returnTileTypes()
	{
		return tileTypes;
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
	public void removeTile(int x, int y, Chunk chun)
	{
		chun.removeTile(x,y);
		
	}
	public void removeTile(Tile t)
	{
		world.remove(t);
		t = null;
	}
	public Chunk[] returnChunks()
	{
		return chunk;
	}

}
