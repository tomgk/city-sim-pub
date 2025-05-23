package org.exolin.citysim.ui;

import java.awt.Point;
import java.net.URL;
import java.util.Optional;
import org.exolin.citysim.model.RCI;
import org.exolin.citysim.model.Worlds;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.utils.ImageUtils;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class GamePanelTest
{
    private static final GamePanel gamePanel = new GamePanel(Worlds.World2(), null, new GamePanelListener(){
        @Override
        public void created(GamePanel panel)
        {
            assertNotNull(panel);
        }

        @Override
        public void onActionChanged(Action newAction)
        {
            assertNotNull(newAction);
        }

        @Override
        public void onRCIChanged(RCI rci)
        {
            assertNotNull(rci);
        }
    }, Optional.empty());
    
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
        Assertions.assertEquals(new Point(50, 1), p);
    }
    
    @Test
    public void testTranform_w0()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 100, 0, p);
        Assertions.assertEquals(new Point(-116, 83), p);
    }
    
    @Test
    public void testTranform_0h()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 0, 100, p);
        Assertions.assertEquals(new Point(216, 83), p);
    }
    
    @Test
    public void testTranform_wh()
    {
        Point p = new Point(-1, -1);
        gamePanel.transform(100, 100, 100, p);
        Assertions.assertEquals(new Point(50, 166), p);
    }
    
    @Test
    public void getImage()
    {
        URL url = GamePanel.class.getClassLoader().getResource(ImageUtils.RESOURCE_PREFIX+"business/office.png");
        assertNotNull(url);
    }
}
