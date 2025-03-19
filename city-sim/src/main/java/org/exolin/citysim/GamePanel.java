package org.exolin.citysim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    private final int size = 100;
    
    @Override
    public void paint(Graphics g)
    {
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        draw((Graphics2D)g, Math.min(getWidth(), getHeight()*2));
    }
    
    private void draw(Graphics2D g, int dim)
    {
        /*
        double w = (double)width / size;
        double h = (double)height / size;
        
        for(int i = 0; i<size;++i)
        {
            g.drawLine(0, height/2, width/2, 0);
        }
        */
        
        g.drawLine(0, dim/2/2, dim/2, 0);
        g.drawLine(dim/2, 0, dim, dim/2/2);
        g.drawLine(dim, dim/2/2, dim/2, dim/2);
        g.drawLine(dim/2, dim/2, 0, dim/2/2);
    }
}
