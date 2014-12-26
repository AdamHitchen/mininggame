package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import something.Game;

public class Bullet
{
    float tarX = 0;
    float tarY = 0;
    protected Vector2f velocity;
    protected Vector2f position;
    float speed = 0.3f; //how fast this moves.
    float deg;
    float dx;
    float dy;
    protected Image boolet;
    Game game;
    public Bullet(float starX, float starY, float tarX, float tarY, Game gg) throws SlickException
    {
    	game = gg;
    	velocity = new Vector2f(0,0);
    	position = new Vector2f(starX,starY);
        speed = 0.3f;
		Double angle = Math.atan2((tarY - starY), (tarX - starX));
		velocity.x = (float) (this.speed*Math.cos(angle));
		velocity.y = (float) (this.speed*Math.sin(angle));

       boolet = new Image("res/bullet.png");
       

    }
    
	public Image getFace()
	{
		return boolet;
	}
    public void init()
    {
    	
    }
    public float returnDX()
    {
    	return dx;
    }
    public Vector2f returnPosition()
    {
    	return position;
    }
	public void render(Graphics g)
	{
		boolet.draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
	}
    /**
     * Calculates a new vector based on the input destination X and Y.
     * @param tarX
     * @param tarY
     */

    public void update(GameContainer gc, int arg0)
    {
		//float nextX = position.x +velocity.x*arg0;
		//float nextY = position.y +velocity.y*arg0;
		position.x = position.x + velocity.x*arg0;
		position.y = position.y + velocity.y*arg0;
	
    }
}
