package org.exolin.citysim.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.exolin.citysim.utils.MathUtils;

/**
 *
 * @author Thomas
 */
public class AnimationStacker
{
    //TODO: remove after n stacks are supported
    private static class StackCall
    {
        private final List<Animation> animations;

        public StackCall(List<Animation> animations)
        {
            this.animations = Objects.requireNonNull(animations);
        }
        
        public int getAnimationLength()
        {
            return MathUtils.lcm(animations.stream().mapToInt(Animation::getAnimationLength));
        }
        
        public int getAnimationSpeed()
        {
            return MathUtils.gcd(animations.stream().mapToInt(Animation::getAnimationSpeed));
        }
        
        public String getStackedName(int time)
        {
            return "stacked:"+
                    animations.stream().map(a -> a.getFileNameAt(time)).collect(Collectors.joining("_"));
        }
        
        public BufferedImage stackImages(int time)
        {
            List<BufferedImage> imagesForFrame = animations.stream()
                    .map(img -> img.getImageAt(time))
                    .toList();
            
            return AnimationStacker.stackImages(imagesForFrame);
        }
    }
    
    private static final boolean DEBUG = false;
    
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
    public static Animation stack(List<Animation> animations)
    {
        if(animations.size() < 2)
            throw new IllegalArgumentException("not enough to stack");
        
        StackCall call = new StackCall(animations);
        
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
            if(DEBUG) System.out.println("Creating "+filenames.getLast());
            images.add(call.stackImages(time));
        }
        
        String name = call.getStackedName(0);
        
        return new Animation(name, filenames, images, animationSpeed);
    }
    
    private static BufferedImage stackImages(List<BufferedImage> images)
    {
        int[] widths = images.stream()
                .mapToInt(b -> b.getWidth())
                .distinct()
                .toArray();
        if(widths.length != 1)
            throw new IllegalArgumentException();
        int w = widths[0];
        
        int h = images.stream()
                .mapToInt(img -> img.getHeight())
                .max().getAsInt();
        
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g = image.createGraphics();
        try{
            for(BufferedImage a: images)
                g.drawImage(a, 0, h - a.getHeight(), null);
        }finally{
            g.dispose();
        }
        
        return image;
    }
}
