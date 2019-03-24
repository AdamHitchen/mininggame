package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import something.Game;

public class tarpeyProjectile extends Bullet {

    public tarpeyProjectile(float starX, float starY, float tarX, float tarY, Game game)
            throws SlickException {
        super(starX, starY, tarX, tarY, game);
        boolet = new Image("res/bullet.png");
    }
}
