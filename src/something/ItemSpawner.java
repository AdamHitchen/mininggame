package something;

import items.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

public class ItemSpawner {
    public ArrayList items;
    private int noItems;
    Game game;

    ItemSpawner(Game iGame) {
        items = new ArrayList();
        game = iGame;
        //items = new ArrayList();
    }

    public void render(Graphics g) {
        for (int i = 0; i < items.size(); i++) {
            Item item = (Item) items.get(i);
            item.render(g);
        }
    }

    public void update(GameContainer gc, int arg1) {
        for (int i = 0; i < items.size(); i++) {
            Item item = (Item) items.get(i);
            item.update(gc, arg1);
        }
    }

    public Game getGame() {
        return game;
    }

    public void destroyItem(Item item, int tileID) {
        int tileid = tileID;
        for (int i = tileid; i < items.size(); i++) {
            Item iitem = (Item) items.get(i);
            iitem.setID(i - 1);

        }
        item = null;
        items.remove(tileID);

    }

    public void spawnItem(int type, float x1, float y1) throws SlickException {
        if (type == 1) {
            i_dirt item = new i_dirt(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 2) {
            i_grass item = new i_grass(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 3) {
            i_stone item = new i_stone(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 4) {
            i_iron item = new i_iron(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 6) {
            i_Wood item = new i_Wood(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 7) {
            i_Sticks item = new i_Sticks(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 8) {
            i_WoodenPickaxe item = new i_WoodenPickaxe(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 9) {
            i_Workbench item = new i_Workbench(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        } else if (type == 10) {
            i_StonePickaxe item = new i_StonePickaxe(game, x1, y1, this);
            items.add(item);
            item.setID(items.size() - 1);
            noItems++;
        }

    }

}