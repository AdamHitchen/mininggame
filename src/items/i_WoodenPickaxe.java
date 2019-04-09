package items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.Game;
import something.ItemSpawner;

public class i_WoodenPickaxe extends Item {
    public String image = "res/i/Wooden Pickaxe.png";

    public i_WoodenPickaxe(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException {
        super(iGame, x, y, ispi);
        itemImg = new Image(image);
        type = 8;
    }
}
