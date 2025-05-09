package org.exolin.citysim.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.Constants;
import org.exolin.citysim.utils.ImageUtils;
import org.exolin.citysim.utils.Utils;

/**
 * Animation with multiple frames.
 * Can also be an unanimated image by just having one frame.
 *
 * @author Thomas
 */
public class Animation
{
    private final String name;
    private final List<String> fileNames;
    private final List<BufferedImage> images;
    private final int animationSpeed;

    public Animation(String name, List<String> fileNames, List<BufferedImage> images, int animationSpeed)
    {
        this.name = name;
        
        if(images.isEmpty())
            throw new IllegalArgumentException("no images");
        
        if(fileNames.size() != images.size())
            throw new IllegalArgumentException();
       
        this.fileNames = List.copyOf(fileNames);
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
        List<String> fileNames = new ArrayList<>(numFrames);
        
        images.add(ImageUtils.loadImage(name));
        fileNames.add(name);
        //add aditional frames (if they exist)
        for(int i=1;i<numFrames;++i)
        {
            String fileName = name+"_"+i;
            images.add(ImageUtils.loadImage(fileName));
            fileNames.add(fileName);
        }
        
        return new Animation(name, fileNames, images, animationSpeed);
    }
    
    public Animation createRotated(String name, int distance)
    {
        return new Animation(
                name,
                Utils.rotate(fileNames, distance),
                Utils.rotate(images, distance),
                animationSpeed
        );
    }
    
    public static Animation createUnanimated(String name)
    {
        return new Animation(name, List.of(name), List.of(ImageUtils.loadImage(name)), 1);
    }
    
    public static Animation createUnanimated(String name, String filename, BufferedImage image)
    {
        return new Animation(name, List.of(filename), List.of(image), 1);
    }

    public List<String> getFileNames()
    {
        return fileNames;
    }
    
    public String getUnaminatedFileName()
    {
        if(fileNames.size() != 1)
            throw new IllegalStateException("not an animation");
        
        return fileNames.getFirst();
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
