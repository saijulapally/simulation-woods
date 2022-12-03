/* e-LEMON-ators */

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;

public class SpriteCache 
{
	private static SpriteCache single = new SpriteCache();
	public static SpriteCache get() 
    {
		return single;
	}
	private HashMap sprites = new HashMap();
	public Sprite getSprite(String ref) 
    {
		if (sprites.get(ref) != null) {
			return (Sprite) sprites.get(ref);
		}
		BufferedImage sourceImage = null;
        try {
           sourceImage = ImageIO.read(new File(ref));
        }
        catch (IOException e) {
           e.printStackTrace();
        }

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		image.getGraphics().drawImage(sourceImage,0,0,null);

		Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);
		return sprite;
	}
}

