package something;
//file reader is not reading the file locations properly: writes all locations to one string. Otherwise code is working;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Font;
import java.awt.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Inventory {
private Game game;
	Font font;
	TrueTypeFont ttf;
	private int maxInv = 28;
	private int imageDragged;
	public int[] inventoryContents = new int[maxInv];
	boolean mousePressed = false;
	public int[] inventorySize = new int[maxInv];
	private Image[] itemImages;
	private Image[] uiImages;
	int arraySize;
	int imageLoc = 0;
	private float mouseX, mouseY;
	private boolean dragImage;
	private Image uiImage;
	Camera cam;
	public static String currentDirectory = new File("").getAbsolutePath();
	private ArrayList<Integer> miningTools, miningStrength;
	public enum Placeable {
		dirt, grass, stone
	}
	
	public Inventory(Game gm, Camera cam1) throws SlickException
	{
		miningTools = new ArrayList<Integer>();
		miningStrength = new ArrayList<Integer>();
		miningTools.add(8);
		miningTools.add(10);
		miningTools.add(11);
		miningStrength.add(5);
		miningStrength.add(8);
		miningStrength.add(12);
		dragImage = false;
		font = new Font("Veranda", Font.BOLD, 10);
		ttf = new TrueTypeFont((java.awt.Font) font, true);
		ClassLoader classLoader = getClass().getClassLoader();
		
		try{
			
   	 	String id = new Scanner( new File(currentDirectory + "\\res\\id.txt")).useDelimiter("\\A").next();
   	 	System.out.println(id);
   	 	arraySize =Integer.parseInt(id) -1; 
   	 	
		}catch(Exception i){System.out.println(i);}
		System.out.println(arraySize);
		game = gm;
		itemImages = new Image[arraySize];
		String desu = "";
/*		for (int i = 0; i < arraySize; i ++)
		{
			try{
	        	 String id = new Scanner( new File("K:\\workspace\\something\\res\\items.txt")).useDelimiter("\\A").next();
	        	 desu = id;
	        	
			}catch(Exception ex){System.out.println(ex);}
			itemImages[i] = new Image(desu);
		}*/
		try(BufferedReader br = new BufferedReader(new FileReader(currentDirectory + "\\res\\items.txt"))) {
			int i = 0;
		    for(String line; (line = br.readLine()) != null; ) {
		        itemImages[i] = new Image(line);
		        i++;
		    }
		    // line is not visible here.
		}catch(Exception e){System.out.println(e);}
		uiImages = new Image[20];
		cam = cam1;
		for(int i = 0; i < 9; i ++)
		{
			uiImages[i] = new Image("res/ui/uiSquare.png");
		}
		addItem(0, 0);
		addItem(6,4);
		addItem(6,4);
		addItem(10,1);
	}
	public int returnID()
	{
		return arraySize;
	}
	public int activeID(int id)
	{
		return inventoryContents[id];
	}
	public ArrayList<Integer> returnMiningTools()
	{
		return miningTools;
	}
	public ArrayList<Integer> returnMiningStrength()
	{
		return miningStrength;
	}
	public void render(Graphics g)
	{
		for(int i = 0; i < 9; i ++)
		{
			if(game.getSelection() -1 != i)
			{
				g.setDrawMode(g.MODE_ALPHA_MAP);
				uiImages[i].setColor(0, 1f, 1f, 1, 0.4f);
				uiImages[i].setColor(1, 1f, 1f, 1, 0.4f);
				uiImages[i].setColor(2, 1f, 1f, 1, 0.4f);
				uiImages[i].setColor(3, 1f, 1f, 1, 0.4f);
				uiImages[i].draw(40 + i*48,40);
				g.setDrawMode(g.MODE_NORMAL);
				g.setDrawMode(g.MODE_ALPHA_BLEND);
				uiImages[i].draw(40 + i*48,40 );
				g.setDrawMode(g.MODE_NORMAL);
			}
			else
			{
				g.setDrawMode(g.MODE_ALPHA_MAP);
				uiImages[i].setColor(0, 1f, 1f, 1, 10f);
				uiImages[i].setColor(1, 1f, 1f, 1, 10f);
				uiImages[i].setColor(2, 1f, 1f, 1, 10f);
				uiImages[i].setColor(3, 1f, 1f, 1, 10f);
				uiImages[i].draw(40 + i*48,40);
				g.setDrawMode(g.MODE_NORMAL);
				g.setDrawMode(g.MODE_ALPHA_BLEND);
				uiImages[i].draw(40 + i*48,40);
				g.setDrawMode(g.MODE_NORMAL);
			} 
		
		}

		if(!dragImage)
		{
			for(int i = 0; i < arraySize && i < 9; i ++)
			{

				if(inventoryContents[i] != 0)
				{
					itemImages[inventoryContents[i] -1].draw(40 + 8 + i*48,40 + 8);
					ttf.drawString(40 + i * 48, 56, ""+inventorySize[i]);
				}
	
				
			}
			imageDragged = 0;
		}
		else if(dragImage)
		{
			if(imageDragged <= arraySize&&imageDragged > 0)
			{
				itemImages[imageDragged-1].draw(mouseX-12, mouseY-12);
				ttf.drawString(mouseX-12, mouseY+12, ""+inventorySize[imageDragged-1]);

			}
			for(int i = 0; i < arraySize && i < 9; i ++)
			{
				if(inventoryContents[i] != 0 && inventoryContents[i] != imageDragged)
				{
					itemImages[inventoryContents[i] -1].draw(40+12 + i*48,40+12);
					ttf.drawString(40 + i * 48, 56, ""+inventorySize[i]);
				}


			}
		}
		//uiImage.draw(40 + cam.camPosX(), 40 + cam.camPosY());
	}
	
	public void update(GameContainer gc, int arg1)
	{
		
		mouseX = gc.getInput().getAbsoluteMouseX();
		mouseY = gc.getInput().getAbsoluteMouseY();
		boolean mouseDown = gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		if(mouseDown && !mousePressed)
		{
			for(int i = 0; i < 9; i++)
			{
				if(mouseX > 40 + i*48 && mouseX < 40 + i*48 + 32 && mouseY > 40 && mouseY < 40 + 32)
				{
					dragImage = true;
					imageDragged = inventoryContents[i];
					imageLoc = i;
					mousePressed = true;

				}
			}
		
		}
		if(!mouseDown && mousePressed)
		{
			boolean swapped = false;
			for(int i = 0; i < 9; i++)
			{
				if(mouseX > 40 + i*48 && mouseX < 40 + i*48 + 32 && mouseY > 40 && mouseY < 40 + 32 && !swapped)
				{
					int tempContent = inventoryContents[i];
					int tempSize = inventorySize[i];
					inventoryContents[i] = inventoryContents[imageLoc];
					inventorySize[i] = inventorySize[imageLoc];
					inventoryContents[imageLoc] = tempContent;
					inventorySize[imageLoc] = tempSize;
					swapped = true;
				}
			}
			mousePressed = false;
			dragImage =false;

		}


	}
	public void addItem(int item)
	{
		boolean foundItem = false;
		for(int i = 0; i < maxInv; i++)
		{
			if(inventoryContents[i] == item)
			{
				inventorySize[i]++;
				foundItem = true;
			}
		}
		if(!foundItem)
		{
			for(int i = 0; i < maxInv; i++)
			{
				if(inventoryContents[i] == 0 && !foundItem)
				{
					inventoryContents[i] = item;
					inventorySize[i]++;
					foundItem^=true;
				}
			}
		}
	}
	public void addItem(int item, int numItem)
	{
		boolean foundItem = false;
		for(int i = 0; i < maxInv; i++)
		{
			if(inventoryContents[i] == item)
			{
				inventorySize[i]+=numItem;
				foundItem = true;
			}
		}
		if(!foundItem)
		{
			for(int i = 0; i < maxInv; i++)
			{
				if(inventoryContents[i] == 0 && !foundItem)
				{
					inventorySize[i] = 0;
					inventoryContents[i] = item;
					inventorySize[i]=numItem;
					foundItem=true;
				}
			}
		}
	}
	public int returnNum(int item)
	{
		return inventorySize[item];
	}
	public void removeItem(int item)
	{
		for(int i = 0; i< maxInv; i++)
		{
			if(inventoryContents[i] == item)
			{
				inventorySize[i]--;
				if(inventorySize[i] == 0)inventoryContents[i]=0;
			}
		}
	}

	public void removeItem(int item, int numItem)
	{
		boolean itemRemoved = false;
		for(int i = 0; i< maxInv; i++)
		{
			if(inventoryContents[i] == item && !itemRemoved)
			{
				inventorySize[i]-=numItem;
				if(inventorySize[i] <= 0)
				{
					inventoryContents[i]=0;
					inventorySize[i] = 0;
				}
				itemRemoved = true;
			}
		}
	}
	public boolean haveSpace(int item)
	{
		for(int i = 0; i < maxInv; i++)
		{
			if(inventoryContents[i] == 0 || inventoryContents[i] == item)
			{
				return true;
			}
		}	
		
		return false;
	}
	
	public boolean haveItem(int item)
	{
		for(int i = 0; i < maxInv; i++)
		{
			if(inventoryContents[i] == item && inventorySize[i] > 0)
			{
				return true;
			}
		}
		
		
		return false;
	}
}
