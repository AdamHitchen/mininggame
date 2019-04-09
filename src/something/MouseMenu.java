package something;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class MouseMenu {
    Crafting craft;
    Rectangle menu;
    int numLines = 0;
    Game game;
    Vector2f position;

    public MouseMenu(float x, float y, Recipe recipe, Game game) {
        position = new Vector2f(x, y);
        this.craft = craft;
        this.game = game;
    }

    public void render(Graphics g) {
        menu.setLocation(position.x, position.y);
        g.draw(menu);

    }

    public void update(GameContainer gc) {
        position.x = gc.getInput().getAbsoluteMouseX();
        position.y = gc.getInput().getAbsoluteMouseY();
    }
}
