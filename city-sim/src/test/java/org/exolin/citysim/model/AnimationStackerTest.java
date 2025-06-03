package org.exolin.citysim.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.zone.ZoneType;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class AnimationStackerTest
{
    private BufferedImage createBufferedImage(Color left, Color right)
    {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g = image.createGraphics();
        try{
            if(left != null)
            {
                g.setColor(left);
                g.fillRect(0, 0, 1, 2);
            }
            if(right != null)
            {
                g.setColor(right);
                g.fillRect(1, 0, 1, 2);
            }
        }finally{
            g.dispose();
        }
        return image;
    }
    
    private void assertImage(BufferedImage img, Color expectedLeft, Color expectedRight)
    {
        Color left = new Color(img.getRGB(0, 0), true);
        Color right = new Color(img.getRGB(1, 0), true);
        
        if(expectedLeft != null)
            assertColorEquals(expectedLeft, left);
        
        if(expectedRight != null)
            assertColorEquals(expectedRight, right);
    }
    
    private static final Color ALPHA = new Color(0, 0, 0, 0);
    
    @Test
    public void testStack_None()
    {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AnimationStacker.stack(List.of());
        });
        assertEquals("not enough to stack", e.getMessage());
    }
    
    @Test
    public void testStack_One()
    {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AnimationStacker.stack(List.of(Zones.destroyed.getImage(ZoneType.Variant.DEFAULT)));
        });
        assertEquals("not enough to stack", e.getMessage());
    }
    
    @Test
    public void testStack_SameSpeedAndFrameCount_2Animations_2Frames() throws IOException, InterruptedException
    {
        Animation a = new Animation(
                "a",
                List.of("a", "a1"),
                List.of(
                        createBufferedImage(Color.red, null),
                        createBufferedImage(Color.yellow, null)
                ),
                100);
        
        //ImageDisplay.show((BufferedImage) a.getDefault());
        assertImage((BufferedImage)a.getDefault(), Color.red, ALPHA);
        
        Animation b = new Animation(
                "b",
                List.of("b", "b1"),
                List.of(
                        createBufferedImage(null, Color.green),
                        createBufferedImage(null, Color.blue)
                ),
                100);
        
        Animation s = AnimationStacker.stack(List.of(a, b));
        assertEquals(100, s.getAnimationSpeed());
        assertEquals(2, s.getImageCount());
        
        ImageIO.write(a.getImage(0), "png", new File("./target/a.png"));
        ImageIO.write(b.getImage(0), "png", new File("./target/b.png"));
        
        for(int i=0;i<s.getImageCount();++i)
            ImageIO.write(s.getImage(i), "png", new File("./target/stacked"+i+".png"));
        
        assertEquals("stacked:a_b", s.getName());
        
        {
            BufferedImage img = s.getImage(0);
            assertImage(img, Color.red, Color.green);
            assertEquals("stacked:a_b", s.getFileName(0));
        }
        
        {
            BufferedImage img = s.getImage(1);
            assertImage(img, Color.yellow, Color.blue);
            assertEquals("stacked:a1_b1", s.getFileName(1));
        }
    }
    
    @Test
    public void testStack_SameSpeedAndFrameCount_2Animation_3Frames() throws IOException, InterruptedException
    {
        Animation a = new Animation(
                "a",
                List.of("a", "a1", "a2"),
                List.of(
                        createBufferedImage(Color.red, null),
                        createBufferedImage(Color.yellow, null),
                        createBufferedImage(Color.orange, null)
                ),
                100);
        
        //ImageDisplay.show((BufferedImage) a.getDefault());
        assertImage((BufferedImage)a.getDefault(), Color.red, ALPHA);
        
        Animation b = new Animation(
                "b",
                List.of("b", "b1", "b2"),
                List.of(
                        createBufferedImage(null, Color.green),
                        createBufferedImage(null, Color.blue),
                        createBufferedImage(null, Color.pink)
                ),
                100);
        
        Animation s = AnimationStacker.stack(List.of(a, b));
        assertEquals(100, s.getAnimationSpeed());
        assertEquals(3, s.getImageCount());
        
        ImageIO.write(a.getImage(0), "png", new File("./target/a.png"));
        ImageIO.write(b.getImage(0), "png", new File("./target/b.png"));
        
        for(int i=0;i<s.getImageCount();++i)
            ImageIO.write(s.getImage(i), "png", new File("./target/stacked"+i+".png"));
        
        assertEquals("stacked:a_b", s.getName());
        
        {
            BufferedImage img = s.getImage(0);
            assertImage(img, Color.red, Color.green);
            assertEquals("stacked:a_b", s.getFileName(0));
        }
        
        {
            BufferedImage img = s.getImage(1);
            assertImage(img, Color.yellow, Color.blue);
            assertEquals("stacked:a1_b1", s.getFileName(1));
        }
        
        {
            BufferedImage img = s.getImage(2);
            assertImage(img, Color.orange, Color.pink);
            assertEquals("stacked:a1_b1", s.getFileName(1));
        }
    }

    private void assertColorEquals(Color expected, Color actual)
    {
        assertEquals(getColorName(expected), getColorName(actual));
    }
    
    private static final Map<Color, String> colorNames = new LinkedHashMap<>();
    
    static
    {
        try{
            for(Field f : Color.class.getFields())
            {
                if(!Modifier.isStatic(f.getModifiers()))
                    continue;
                
                if(f.getType() != Color.class)
                    continue;

                colorNames.merge((Color)f.get(null), f.getName(), (old, newC) -> {
                    //prefer lower case
                    return old.compareTo(newC) > 0 ? old : newC;
                });
            }
        }catch(IllegalAccessException|IllegalArgumentException e){
            throw new RuntimeException(e);
        }
    }
    
    private static String toFullString(Color c)
    {
        return "[r=" + c.getRed() + ",g=" + c.getGreen() + ",b=" + c.getBlue() + ",a=" + c.getAlpha()+ "]";
    }
    
    private static String getColorName(Color c)
    {
        if(c.getAlpha() == 0)
            return "alpha";
        
        return colorNames.computeIfAbsent(c, cc -> toFullString(cc));
    }
    
    public static void main(String[] args)
    {
        colorNames.forEach((k, v) -> System.out.println(k+" = "+v));
    }
}
