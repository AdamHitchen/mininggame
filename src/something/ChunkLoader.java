package something;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import tiles.*;

public class ChunkLoader implements Runnable{
	TerrainGenerator terrain;
	String file;
	ItemSpawner isp;
	Chunk chunk;
	Tile[][] chunkyTiles;
	private ArrayList chunkTiles = new ArrayList();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int[][] newTiles;
		try {
			System.out.println(chunk.returnID()+"Loading Array");
			newTiles = loadTiles();
			System.out.println(chunk.returnID()+"Array loaded");
			long getTime = System.currentTimeMillis();
			for(int i = 0; i < terrain.returnChunkSize(); i++)
			{
				for(int y = 0; y < terrain.returnMaxY(); y++)
				{
					createTile(i * terrain.game.tileSize + chunk.returnID()*terrain.game.tileSize*terrain.returnChunkSize(), 
							terrain.returnMaxY()*terrain.game.tileSize - terrain.game.tileSize * y, newTiles[i][y], y,i);
					Thread.yield();
				}
			}
			getTime = System.currentTimeMillis() - getTime;
			System.out.println("Tile array created in " + getTime + " MS");
			System.out.println(chunk.returnID()+"Tiles Created");
			chunk.setArray(newTiles, chunkyTiles);
			System.out.println(chunk.returnID()+"Tiles returned");
			
		} catch (IOException | SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public ChunkLoader(TerrainGenerator terrain, String file, Chunk chunk, ItemSpawner isp)
	{
		this.isp = isp;
		this.chunk = chunk;
		this.terrain = terrain;
		this.file = file;
		chunkyTiles = new Tile[terrain.returnChunkSize()][terrain.returnMaxY()];
	}
	
	private void loadObjects()
	{
		
	}
	
	private int[][] loadTiles() throws FileNotFoundException, IOException
	{//Loads tiles from a txt file named [chunk ID].txt
		int[][] tempTiles = new int[terrain.returnChunkSize()][terrain.returnMaxY()];
		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			for(int i = 0; i < terrain.returnChunkSize(); i++)
			{
				for(int y = 0; y < terrain.returnMaxY(); y++)
				{
					    String line = br.readLine(); 
					   //System.out.println(line);
					    if(line!=null)tempTiles[i][y] = Integer.parseInt(line);  
					    Thread.yield();
				}
			}
		}
		return tempTiles;
	}


	public void createTile(int tileX, int tileY, int tileType, int row, int x) throws SlickException
	{
			if(tileType == 1)
			{
				t_dirt tile = new t_dirt(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row] = new t_dirt(tileX, tileY, row, isp);
			}
			else if(tileType == 2)
			{
				t_grass tile = new t_grass(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row]  =  new t_grass(tileX, tileY, row, isp);
			}
			else if( tileType == 3)
			{
				t_stone tile = new t_stone(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row] = tile;
			}
			else if( tileType == 4)
			{
				t_iron tile = new t_iron(tileX, tileY, row, isp);
				chunkTiles.add(tile);	
				chunkyTiles[x][row] = new t_iron(tileX, tileY, row, isp);			}
			else if( tileType == 5)
			{
				t_silver tile = new t_silver(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row] = tile;
			}
			else if(tileType == 6)
			{
				t_Wood tile = new t_Wood(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row] = new t_Wood(tileX, tileY, row, isp);
			}
			else if (tileType == 9)
			{
				t_Workbench tile = new t_Workbench(tileX, tileY, row, isp);
				chunkTiles.add(tile);
				chunkyTiles[x][row] = new t_Workbench(tileX, tileY, row, isp);
			}
	}

}
