package entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import something.Game;

public class tarpeyProjectile extends Bullet {

	public tarpeyProjectile(float starX, float starY, float tarX, float tarY,Game game)
			throws SlickException {
		super(starX, starY, tarX, tarY, game);
       boolet = new Image("res/bullet.png");
	}
}
