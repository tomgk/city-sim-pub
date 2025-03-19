package org.exolin.citysim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    private final int size = 10;
    private static final int FACTOR = 2;
    
    @Override
    public void paint(Graphics g)
    {
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        draw((Graphics2D)g, Math.min(getWidth(), getHeight()*FACTOR));
    }
    
    /**
     *    
     * 
     * 
     * @param dim
     * @param x
     * @param y
     * @param drawPoint 
     */
    public static void transform(double dim, double x, double y, Point drawPoint)
    {
        double w = dim;
        double h = dim/FACTOR;
        
        //x part
        double xx = - x/dim * w / 2;
        double xy = h/2 * x/dim;
        //y part
        double yx = y/dim * (w/2);
        double yy = (h/2) * (y/dim);
        
        drawPoint.x = (int)(w/2 + xx + yx);
        drawPoint.y = (int)(0 + xy + yy);
    }
    
    private void draw(Graphics2D g, int dim)
    {
        Point p00 = new Point();
        Point pw0 = new Point();
        Point pwh = new Point();
        Point p0h = new Point();
        
        transform(dim, 0, 0, p00);
        transform(dim, dim, 0, pw0);
        transform(dim, dim, dim, pwh);
        transform(dim, 0, dim, p0h);
        
        System.out.println(p00+" "+pw0+" "+pwh+" "+p0h);
        
        g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
        g.drawLine(pw0.x, pw0.y, pwh.x, pwh.y);
        g.drawLine(pwh.x, pwh.y, p0h.x, p0h.y);
        g.drawLine(p0h.x, p0h.y, p00.x, p00.y);
    }
    
    private void draw2(Graphics2D g, int dim)
    {
        double s = (double)dim / size / FACTOR;
        
        for(int i = 0; i<size;++i)
        {
            int xoffset = (int)(i * s);
            int yoffset = (int)(i * s / FACTOR);
            
            g.drawLine(0 + xoffset, dim/FACTOR/2 + yoffset,
                       dim/2 + xoffset, 0 + yoffset);
        }
        
        g.drawLine(0, dim/FACTOR/2, dim/2, 0);
        g.drawLine(dim/2, 0, dim, dim/FACTOR/2);
        g.drawLine(dim, dim/FACTOR/2, dim/2, dim/FACTOR);
        g.drawLine(dim/2, dim/FACTOR, 0, dim/FACTOR/2);
    }
}
