package something;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.awt.*;

public class CraftingButton {
    private Vector2f position;
    Crafting craft;
    Font font;
    TrueTypeFont ttf;
    private boolean uiOpen;
    private Recipe recipe;
    MouseMenu mMenu;
    int offset;
    private Vector2f size;
    Game game;
    String[] menuMessage;
    Rectangle menu;

    public CraftingButton(float x, float y, Recipe recipe, Crafting craft, Game game) {
        font = new Font("Veranda", Font.BOLD, 10);
        ttf = new TrueTypeFont(font, true);
        this.craft = craft;
        this.game = game;
        this.recipe = recipe;
        int xy = 0;
        for (int i = 0; i < recipe.returnMaterials().size(); i++) {
            if (craft.returnItemNames(recipe.returnMaterial(i) - 1).length() > xy) {
                xy = craft.returnItemNames(recipe.returnMaterial(i) - 1).length();
            }
        }
        menu = new Rectangle(x, y, craft.returnItemNames(xy).length() * 12 + 15, recipe.returnMaterials().size() * 24 + 12);
        position = new Vector2f(x, y);
        size = new Vector2f(64, 12);
        menuMessage = new String[recipe.returnMaterials().size()];
        for (int i = 0; i < recipe.returnMaterials().size(); i++) {
            menuMessage[i] = recipe.returnNumMats().get(i) + " " + craft.returnItemNames(recipe.returnMaterial(i) - 1) + "";
        }
    }

    public void setOffset(int off) {
        offset = off;
    }

    public void render(Graphics g, GameContainer gc) {
        float mouseX = gc.getInput().getAbsoluteMouseX();
        float mouseY = gc.getInput().getAbsoluteMouseY();

        if (uiOpen && position.y - offset < craft.returnPosition() + craft.returnSize() && position.y - offset > craft.returnPosition()) {
            if (craft.checkMats(recipe)) {
                g.setColor(Color.green);

                ttf.drawString(position.x, position.y - offset, recipe.returnName(), Color.green);
            } else {
                g.setColor(Color.red);
                ttf.drawString(position.x, position.y - offset, recipe.returnName(), Color.red);
            }
        }
        if (mouseX > position.x && mouseX < position.x + size.x
                && mouseY > position.y - offset && mouseY < position.y - offset + size.y && uiOpen) {
            g.setColor(Color.white);
            g.fill(menu);
            menu.setX(gc.getInput().getAbsoluteMouseX() + 32 - 10);
            menu.setY(gc.getInput().getAbsoluteMouseY() + 4);
            g.draw(menu);
            g.setColor(Color.black);
            for (int i = 0; i < recipe.returnMaterials().size(); i++) {
                g.setColor(Color.black);
                g.drawString(menuMessage[i],
                        gc.getInput().getAbsoluteMouseX() + 32 - 8,
                        gc.getInput().getAbsoluteMouseY() + 4 + i * 12);
            }
        }
    }

    public Recipe returnRecipe() {
        return recipe;
    }

    public Vector2f returnPosition() {
        return position;
    }

    public Vector2f returnSize() {
        return size;
    }

    public void setUiOpen(boolean uiOpen) {
        this.uiOpen = uiOpen;
    }
}
