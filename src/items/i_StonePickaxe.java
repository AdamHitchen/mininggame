package items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.Game;
import something.ItemSpawner;

public class i_StonePickaxe extends Item {
    public String image = "res/i/StonePickaxe.png";

    public i_StonePickaxe(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException {
        super(iGame, x, y, ispi);
        itemImg = new Image(image);
        type = 10;
    }
}
