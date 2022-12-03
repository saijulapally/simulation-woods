

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
 
public class Background 
{
   private BufferedImage image;
   private final int FRAME = 600;
   private int xPos;
   private int yPos;
 
   public Background(int x, int y) 
   {
      xPos = x;
      yPos = y;
 
      try {
         image = ImageIO.read(new File("sprites/background.png"));
      }
      catch (Exception e) {
         System.out.println("Error-Background");
      }
   }
   public void draw(Graphics g) 
   {
      g.drawImage(image, getX(), getY(), image.getWidth(), image.getHeight(), null);
      xPos -= 1;
      if (xPos <= -FRAME) 
         xPos = xPos + image.getWidth() * 2;
   }
 
   public void setX(int x) 
   {
      xPos = x;
   }
   public void setY(int y) 
   {
      yPos = y;
   }
   public int getX() 
   {
      return xPos;
   }
   public int getY() 
   {
      return yPos;
   }
}