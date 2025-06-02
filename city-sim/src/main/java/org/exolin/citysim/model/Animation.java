package org.exolin.citysim.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.exolin.citysim.Constants;
import static org.exolin.citysim.Constants.DEFAULT_NONANIMATION_SPEED;
import org.exolin.citysim.utils.ImageUtils;
import org.exolin.citysim.utils.MathUtils;
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
        return new Animation(name, List.of(name), List.of(ImageUtils.loadImage(name)), DEFAULT_NONANIMATION_SPEED);
    }
    
    public static Animation createUnanimated(String name, String filename, BufferedImage image)
    {
        return new Animation(name, List.of(filename), List.of(image), 1);
    }
    
    /**
     * @return total length of animation before it starts again
     */
    private int getAnimationLength()
    {
        return animationSpeed * images.size();
    }
    
    //TODO: remove after n stacks are supported
    private static class StackCall
    {
        private final Animation a;
        private final Animation b;
        private final List<Animation> animations;

        public StackCall(Animation a, Animation b)
        {
            this.a = a;
            this.b = b;
            this.animations = List.of(a, b);
        }
        
        public int getAnimationLength()
        {
            return MathUtils.lcm(animations.stream().mapToInt(Animation::getAnimationLength));
        }
        
        public int getAnimationSpeed()
        {
            return MathUtils.gcd(animations.stream().mapToInt(a -> a.animationSpeed));
        }
        
        public String getStackedName(int time)
        {
            return Animation.getStackedName(a.getFileNameAt(time), b.getFileNameAt(time));
        }
        
        public BufferedImage stackImages(int time)
        {
            return Animation.stackImages(a.getImageAt(time), b.getImageAt(time));
        }
    }
    
    /**
     * Creates an animation where two animations are played.
     * <s>In case the animations don't have the same speed, the resulting animation
     * will have lowest common multiple number of frames.
     * In case the frame count is the same, the resulting animation will also
     * have the same number of frames</s>
     * 
     * @param a first animation
     * @param b second animation
     * @return 
     */
    public static Animation stack(Animation a, Animation b)
    {
        StackCall call = new StackCall(a, b);
        
        //LCM before both animations line up again
        //=> that's the minimum length to create a stacked animation
        int animationLength = call.getAnimationLength();
        
        //every GCD either (or both) animation have a new frame
        //=> need to create a new frame in the combined animation
        int animationSpeed = call.getAnimationSpeed();
        
        int frameCount = animationLength/animationSpeed;
        
        List<BufferedImage> images = new ArrayList<>(frameCount);
        List<String> filenames = new ArrayList<>(frameCount);
        
        for(int i=0;i<frameCount;++i)
        {
            int time = i * animationSpeed;
            filenames.add(call.getStackedName(time));
            System.out.println("@"+i+": Combine "+a.getFileNameAt(time)+" and "+b.getFileNameAt(time));
            images.add(call.stackImages(time));
        }
        
        String name = getStackedName(a.name, b.name);
        
        return new Animation(name, filenames, images, animationSpeed);
    }
    
    private static String getStackedName(String a, String b)
    {
        return "stacked:"+a+"_"+b;
    }
    
    public static BufferedImage stackImages(Image a, Image b)
    {
        int wa = a.getWidth(null);
        int wb = b.getWidth(null);
        if(wa != wb)
            throw new IllegalArgumentException();
        int w = wa;
        
        int ha = a.getHeight(null);
        int hb = b.getHeight(null);
        
        int h = Math.max(ha, hb);
        
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g = image.createGraphics();
        try{
            g.drawImage(a, 0, h - ha, null);
            g.drawImage(b, 0, h - hb, null);
        }finally{
            g.dispose();
        }
        
        return image;
    }

    public List<String> getFileNames()
    {
        return fileNames;
    }
    
    public String getFileNameAt(long time)
    {
        return fileNames.get(getFrame(time));
    }
    
    public String getUnaminatedFileName()
    {
        if(fileNames.size() != 1)
            throw new IllegalStateException("not an unanimation");
        
        return fileNames.getFirst();
    }
    
    public String getName()
    {
        return name;
    }

    public BufferedImage getImage(int index)
    {
        return images.get(index);
    }
    
    private int getFrame(long time)
    {
        return (int)(time/getAnimationSpeed()%getImageCount());
    }
    
    public BufferedImage getImageAt(long time)
    {
        return getImage(getFrame(time));
    }
    
    public int getImageCount()
    {
        return images.size();
    }

    public BufferedImage getDefault()
    {
        return images.get(0);
    }

    private static List<Animation> noAnimation(List<String> variants)
    {
        return variants.stream()
                .map(Animation::createUnanimated)
                .toList();
    }

    public int getAnimationSpeed()
    {
        return animationSpeed;
    }

    @Override
    public String toString()
    {
        return getClass().getName()+"[name="+name+",animationSpeed="+animationSpeed+"]";
    }
}
