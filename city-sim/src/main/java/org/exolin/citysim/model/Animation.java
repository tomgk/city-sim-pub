package org.exolin.citysim.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 */
public class Animation
{
    private final String name;
    private final List<BufferedImage> images;

    public Animation(String name, List<BufferedImage> images)
    {
        this.name = name;
        
        if(images.isEmpty())
            throw new IllegalArgumentException();
       
        this.images = List.copyOf(images);
    }
    
    public static Animation create(String name, int numFrames)
    {
        List<BufferedImage> images = new ArrayList<>(numFrames);
        
        images.add(Utils.loadImage(name));
        //add aditional frames (if they exist)
        for(int i=1;i<numFrames;++i)
            images.add(Utils.loadImage(name+"_"+i));
        
        return new Animation(name, List.copyOf(images));
    }
    
    public static Animation createUnanimated(String name)
    {
        return new Animation(name, List.of(Utils.loadImage(name)));
    }

    public String getName()
    {
        return name;
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
        return variants.stream()
                .map(Animation::createUnanimated)
                .toList();
    }
}
