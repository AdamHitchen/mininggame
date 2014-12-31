package something;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import tiles.*;

public class Chunk 
{
	int chunkID;
	TerrainGenerator terrain;
	private static String currentDirectory = new File("").getAbsolutePath();
	int[][] relativechunkTiles; 
	ItemSpawner isp;
	String mapName = "";
	Tile[][] relativeTiles;
	boolean newWorld;
	int timePassed;
	String file;
	public Chunk(int[][] tiles, int chunkID, TerrainGenerator terrain, ItemSpawner isp, String mapName, boolean newWorld) throws SlickException, IOException 
	{//called when generating a new world
		this.newWorld = newWorld;
		this.chunkID = chunkID;
		this.mapName = mapName;
		relativechunkTiles = new int[terrain.returnChunkSize()][terrain.returnMaxY()];
		this.isp = isp;
		this.terrain = terrain;
		relativeTiles = new Tile[terrain.returnChunkSize()][terrain.returnMaxY()];
		relativechunkTiles = tiles;
		file = currentDirectory + "/maps/"+mapName+"/"+chunkID+".txt";
		saveTiles();
		makeTiles();

	}
	
	public Chunk(int chunkID, TerrainGenerator terrain, ItemSpawner isp, String mapName, boolean newWorld) throws SlickException, FileNotFoundException, IOException 
	{//called when world already exists
		this.chunkID = chunkID;
		relativechunkTiles = new int[terrain.returnChunkSize()][terrain.returnMaxY()];
		this.isp = isp;
		this.terrain = terrain;
		this.mapName = mapName;
		file = currentDirectory + "/maps/"+mapName+"/"+chunkID+".txt";
		this.newWorld = newWorld;
		relativeTiles = new Tile[terrain.returnChunkSize()][terrain.returnMaxY()];
		loadTiles();
		//makeTiles();
	}
	
	public void loadTiles()
	{
		Runnable r = new ChunkLoader(terrain, file, this, isp);
		r.run();
	}
	public void setArray(int[][] array, Tile[][] t)
	{
		relativechunkTiles = array;
		relativeTiles = t;
	}

	public int returnID()
	{
		return chunkID;
	}
	public void saveTiles() throws IOException
	{
		Runnable f = new ChunkSaver(terrain, file, relativechunkTiles, this);
		f.run();
	}
	public int[][] returnRelativeTiles()
	{
		return relativechunkTiles;
	}
	public void removeTile(int x, int y)
	{
		relativeTiles[x][y] = null;
	}
	private void makeTiles() throws SlickException
	{
		for(int i = 0; i < terrain.returnChunkSize(); i++)
		{
			for(int y = 0; y < terrain.returnMaxY(); y++)
			{
				if(i >= 0 && i < terrain.returnChunkSize() && y >= 0 && y < terrain.returnMaxY())
				{	
					createTile(i * terrain.game.tileSize + chunkID*terrain.game.tileSize*terrain.returnChunkSize(), 
						terrain.returnMaxY()*terrain.game.tileSize - terrain.game.tileSize * y, relativechunkTiles[i][y], y, i);
				}

			}
		}
	}
	
	public Tile[][] getTiles()
	{
		return relativeTiles;
	}
	public void render(GameContainer gc,Graphics g)
	{
		for (int i = 0; i < terrain.returnChunkSize(); i++)
		{
			for(int y = 0; y < terrain.returnMaxY(); y++)
			{
				if(relativeTiles[i][y] != null)
				{
					relativeTiles[i][y].render(gc, g);				
					}
			}
		}
	}
	public void setTile(int xx, int y, int type, boolean bool,float xloc, float yloc) throws SlickException
	{
		System.out.println("Trying to set: "  + ", " + y + " to type: " + type);
		relativechunkTiles[xx][y] = type;
		if(bool)
		{
			createTile((int)xloc, (int)yloc,type,y, xx);
		}
	}
	public void update(GameContainer gc, int arg1) throws IOException
	{
		for (int i = 0; i < terrain.returnChunkSize(); i++)
		{
			for(int y = 0; y < terrain.returnMaxY(); y++)
			{
				if(relativeTiles[i][y] != null)
				{
					relativeTiles[i][y].update(gc);		
				}
			}
		}
		timePassed+=1*arg1;
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE) && timePassed > 10000)
		{
			saveTiles();
			System.out.println("Chunk" + chunkID + " is trying to save");
			timePassed = 0;
		}
	}

	public void createTile(int tileX, int tileY, int tileType, int row, int x) throws SlickException
	{
			if(tileType == 1)
			{
				t_dirt tile = new t_dirt(tileX, tileY, row, isp);
				relativeTiles[x][row] =  new t_dirt (tileX, tileY, row, isp);
				//System.out.println("relativeTiles = "+relativeTiles[row][y]);
			}
			else if(tileType == 2)
			{
				t_grass tile = new t_grass(tileX, tileY, row, isp);
				relativeTiles[x][row] = tile;
			}
			else if( tileType == 3)
			{
				t_stone tile = new t_stone(tileX, tileY, row, isp);
				//System.out.println("row = " + row + " y = " + y);
				relativeTiles[x][row] = tile;
			}
			else if( tileType == 4)
			{
				t_iron tile = new t_iron(tileX, tileY, row, isp);
				relativeTiles[x][row] = tile;
				
			}
			else if( tileType == 5)
			{
				t_silver tile = new t_silver(tileX, tileY, row, isp);
				relativeTiles[x][row] = tile;
			}
			else if(tileType == 6)
			{
				t_Wood tile = new t_Wood(tileX, tileY, row, isp);
				relativeTiles[x][row] = tile;
			}
			else if (tileType == 9)
			{
				t_Workbench tile = new t_Workbench(tileX, tileY, row, isp);
				relativeTiles[x][row] = tile;
			}
	}

	
}
