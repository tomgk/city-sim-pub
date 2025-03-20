package org.exolin.citysim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    private final GamePanelListener listener;
    private final static int GRID_SIZE = 10;
    private static final int FACTOR = 2;
    
    private static final BufferedImage land = loadImage("land");
    private static final BufferedImage office = loadImage("office");
    private static final BufferedImage office2 = loadImage("office_2");
    private static final BufferedImage office3 = loadImage("office_3");
    private static final BufferedImage car_cinema = loadImage("car-cinema");
    private static final BufferedImage cinema = loadImage("cinema");
    private static final BufferedImage parkbuilding = loadImage("parkbuilding");
    
    private int zoom = 0;
    private int xoffset = 0;
    private int yoffset = 0;

    static BufferedImage loadImage(String name)
    {
        URL resource = GamePanel.class.getClassLoader().getResource(name+".png");
        if(resource == null)
            throw new IllegalArgumentException("not found");
        
        try{
            return ImageIO.read(resource);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public GamePanel(JFrame frame, GamePanelListener listener)
    {
        this.listener = listener;
        setBackground(Color.black);
        
        listener.created(this);
        
        frame.addMouseWheelListener((MouseWheelEvent e) ->
        {
            zoom -= e.getWheelRotation();
            listener.zoomChanged(zoom);
            
            //doesnt work
            if(false)
            {
                double zoomFactor = getZoomFactor();
                xoffset += 1/zoomFactor * GRID_SIZE;
                yoffset += 1/zoomFactor * GRID_SIZE;
                listener.offsetChanged(xoffset, yoffset);
                System.out.println(xoffset+"/"+yoffset);
            }
            
            GamePanel.this.repaint();
        });
        //setFocusable(true);
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                boolean update = true;
                double f = 50;
                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_LEFT:
                        xoffset += getZoomFactor() * f;
                        break;
                    case KeyEvent.VK_RIGHT:
                        xoffset -= getZoomFactor() * f;
                        break;
                    case KeyEvent.VK_UP:
                        yoffset += getZoomFactor() * f;
                        break;
                    case KeyEvent.VK_DOWN:
                        yoffset -= getZoomFactor() * f;
                        break;
                    default:
                        update = false;
                }
                if(update)
                {
                    listener.offsetChanged(xoffset, yoffset);
                    repaint();
                }
            }
        });
        listener.zoomChanged(zoom);
        listener.offsetChanged(xoffset, yoffset);
    }
    
    public void resetPosition()
    {
        zoom = 0;
        xoffset = 0;
        yoffset = 0;
        repaint();
        requestFocus();
        listener.zoomChanged(zoom);
        listener.offsetChanged(xoffset, yoffset);
    }
    
    private double getZoomFactor()
    {
        return getZoomFactor(zoom);
    }
    
    private double getZoomFactor(int zoom)
    {
        return Math.pow(1.5, zoom);
    }
    
    @Override
    public void paint(Graphics g)
    {
        g.fillRect(0, 0, getWidth(), getHeight());
        //g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        Graphics2D g2 = (Graphics2D) g;
        
        double z = getZoomFactor();
        AffineTransform scalingTransform = AffineTransform.getScaleInstance(z, z);

        g2.transform(scalingTransform);
        g2.translate(xoffset, yoffset);
        
        draw(g2, Math.min(getWidth(), getHeight()*FACTOR));
    }
    
    /**
     *    
     * 
     * 
     * @param dim
     * @param grid_x
     * @param grid_y
     * @param drawPoint 
     */
    public void transform(double dim, double grid_x, double grid_y, Point drawPoint)
    {
        //dim *= Math.pow(1.5, zoom);
        
        double SCREEN_WIDTH = dim;
        double SCREEN_HEIGHT = dim/FACTOR;
        
        //System.out.println(dim);
        
        if(false)
        {
            drawPoint.x = (int)(SCREEN_WIDTH / 2 + grid_y * (SCREEN_WIDTH / 2 / GRID_SIZE) - grid_x * (SCREEN_WIDTH / 2 / GRID_SIZE));
            drawPoint.y = (int)(0 + grid_y * (SCREEN_HEIGHT / 2 / GRID_SIZE) + grid_x * (SCREEN_HEIGHT / 2 / GRID_SIZE));
        }
        
        grid_x *= dim / GRID_SIZE;
        grid_y *= dim / GRID_SIZE;
        
        //x part
        double xx = - grid_x/dim * SCREEN_WIDTH / 2;
        double xy = SCREEN_HEIGHT/2 * grid_x/dim;
        //y part
        double yx = grid_y/dim * (SCREEN_WIDTH/2);
        double yy = (SCREEN_HEIGHT/2) * (grid_y/dim);
        
        double screen_x = SCREEN_WIDTH/2 + xx + yx;
        double screen_y = 0 + xy + yy;
        
        drawPoint.x = (int)(screen_x);
        drawPoint.y = (int)(screen_y);
        
        //System.out.println(grid_x+","+grid_y+" => "+screen_x+","+screen_y);
    }
    
    private final boolean colorGrid = false;
    
    private void draw(Graphics2D g, int dim)
    {
        if(colorGrid)
        {
            Color[] color = {Color.red, Color.green, Color.blue};
            int c = 0;

            for(int y=0;y<=GRID_SIZE;++y)
            {
                for(int x=0;x<=GRID_SIZE;++x)
                {
                    Point p00 = new Point();
                    Point pw0 = new Point();
                    if(x != GRID_SIZE)
                    {
                        transform(dim, (double)x, (double)y, p00);
                        transform(dim, (double)(x+1), (double)(y), pw0);
                        g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
                    }
                    
                    if(y != GRID_SIZE)
                    {
                        transform(dim, (double)x, (double)y, p00);
                        transform(dim, (double)(x), (double)(y+1), pw0);
                        g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
                    }

                    g.setColor(color[c]);
                    ++c;
                    if(c >= color.length)
                        c = 0;
                }
            }
        }
        else
        {
            g.setColor(Color.orange.darker().darker());
            g.setStroke(new BasicStroke(10));
            
            for(int i = 0; i < GRID_SIZE+1; ++i)
            {
                Point p00 = new Point();
                Point pw0 = new Point();
                transform(dim, 0, (double)i, p00);
                transform(dim, GRID_SIZE, (double)i, pw0);
                g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
            }

            for(int i = 0; i < GRID_SIZE+1; ++i)
            {
                Point pw0 = new Point();
                Point pwh = new Point();

                transform(dim, (double)i, 0, pw0);
                transform(dim, (double)i, GRID_SIZE, pwh);
                g.drawLine(pw0.x, pw0.y, pwh.x, pwh.y);
            }
        }
        
        g.setStroke(new BasicStroke(1));
        
        //System.out.println("-------------------------------------------------");
        
        for(int y=0;y<GRID_SIZE;++y)
        {
            for(int x=0;x<GRID_SIZE;++x)
            {
                drax(g, dim, x, y, land);
            }
        }
        
        for(Building b: buildings)
            drax(g, dim, b.getX(), b.getY(), b.getImage());
    }
    
    private final List<Building> buildings = new ArrayList<>();
    
    {
        addBuilding(office, 2, 1);
        addBuilding(office, 3, 4);
        addBuilding(car_cinema, 6, 6);
        addBuilding(cinema, 6, 7);
        addBuilding(office2, 6, 8);
        addBuilding(parkbuilding, 6, 9);
        addBuilding(office3, 5, 9);
        
        addBuilding(office, 0, 0);
    }
    
    void addBuilding(Image image, int x, int y)
    {
        buildings.add(new Building(image, x, y));
        buildings.sort(Comparator.comparing(Building::getLevel));
    }
    
    void drax(Graphics2D g, int dim, int x, int y, Image img)
    {
        Point p = new Point();
        transform(dim, 0.5 + x, -0.5 + y, p);
        Point p1 = new Point();
        transform(dim, 0.5 + x, 1.5 + y, p1);
        
        double tileHeight = dim/FACTOR/GRID_SIZE;
        
        //System.out.println(p+" to "+p1);
        //p.y -= (tileHeight / 50) * 10;
        
        //assumption: image is exactly one tile wide
        int imageTileHeight = img.getWidth(null) / FACTOR;
        
        //image can be higher than a tile
        //substract the part above to place it on the tile
        p.y -= (tileHeight / imageTileHeight) * (img.getHeight(null) - imageTileHeight);
        
        //System.out.println(p+" to "+p1);
        
        g.setColor(Color.black);
        //g.drawRect(p.x-1, p.y-1, 3, 3);
        
        if(false)
        {
            g.setColor(Color.red);
            g.drawRect(p.x, p.y, p1.x-p.x, p1.y-p.y);
        }
        
        g.drawImage(img, p.x, p.y, p1.x-p.x, p1.y-p.y, new ImageObserver()
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
        double s = (double)dim / GRID_SIZE / FACTOR;
        
        for(int i = 0; i<GRID_SIZE;++i)
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
