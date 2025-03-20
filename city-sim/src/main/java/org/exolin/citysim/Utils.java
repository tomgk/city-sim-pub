package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas
 */
public class Utils
{
    public static BufferedImage loadImage(String name)
    {
        String resourcePath = name+".png";
        URL resource = GamePanel.class.getClassLoader().getResource(resourcePath);
        if(resource == null)
            throw new IllegalArgumentException("not found: resource "+resourcePath);
        
        try{
            return ImageIO.read(resource);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
