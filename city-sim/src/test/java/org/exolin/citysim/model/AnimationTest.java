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
import org.exolin.citysim.Constants;
import static org.exolin.citysim.Constants.DEFAULT_NONANIMATION_SPEED;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class AnimationTest
{
    @Test
    public void testCreateAnimated()
    {
        Animation a = createAnimation("plants/gas_plant", 8);
        assertEquals(List.of(
                "plants/gas_plant",
                "plants/gas_plant_1",
                "plants/gas_plant_2",
                "plants/gas_plant_3",
                "plants/gas_plant_4",
                "plants/gas_plant_5",
                "plants/gas_plant_6",
                "plants/gas_plant_7"
        ), a.getFileNames());
        
        assertEquals(Constants.DEFAULT_ANIMATION_SPEED, a.getAnimationSpeed());
        assertEquals("plants/gas_plant", a.getName());
        assertEquals(8, a.getImageCount());
        
        try{
            a.getUnaminatedFileName();
            fail();
        }catch(IllegalStateException e){
            assertEquals("not an unanimation", e.getMessage());
        }
    }
    
    private void testUnanimated(String name)
    {
        Animation a = Animation.createUnanimated(name);
        assertEquals(List.of(name), a.getFileNames());
        assertEquals(name, a.getUnaminatedFileName());
        assertEquals(DEFAULT_NONANIMATION_SPEED, a.getAnimationSpeed());
        assertEquals(name, a.getName());
        assertEquals(1, a.getImageCount());
    }
    
    @Test
    public void createUnanimated()
    {
        testUnanimated("plants/gas_plant");
    }
    
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
    public void testStack_SameSpeedAndFrameCount() throws IOException, InterruptedException
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
        
        Animation s = Animation.stack(a, b);
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
