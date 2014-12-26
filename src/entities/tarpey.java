package entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import something.Game;

public class tarpey {
	private Vector2f position;
	private boolean dead;
	private Image tarpeyFace, tarpeyShoot;
	private int width, height;
	private boolean firing;
	private boolean loaded;
	private Vector2f playerPos;
	private Vector2f tarpeyRot;
	float speed;
	private String[] imageLoc = new String[2];
	private ArrayList tarpeyBullets;
	private float offset;
	int time;
	Game game;
	public tarpey(float x, float y, Game gg) throws SlickException 
	{
		game = gg;
		this.position = new Vector2f(x,y);
		tarpeyRot= new Vector2f(0,0);
		imageLoc[0] = "res/tarpey.png";
		imageLoc[1] = "res/tarpeyShoot.png";
		tarpeyFace = new Image(imageLoc[0]);
		tarpeyShoot = new Image (imageLoc[1]);
		width = tarpeyFace.getWidth();
		height = tarpeyFace.getHeight();
		speed = 0.1f;
		tarpeyBullets = new ArrayList();
	}
	public void render(Graphics g)
	{
		if(loaded)
		{
			
			if(firing)
			{
				tarpeyShoot.draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
			//	tarpeyFace.draw(-500,-500);
			}
			else
			{
				tarpeyFace.draw(position.x-game.cam.camPosX(),position.y-game.cam.camPosY());
			//	tarpeyShoot.draw(-500,-500);
			}
		}
			for(int i = 0; i < tarpeyBullets.size(); i++)
			{
				tarpeyProjectile tarpeyBullet = (tarpeyProjectile) tarpeyBullets.get(i);
				tarpeyBullet.render(g);
			}
	}
	
	public void update(GameContainer gc, int arg0) throws SlickException
	{
		if(position.x > game.cam.camPosX() - 64 && position.x < game.cam.camPosX() + game.getWidth() + 64 && 
		position.y > game.cam.camPosY() - 64 && position.y < game.cam.camPosY() + game.getHeight())
		{
			loaded = true;
		}
		else
		{
			loaded = false;
		}
		if(loaded)
		{
			if(playerPos.x > position.x)
			{
				position.x += speed * arg0;
			}
			else if(playerPos.x < position.x)
			{
				position.x -= speed * arg0;
			}
			if(playerPos.y > position.y)
			{
				position.y += speed * arg0;
			}
			else if (playerPos.y < position.y)
			{
				position.y -= speed * arg0;
			}
			
			tarpeyRot.x = (float) playerPos.x - position.x;
			tarpeyRot.y = (float) playerPos.y - position.y;
			float toTurn = (float) Math.atan2((double)tarpeyRot.y,(double) tarpeyRot.x);
			tarpeyFace.setRotation((float) Math.toDegrees(toTurn-0.4));
			tarpeyShoot.setRotation((float) Math.toDegrees(toTurn-0.4));
			
			time++;
			if(time == 200)
			{
				firing = true;
				tarpeyProjectile tarpeyBullet = new tarpeyProjectile(position.x,position.y,playerPos.x,playerPos.y,game);
				tarpeyBullets.add(tarpeyBullet);
				
			}
			else if(time > 225)
			{
				firing = false;
				time = 0;
			}
			for(int i = 0; i < tarpeyBullets.size(); i++)
			{
				tarpeyProjectile tarpeyBullety = (tarpeyProjectile) tarpeyBullets.get(i);
				tarpeyBullety.update(gc, arg0);
			}
		}
	}
	
	public void setOffset(float in)
	{
		offset = in;
	}
	public float getOffset()
	{
		return offset;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setPPos(Vector2f i)
	{
		playerPos = i;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	public Image getFace()
	{
		return tarpeyFace;
	}
	public boolean isDead() {
		return dead;
	}
	public void die()
	{
		position.x += 500;
	}
	public void setDead(boolean d) {
		dead = d;
	}
}
