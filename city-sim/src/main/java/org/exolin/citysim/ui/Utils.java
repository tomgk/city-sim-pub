package org.exolin.citysim.ui;

import java.awt.Color;
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
    
    public static BufferedImage brighter(BufferedImage source)
    {
        float brightnessPercentage = 1.5f;
        
        BufferedImage bi = new BufferedImage(
                source.getWidth(null),
                source.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        int[] pixel =
        {
            0, 0, 0, 0
        };
        float[] hsbvals =
        {
            0, 0, 0
        };

        bi.getGraphics().drawImage(source, 0, 0, null);

        // recalculare every pixel, changing the brightness
        for (int i = 0; i < bi.getHeight(); i++)
        {
            for (int j = 0; j < bi.getWidth(); j++)
            {

                // get the pixel data
                bi.getRaster().getPixel(j, i, pixel);

                // converts its data to hsb to change brightness
                Color.RGBtoHSB(pixel[0], pixel[1], pixel[2], hsbvals);

                // create a new color with the changed brightness
                Color c = new Color(Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2] * brightnessPercentage));

                // set the new pixel
                bi.getRaster().setPixel(j, i, new int[]
                {
                    c.getRed(), c.getGreen(), c.getBlue(), pixel[3]
                });

            }
        }
        
        return bi;
    }
}
