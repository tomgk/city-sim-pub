package org.exolin.citysim;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 */
public class Animation
{
    private final List<BufferedImage> images;

    public Animation(List<BufferedImage> images)
    {
        if(images.isEmpty())
            throw new IllegalArgumentException();
       
        this.images = List.copyOf(images);
    }
    
    public static Animation create(String name)
    {
        //todo: add other animation parts
        return new Animation(List.of(Utils.loadImage(name)));
    }
    
    public static Animation ofUnanimated(BufferedImage img)
    {
        return new Animation(List.of(img));
    }

    public Image getImage(int index)
    {
        return images.get(index);
    }
    
    public int getImageCount()
    {
        return images.size();
    }

    public BufferedImage getDefault()
    {
        return images.get(0);
    }

    public static List<Animation> noAnimation(List<String> variants)
    {
        List<BufferedImage> images = variants.stream()
                .map(Utils::loadImage)
                .toList();
        
        return images.stream()
                .map(Animation::ofUnanimated)
                .toList();
    }
}
