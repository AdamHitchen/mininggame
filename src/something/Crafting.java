package something;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Crafting {
	private XMLReader xml;
	private Game game;
	private Inventory inv;
	private ArrayList<Recipe> recipes;
	int waitCraft = 0;
	private ArrayList<CraftingButton> buttons;
	private Image craftUI;
	private Vector2f position;
	MouseMenu mMenu;
	private int offsetMax, offset;
	public static String currentDirectory = new File("").getAbsolutePath();

	private boolean uiOpen = false;
	private String[] itemNames;
	public Crafting(Game gm, Inventory inv) throws SlickException
	{
		game = gm;
		this.inv = inv;
		buttons = new ArrayList<CraftingButton>();
		XMLReader xml = new XMLReader();
		recipes = xml.readConfig(inv.currentDirectory + "/res/recipes.xml");
		craftUI = new Image("res/ui/uiCrafting.png");
		displayRecipes();
		itemNames = new String[game.inv.returnID()];
		try(BufferedReader br = new BufferedReader(new FileReader(currentDirectory + "/res/names.txt"))) {
			int i = 0;
		    for(String line; (line = br.readLine()) != null; ) {
		        itemNames[i] = line;
		        i++;}}catch(Exception e){System.out.println(e);}
		position = new Vector2f(40,104);
		for(int i = 0; i < recipes.size(); i++)
		{
			CraftingButton button = new CraftingButton(50, 104 + 24 + 16 * i, recipes.get(i), this, game);
			buttons.add(button);
		}
		offsetMax = recipes.size() * 16 - craftUI.getHeight();

	}
	public String returnItemNames(int i)
	{
		return itemNames[i];
	}
	public boolean returnUI()
	{
		return uiOpen;
	}
	public float returnPosition()
	{
		return position.y;
	}
	public float returnSize()
	{
		return craftUI.getHeight();
	}
	public boolean checkMats(Recipe recipe)
	{
		List<Integer> mats = (List) recipe.returnMaterials();
		List<Integer> numMats = (List) recipe.returnNumMats();
		boolean ret = true;
		for(int i = 0; i < mats.size(); i++)
		{
			if(inv.haveItem(mats.get(i)) && inv.returnNum(i) >= numMats.get(i))
			{
				
			}
			else
			{
				ret = false;
			}
		}
		return ret;
	}
	public void craftItem(Recipe recipe)
	{
		List<Integer> mats = (List) recipe.returnMaterials();
		List<Integer> numMats = (List) recipe.returnNumMats();
		if(checkMats(recipe) && inv.haveSpace(recipe.returnResult()))
		{
			inv.addItem(recipe.returnResult(), recipe.returnResultQ());
			for(int i = 0; i < mats.size(); i++)
			{
				inv.removeItem(mats.get(i), numMats.get(i));
			}
		}
	}
	public void update(GameContainer gc)
	{
		float mouseX = gc.getInput().getAbsoluteMouseX();
		float mouseY = gc.getInput().getAbsoluteMouseY();
		if(game.returnDwheel() < 0 && offset < offsetMax)
		{
			offset+=10;
		}
		else if(game.returnDwheel() > 0 && offset > 0)
		{
			offset-=10;
		}
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE) & waitCraft > 20)
		{
			uiOpen ^= true;
			waitCraft = 0;
		}
		waitCraft++;
		if(uiOpen)
		{
			for(int i = 0; i < buttons.size(); i ++)
			{
				CraftingButton button = (CraftingButton) buttons.get(i);
				if(mouseX > button.returnPosition().x && mouseX < button.returnPosition().x + button.returnSize().x
						&& mouseY>button.returnPosition().y - offset && mouseY < button.returnPosition().y - offset + button.returnSize().y)
				{
					if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
					{
						craftItem(button.returnRecipe());
					}
					
					
				}
			}
		}
		
	}
	public void craftItem(List<Integer> mats, List<Integer> numMats)
	{
		for(int i = 0; i < mats.size(); i++)
		{
			inv.removeItem(mats.get(i), numMats.get(i));
	
		}
	}
	public void render(Graphics g, GameContainer gc)
	{
		
		if(uiOpen)
		{
			g.setDrawMode(g.MODE_ALPHA_MAP);
			craftUI.setColor(0, 1f, 1f, 1, 0.4f);
			craftUI.setColor(1, 1f, 1f, 1, 0.4f);
			craftUI.setColor(2, 1f, 1f, 1, 0.4f);
			craftUI.setColor(3, 1f, 1f, 1, 0.4f);
			craftUI.draw(position.x, position.y);
			g.setDrawMode(g.MODE_NORMAL);
			g.setDrawMode(g.MODE_ALPHA_BLEND);
			craftUI.draw(position.x, position.y);
			g.setDrawMode(g.MODE_NORMAL);
		}
		for(int i = 0; i < buttons.size(); i ++)
		{
			CraftingButton button = (CraftingButton) buttons.get(i);
			button.setUiOpen(uiOpen);
			button.setOffset(offset);
			button.render(g, gc);
		}

	}
	public void displayRecipes()
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			Recipe recipe = (Recipe) recipes.get(i);
			List mats = (List) recipe.returnMaterials();
			List numMats = (List) recipe.returnNumMats();
			
		}
	}
}
