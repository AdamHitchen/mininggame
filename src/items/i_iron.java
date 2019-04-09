package items;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.Game;
import something.ItemSpawner;

public class i_iron extends Item {
    public String image = "res/i/iron.png";

    public i_iron(Game iGame, float x, float y, ItemSpawner ispi) throws SlickException {
        super(iGame, x, y, ispi);
        type = 4;
        itemImg = new Image("res/i/iron.png");
    }
	/*public void render(Graphics g)
	{
		itemImg.draw(position.x, position.y);
	}*/

}
