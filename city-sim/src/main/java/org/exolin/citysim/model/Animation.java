package org.exolin.citysim.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.Constants;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class Animation
{
    private final String name;
    private final List<BufferedImage> images;
    private final int animationSpeed;

    public Animation(String name, List<BufferedImage> images, int animationSpeed)
    {
        this.name = name;
        
        if(images.isEmpty())
            throw new IllegalArgumentException();
       
        this.images = List.copyOf(images);
        this.animationSpeed = animationSpeed;
    }
    
    public static Animation createAnimation(String name, int numFrames)
    {
        return createAnimation(name, numFrames, Constants.DEFAULT_ANIMATION_SPEED);
    }
    
    public static Animation createAnimation(String name, int numFrames, int animationSpeed)
    {
        List<BufferedImage> images = new ArrayList<>(numFrames);
        
        images.add(Utils.loadImage(name));
        //add aditional frames (if they exist)
        for(int i=1;i<numFrames;++i)
            images.add(Utils.loadImage(name+"_"+i));
        
        return new Animation(name, List.copyOf(images), animationSpeed);
    }
    
    public Animation createRotated(String name, int distance)
    {
        return new Animation(name, Utils.rotate(images, distance), animationSpeed);
    }
    
    public static Animation createUnanimated(String name)
    {
        return new Animation(name, List.of(Utils.loadImage(name)), 1);
    }
    
    public static Animation createUnanimated(String name, BufferedImage image)
    {
        return new Animation(name, List.of(image), 1);
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

    public int getAnimationSpeed()
    {
        return animationSpeed;
    }
}
