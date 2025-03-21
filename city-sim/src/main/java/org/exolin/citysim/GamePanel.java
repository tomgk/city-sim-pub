package org.exolin.citysim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static org.exolin.citysim.Utils.brighter;
import static org.exolin.citysim.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public class GamePanel extends JComponent
{
    private final GamePanelListener listener;
    private static final int FACTOR = 2;
    
    private static final BufferedImage land = loadImage("land");
    private static final BufferedImage land_highlighted = brighter(loadImage("land"));
    
    private int zoom = 0;
    private int xoffset = 0;
    private int yoffset = 0;
    
    private final Point currentGridPos = new Point();
    
    private Action action;
    
    public void setTool(Tool tool)
    {
        switch(tool)
        {
            case STREET:
                action = new StreetBuilder();
                break;
                
            default:
                action = null;
        }
    }
    
    private void zoomChanged()
    {
        listener.zoomChanged(zoom, getZoomFactor(zoom));
    }
    
    private class StreetBuilder implements Action
    {
        private Point start;
        private Rectangle marking;

        @Override
        public void mouseDown(Point gridPoint)
        {
            this.start = new Point(gridPoint);
            System.out.println("StreetBuilder @ "+start);
            marking = new Rectangle(start.x, start.y, 1, 1);
        }

        @Override
        public Rectangle getSelection()
        {
            return marking;
        }

        @Override
        public void moveMouse(Point gridPoint)
        {
            if(start == null)
                return;
            
            int diffX = gridPoint.x - start.x;
            int diffY = gridPoint.y - start.y;
            
            if(Math.abs(diffX) > Math.abs(diffY))
            {
                diffX = diffX + Integer.signum(diffX);
                diffY = 1;
            }
            else
            {
                diffX = 1;
                diffY = diffY + Integer.signum(diffY);
            }
            
            marking.x = start.x;
            marking.y = start.y;
            
            if(diffX > 0)
                marking.width = diffX;
            else
            {
                marking.width = -diffX;
                marking.x += diffX;
            }
            
            if(diffY > 0)
                marking.height = diffY;
            else
            {
                marking.height = -diffY;
                marking.y += diffY;
            }
            
            //System.out.println("StreetBuilder move to "+gridPoint+" => "+marking);
        }

        @Override
        public void releaseMouse(Point gridPoint)
        {
            //TODO
            System.out.println("release "+marking);
            
            int diffX;
            int diffY;
            int len;
            BuildingType type;
            if(marking.width == 1)
            {
                diffX = 0;
                diffY = 1;
                len = marking.height;
                type = World.street2;
            }
            else
            {
                diffX = 1;
                diffY = 0;
                len = marking.width;
                type = World.street1;
            }
            
            for(int i=0;i<len;++i)
            {
                int x = marking.x + diffX * i;
                int y = marking.y + diffY * i;
                
                if(world.containsBuilding(x, y))
                    continue;
                
                world.addBuilding(type, x, y);
            }
            
            start = null;
            marking = null;
        }
    }
    
    public GamePanel(World world, JFrame frame, GamePanelListener listener)
    {
        this.world = world;
        this.listener = listener;
        setBackground(Color.black);
        
        listener.created(this);
        
        MouseAdapter a  = new MouseAdapter()
        {
            private void updatePos(MouseEvent e)
            {
                double x = e.getX();
                double y = e.getY();
                transformBack(getDim(), x, y, currentGridPos);
            }
            
            @Override
            public void mousePressed(MouseEvent e)
            {
                updatePos(e);
                if(action != null)
                    action.mouseDown(currentGridPos);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(action == null)
                    return;
                
                updatePos(e);
                action.releaseMouse(currentGridPos);
                repaint();
            }
            
            @Override
            public void mouseMoved(MouseEvent e)
            {
                updatePos(e);
                
                if(action != null)
                    action.moveMouse(currentGridPos);
                
                repaint();
                
                listener.onSelectionChanged(currentGridPos);
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                mouseMoved(e);
            }
        };
        
        frame.addMouseListener(a);
        frame.addMouseMotionListener(a);
        
        frame.addMouseWheelListener((MouseWheelEvent e) ->
        {
            zoom -= e.getWheelRotation();
            zoomChanged();
            
            //doesnt work
            if(false)
            {
                double zoomFactor = getZoomFactor();
                xoffset += 1/zoomFactor * world.getGridSize();
                yoffset += 1/zoomFactor *  world.getGridSize();
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
        zoomChanged();
        listener.offsetChanged(xoffset, yoffset);
    }
    
    public void resetPosition()
    {
        zoom = 0;
        xoffset = 0;
        yoffset = 0;
        repaint();
        requestFocus();
        zoomChanged();
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
    
    private int getDim()
    {
        return Math.min(getWidth(), getHeight()*FACTOR);
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
        
        draw(g2, getDim());
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
        double SCREEN_WIDTH = dim;
        double SCREEN_HEIGHT = dim/FACTOR;
        
        grid_x *= dim /  world.getGridSize();
        grid_y *= dim /  world.getGridSize();
        
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
    }
    
    public void transformBack(double dim, double screen_x, double screen_y, Point gridPoint)
    {
        double SCREEN_WIDTH = dim;
        double SCREEN_HEIGHT = dim/FACTOR;
        
        //pretend that top of (0, 0) is in the top left corner of the screen
        screen_x -= SCREEN_WIDTH / 2;
        screen_y -= 0;
        
        screen_x /= dim /  world.getGridSize();
        screen_y /= dim /  world.getGridSize();
        
        double gridX = -screen_x + screen_y * FACTOR;
        double gridY = screen_x + screen_y * FACTOR;
        
        gridPoint.x = (int)gridX - 2;
        gridPoint.y = (int)gridY - 2;
    }
    
    private final boolean colorGrid = false;
    
    private void draw(Graphics2D g, int dim)
    {
        drawGrid(g, dim);
        drawBuildings(g, dim);
    }
    
    private void drawGrid(Graphics2D g, int dim)
    {
        if(colorGrid)
        {
            Color[] color = {Color.red, Color.green, Color.blue};
            int c = 0;

            for(int y=0;y<= world.getGridSize();++y)
            {
                for(int x=0;x<= world.getGridSize();++x)
                {
                    Point p00 = new Point();
                    Point pw0 = new Point();
                    if(x !=  world.getGridSize())
                    {
                        transform(dim, (double)x, (double)y, p00);
                        transform(dim, (double)(x+1), (double)(y), pw0);
                        g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
                    }
                    
                    if(y !=  world.getGridSize())
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
            
            for(int i = 0; i <  world.getGridSize()+1; ++i)
            {
                Point p00 = new Point();
                Point pw0 = new Point();
                transform(dim, 0, (double)i, p00);
                transform(dim,  world.getGridSize(), (double)i, pw0);
                g.drawLine(p00.x, p00.y, pw0.x, pw0.y);
            }

            for(int i = 0; i <  world.getGridSize()+1; ++i)
            {
                Point pw0 = new Point();
                Point pwh = new Point();

                transform(dim, (double)i, 0, pw0);
                transform(dim, (double)i,  world.getGridSize(), pwh);
                g.drawLine(pw0.x, pw0.y, pwh.x, pwh.y);
            }
        }
    }
    
    private void drawBuildings(Graphics2D g, int dim)
    {
        g.setStroke(new BasicStroke(1));
        
        //System.out.println("-------------------------------------------------");
        
        Rectangle r = null;
        if(action != null)
            r = action.getSelection();
        if(r == null)
            r = new Rectangle(-1, -1); //out of bounds to never match
        
        for(int y=0;y< world.getGridSize();++y)
        {
            for(int x=0;x< world.getGridSize();++x)
            {
                boolean current = currentGridPos.x == x && currentGridPos.y == y;
                
                if(r.contains(x, y))
                    current = true;
                
                drawItem(g, dim, x, y, current ? land_highlighted : land, 1);
            }
        }
        
        for(Building b: world.getBuildings())
            drawItem(g, dim, b.getX(), b.getY(), b.getImage(), b.getSize());
    }
    
    private final World world;
    
    private void drawItem(Graphics2D g, int dim, int x, int y, Image img, int size)
    {
        Point p = new Point();
        
        transform(dim, size * 0.5 + x, size * -0.5 + y, p);
        Point p1 = new Point();
        transform(dim, size * 0.5 + x, size * 1.5 + y, p1);
        
        double tileHeight = dim/FACTOR/ world.getGridSize();
        
        //System.out.println(p+" to "+p1);
        //p.y -= (tileHeight / 50) * 10;
        
        //assumption: image is exactly one tile wide
        int imageTileHeight = img.getWidth(null) / FACTOR;
        
        //image can be higher than a tile
        //substract the part above to place it on the tile
        p.y -= (tileHeight / imageTileHeight) * (img.getHeight(null) - imageTileHeight) * size;
        
        //System.out.println(p+" to "+p1);
        
        g.setColor(Color.black);
        //g.drawRect(p.x-1, p.y-1, 3, 3);
        
        //draws a rectangle around
        if(false)//img != land)
        {
            g.drawRect(p.x-1, p.y-1, 3, 3);
            g.drawRect(p1.x-1, p1.y-1, 3, 3);
            
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
        double s = (double)dim /  world.getGridSize() / FACTOR;
        
        for(int i = 0; i< world.getGridSize();++i)
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
