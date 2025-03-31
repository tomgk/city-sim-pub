package org.exolin.citysim.se;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas
 */
public class SpriteExtractor
{
    public static void extract(BufferedImage img)
    {
        for(int y=0;y<img.getHeight();++y)
        {
            for(int x=0;x<img.getWidth();++x)
            {
                Color c = new Color(img.getRGB(x, y), true);
                if(c.getAlpha() > 128)
                    System.out.println(x+"/"+y);
            }
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        BufferedImage img = ImageIO.read(new File("sprite_collection/Terrain.png"));
        extract(img);
    }
}
