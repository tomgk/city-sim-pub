package org.exolin.citysim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    private final static int GRID_SIZE = 10;
    private static final int FACTOR = 2;
    
    BufferedImage i = loadImage("office");
    BufferedImage car_cinema = loadImage("car-cinema");

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
     * @param grid_x
     * @param grid_y
     * @param drawPoint 
     */
    public static void transform(double dim, double grid_x, double grid_y, Point drawPoint)
    {
        double SCREEN_WIDTH = dim;
        double SCREEN_HEIGHT = dim/FACTOR;
        
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
        
        System.out.println(grid_x+","+grid_y+" => "+screen_x+","+screen_y);
    }
    
    private final boolean colorGrid = true;
    
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
        
        System.out.println("-------------------------------------------------");
        
        for(Building b: buildings)
            drax(g, dim, b.getX(), b.getY(), b.getImage());
    }
    
    private final List<Building> buildings = new ArrayList<>();
    
    {
        addBuilding(i, 2, 1);
        addBuilding(i, 3, 4);
        addBuilding(car_cinema, 6, 6);
        
        addBuilding(i, 0, 0);
    }
    
    void addBuilding(Image image, int x, int y)
    {
        buildings.add(new Building(image, x, y));
        buildings.sort(Comparator.comparing(Building::getLevel));
    }
    
    static class Building
    {
        private final Image image;
        private final int x;
        private final int y;

        public Building(Image image, int x, int y)
        {
            this.image = image;
            this.x = x;
            this.y = y;
        }

        public Image getImage()
        {
            return image;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }
        
        public int getLevel()
        {
            return x + y;
        }
    }
    
    void drax(Graphics2D g, int dim, int x, int y, Image img)
    {
        Point p = new Point();
        transform(dim, 0.5 + x, -0.5 + y, p);
        Point p1 = new Point();
        transform(dim, 0.5 + x, 1.5 + y, p1);
        
        double tileHeight = dim/FACTOR/GRID_SIZE;
        
        System.out.println(p+" to "+p1);
        //p.y -= (tileHeight / 50) * 10;
        
        //assumption: image is exactly one tile wide
        int imageTileHeight = img.getWidth(null) / FACTOR;
        
        //image can be higher than a tile
        //substract the part above to place it on the tile
        p.y -= (tileHeight / imageTileHeight) * (img.getHeight(null) - imageTileHeight);
        
        System.out.println(p+" to "+p1);
        
        g.setColor(Color.black);
        g.drawRect(p.x-1, p.y-1, 3, 3);
        
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
