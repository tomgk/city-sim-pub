package org.exolin.citysim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
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
        for(int i = 0; i < size+1; ++i)
        {
            Point p00 = new Point();
            Point pw0 = new Point();
            transform(dim, 0, (double)dim / size * i, p00);
            transform(dim, dim, (double)dim / size * i, pw0);
            g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
        }
        
        for(int i = 0; i < size+1; ++i)
        {
            Point pw0 = new Point();
            Point pwh = new Point();

            transform(dim, (double)dim / size * i, 0, pw0);
            transform(dim, (double)dim / size * i, dim, pwh);
            g.drawLine(pw0.x, pw0.y, pwh.x, pwh.y);
        }
        
        URL resource = GamePanel.class.getClassLoader().getResource("office.png");
        if(resource == null)
            throw new IllegalArgumentException("not found");
        
        BufferedImage i = null;
        try{
            i = ImageIO.read(resource);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        
        Point p = new Point();
        transform(dim, 0, 0, p);
        Point p1 = new Point();
        transform(dim, 1, 1, p1);
        System.out.println(p+" to "+p1);
        
        g.drawImage(i, p.x, p.y, p1.x, p1.y, new ImageObserver()
        {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
            {
                return true;
            }
        });
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
