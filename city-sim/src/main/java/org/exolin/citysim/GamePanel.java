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
        int width = dim;
        int height = dim/2;
        
        /*
        double w = (double)width / size;
        double h = (double)height / size;
        
        for(int i = 0; i<size;++i)
        {
            g.drawLine(0, height/2, width/2, 0);
        }
        */
        
        g.drawLine(0, height/2, width/2, 0);
        g.drawLine(width/2, 0, width, height/2);
        g.drawLine(width, height/2, width/2, height);
        g.drawLine(width/2, height, 0, height/2);
    }
}
