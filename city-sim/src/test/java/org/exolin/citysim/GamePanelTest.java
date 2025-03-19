package org.exolin.citysim;

import java.awt.Point;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class GamePanelTest
{
    @Test
    public void testTranform_00()
    {
        Point p = new Point(-1, -1);
        GamePanel.transform(100, 0, 0, p);
        Assertions.assertEquals(new Point(50, 0), p);
    }
    
    @Test
    public void testTranform_w0()
    {
        Point p = new Point(-1, -1);
        GamePanel.transform(100, 100, 0, p);
        Assertions.assertEquals(new Point(0, 25), p);
    }
    
    @Test
    public void testTranform_0h()
    {
        Point p = new Point(-1, -1);
        GamePanel.transform(100, 0, 100, p);
        Assertions.assertEquals(new Point(100, 25), p);
    }
    
    @Test
    public void testTranform_wh()
    {
        Point p = new Point(-1, -1);
        GamePanel.transform(100, 100, 100, p);
        Assertions.assertEquals(new Point(50, 50), p);
    }
    
    @Test
    public void getImage()
    {
        URL url = GamePanel.class.getClassLoader().getResource("office.png");
        assertNotNull(url);
    }
}
