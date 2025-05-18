package org.exolin.citysim.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas
 */
public class ImageDisplay
{
    public static void showx(BufferedImage image) throws IOException, InterruptedException
    {
        File temp = File.createTempFile("image", ".jpg");
        
        if(!ImageIO.write(image, "png", temp))
            throw new IOException("no formatted found");
            
        System.out.println(temp);
        
        if(Files.size(temp.toPath()) == 0)
            throw new IOException("empty file created");
        
        Process p = new ProcessBuilder("C:\\Program Files\\paint.net\\paintdotnet.exe", temp.toString()).start();
        //Thread.sleep(100000);
    }
}
