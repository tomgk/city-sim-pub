package org.exolin.citysim;

import java.awt.Point;
import java.net.URL;
import javax.swing.JFrame;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class GamePanelTest
{
    private static final GamePanel gamePanel = new GamePanel(World.World1(), new JFrame(), new GamePanelListener(){
        @Override
        public void created(GamePanel panel)
        {
        }

        @Override
        public void zoomChanged(int zoom, double zoomFactor)
        {
        }
        
        @Override
        public void offsetChanged(int xoffset, int yoffset)
        {
        }
        
    });
    
    @Test
    public void testTranform_00()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 0, 0, p);
        Assertions.assertEquals(new Point(50, 0), p);
    }
    
    @Test
    public void testTranform_11()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 1, 1, p);
        Assertions.assertEquals(new Point(3, 25), p);
    }
    
    @Test
    public void testTranform_w0()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 100, 0, p);
        Assertions.assertEquals(new Point(0, 25), p);
    }
    
    @Test
    public void testTranform_0h()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 0, 100, p);
        Assertions.assertEquals(new Point(100, 25), p);
    }
    
    @Test
    public void testTranform_wh()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 100, 100, p);
        Assertions.assertEquals(new Point(50, 50), p);
    }
    
    @Test
    public void getImage()
    {
        URL url = GamePanel.class.getClassLoader().getResource("office.png");
        assertNotNull(url);
    }
}
