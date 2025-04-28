package org.exolin.citysim.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.exolin.citysim.ui.GamePanel;

/**
 *
 * @author Thomas
 */
public class ImageUtils
{
    public static final String RESOURCE_PREFIX = "org/exolin/citysim/";
    
    public static BufferedImage loadImage(String name)
    {
        String resourcePath = RESOURCE_PREFIX+name+".png";
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

    public static Image removeGround(BufferedImage image)
    {
        ImageFilter filter = new RGBImageFilter()
        {
            @Override
            public final int filterRGB(int x, int y, int rgb)
            {
                //System.out.println(Integer.toHexString(rgb));
                
                if(rgb == 0xFF988747)
                    return 0x00000000;
                
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static BufferedImage createOffsetImage(BufferedImage image, int xoffset, int yoffset)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, xoffset, yoffset, null);
        g2d.dispose();

        return newImage;
    }

    public static BufferedImage loadDeadImage(String string)
    {
        BufferedImage image = loadImage(string);
        
        ImageFilter filter = new RGBImageFilter()
        {
            @Override
            public final int filterRGB(int x, int y, int rgb)
            {
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                
                int Red = (r << 16) & 0xFF0000; //Shift red 16-bits and mask out other stuff
                int Green = (g << 8) & 0x00FF00; //Shift Green 8-bits and mask out other stuff
                int Blue = b & 0x0000FF; //Mask out anything not blue.
                
                return Red | Green | Blue;
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }
    
    private static BufferedImage toBufferedImage(Image img)
    {
        Objects.requireNonNull(img);
        
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
