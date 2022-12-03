

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity 
{
	private double xPos, yPos;
	private Sprite sprite;

	private Rectangle me = new Rectangle();
	private Rectangle him = new Rectangle();
   
	public Entity(int x,int y, String ref) 
    {
		this.sprite = SpriteCache.get().getSprite(ref);
		this.xPos = x;
		this.yPos = y;
	} 
	public void draw(Graphics g) 
    { 
		sprite.draw(g,(int) xPos,(int) yPos);
	}
	public int getX() 
    {
		return (int) xPos;
	}
	public int getY() 
    {
		return (int) yPos;
	}
    public void setX(int x) 
    {
		xPos = x;
	}
	public void setY(int y) 
    {
		yPos = y;
	}
	public boolean collidesWith(Entity other) 
    {
		me.setBounds((int) xPos,(int) yPos, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.xPos,(int) other.yPos,other.sprite.getWidth(), other.sprite.getHeight());
		return me.intersects(him);
	}
    public boolean collidesWith(Entity other, int width) 
    {
		me.setBounds((int) xPos,(int) yPos,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.xPos,(int) other.yPos, width, other.sprite.getHeight());
		return me.intersects(him);
	}
    public boolean collidesWith(Entity other, int xOffset, int yOffset, int width, int height) 
    { 
		me.setBounds((int) xPos,(int) yPos,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.xPos + xOffset, (int) other.yPos + yOffset, width, height);
		return me.intersects(him);
	}
    public void setSprite(String ref)
    {
        this.sprite = SpriteCache.get().getSprite(ref);
    }
    public abstract String collidedWith(Entity other);  
}

