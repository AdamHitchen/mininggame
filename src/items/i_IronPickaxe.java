package items;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.ItemSpawner;
import something.Game;
public class i_IronPickaxe extends Item{ 
	public String image = "res/i/IronPickaxe.png"; 
	public i_IronPickaxe(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException
	{
		super(iGame, x, y, ispi);
		itemImg = new Image(image);
		type = 11;
	}
}
