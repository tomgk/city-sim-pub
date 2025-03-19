package org.exolin.citysim;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    @Override
    public void paint(Graphics g)
    {
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }
}
