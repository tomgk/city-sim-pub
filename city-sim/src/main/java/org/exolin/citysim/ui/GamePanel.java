package org.exolin.citysim.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.Building;
import org.exolin.citysim.BuildingType;
import org.exolin.citysim.World;
import org.exolin.citysim.ui.Action.NoAction;
import static org.exolin.citysim.ui.Utils.brighter;
import static org.exolin.citysim.ui.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public final class GamePanel extends JComponent
{
    private static final int FACTOR = 2;
    
    private static final BufferedImage land = loadImage("land");
    private static final BufferedImage land_highlighted = brighter(loadImage("land"));
    
    private int zoom = 0;
    private int xoffset = 0;
    private int yoffset = 0;
    
    private final Point currentGridPos = new Point();
    
    private Action action;
    
    private boolean debugInfo = true;
    
    private long lastPaint;
    private final Timer repaint;
    
    public Map<String, List<Action>> getActions()
    {
        Map<String, List<Action>> actions = new LinkedHashMap<>();
        
        List<Action> sactions = new ArrayList<>();
        sactions.add(new NoAction());
        sactions.add(new TearDownAction(world));
        sactions.add(new StreetBuilder(world));
        actions.put("Special", sactions);
        
        {
            List<Action> zoneActions = new ArrayList<>();
            zoneActions.add(new ZonePlacement(world, World.zone_residential));
            zoneActions.add(new ZonePlacement(world, World.zone_business));
            zoneActions.add(new ZonePlacement(world, World.zone_industrial));
            actions.put("Zones", zoneActions);
        }
        
        //actions.add(new PlaceBuilding(World.office));
        
        for(ActualBuildingType type : BuildingType.actualBuildingTypes())
        {
            String categoryName = type.getZoneType() != null ? type.getZoneType().getName() : "Special buildings";
            
            if(!actions.containsKey(categoryName))
                actions.put(categoryName, new ArrayList<>());
            
            actions.get(categoryName).add(new PlaceBuilding(world, type));
        }
        
        return actions;
    }

    public void setAction(Action action)
    {
        this.action = action;
        Cursor cursor = null;
        
        if(action != null)
            cursor = action.getCursor();
        
        if(cursor == null)
            cursor = Cursor.getDefaultCursor();
        
        setCursor(cursor);
    }
    
    private void updatePos(MouseEvent e)
    {
        double x = e.getX();
        double y = e.getY();
        transformBack(getDim(), x, y, currentGridPos);
    }
    
    private void execute(Runnable b)
    {
        try{
        b.run();
        }catch(Exception e){
            e.printStackTrace(System.out);
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            JOptionPane.showMessageDialog(this, out.toString(), "Unexpected Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private synchronized void mousePressed(MouseEvent e)
    {
        updatePos(e);
        if(action != null)
            execute(() -> action.mouseDown(currentGridPos));
        repaint();
    }
    
    private synchronized void mouseReleased(MouseEvent e)
    {
        if(action == null)
            return;

        updatePos(e);
        execute(() -> action.releaseMouse(currentGridPos));
        repaint();
    }
    
    private synchronized void mouseMoved(MouseEvent e)
    {
        updatePos(e);

        if(action != null)
            execute(() -> action.moveMouse(currentGridPos));
        
        //repaint has to always be done because the selection moves
        repaint();
    }

    public GamePanel(World world, JFrame frame, GamePanelListener listener)
    {
        this.world = world;
        setBackground(Color.black);
        
        listener.created(this);
        
        MouseAdapter a  = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                GamePanel.this.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                GamePanel.this.mouseReleased(e);
            }
            
            @Override
            public void mouseMoved(MouseEvent e)
            {
                GamePanel.this.mouseMoved(e);
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
            onZoom(-e.getWheelRotation());
        });
        //setFocusable(true);
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                GamePanel.this.keyPressed(e.getKeyCode());
            }
        });
        
        repaint = new Timer(10, (ActionEvent e) ->
        {
            synchronized (GamePanel.this)
            {
                world.update(world);
                long u = world.getLastChange();
                if(u >= lastPaint)
                {
                    System.out.println(new Timestamp(System.currentTimeMillis())+"Timeout repaint");
                    repaint();
                }
            }
        });
    }
    
    public void start()
    {
        repaint.start();
    }
    
    private synchronized void update()
    {
        world.update(world);
        long u = world.getLastChange();
        if(u >= lastPaint)
        {
            System.out.println("Timeout repaint");
            repaint();
        }
    }
    
    void onZoom(int amount)
    {
        zoom += amount;

        //doesnt work
        if(false)
        {
            //issue: ignores current position, just adds
            double zoomFactor = getZoomFactor();
            xoffset += 1/zoomFactor * world.getGridSize();
            yoffset += 1/zoomFactor *  world.getGridSize();
            //System.out.println(xoffset+"/"+yoffset);
        }

        GamePanel.this.repaint();
    }
    
    void keyPressed(int keyCode)
    {
        boolean update = true;
        double f = 50;
        switch(keyCode)
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
            repaint();
        }
    }
    
    public void resetPosition()
    {
        zoom = 0;
        xoffset = 0;
        yoffset = 0;
        repaint();
        requestFocus();
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
        lastPaint = System.currentTimeMillis();
        
        g.fillRect(0, 0, getWidth(), getHeight());
        //g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        {
            Graphics2D g2 = (Graphics2D) g.create();

            double z = getZoomFactor();
            AffineTransform scalingTransform = AffineTransform.getScaleInstance(z, z);

            g2.transform(scalingTransform);
            g2.translate(xoffset, yoffset);

            draw(g2, getDim());
        }
        
        g.setColor(Color.white);
        if(debugInfo)
        {
            String f;
            double zoomFactor = getZoomFactor();
            if(zoom == 0)
                f = "full view";
            else if(zoomFactor < 1)
                f = "zoom out "+(1/zoomFactor);
            else
                f = "zoom in "+zoomFactor;

            int start = 12;
            int lineHeight = 15;
            
            g.drawString("Zoom: "+zoom+" | "+f, 0, start);
            g.drawString("Offset: "+xoffset+"/"+yoffset, 0, start + lineHeight * 1);
            g.drawString("Current tile: "+currentGridPos.x+"/"+currentGridPos.y, 0, start + lineHeight * 2);
            Building b = world.getBuildingAt(currentGridPos.x, currentGridPos.y);
            g.drawString("On current tile: "+(b != null ? b.getType().getName() : "none"), 0, start + lineHeight * 3);
        }
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
    
    private boolean colorGrid = false;
    
    private void draw(Graphics2D g, int dim)
    {
        drawGrid(g, dim);
        drawBuildings(g, dim);
    }
    
    public void toggleColorGrid()
    {
        colorGrid = !colorGrid;
        repaint();
    }
    
    private void drawGrid(Graphics2D g, int dim)
    {
        if(colorGrid)
            drawColorGrid(g, dim);
        else
            drawNormalGrid(g, dim);
    }
    
    private void drawColorGrid(Graphics2D g, int dim)
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
    
    private void drawNormalGrid(Graphics2D g, int dim)
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
    
    private void drawBuildings(Graphics2D g, int dim)
    {
        g.setStroke(new BasicStroke(1));
        
        Rectangle r = null;
        Image markerImage = null;
        boolean scaleMarker = false;
        if(action != null)
        {
            markerImage = action.getMarker();
            if(markerImage != null)
                scaleMarker = action.scaleMarker();
            r = action.getSelection();
        }
        if(r == null)
            r = new Rectangle(-1, -1); //out of bounds to never match
        if(markerImage == null)
            markerImage = land_highlighted;
        
        for(int y=0;y< world.getGridSize();++y)
        {
            for(int x=0;x< world.getGridSize();++x)
            {
                boolean current = currentGridPos.x == x && currentGridPos.y == y;
                Image img = current ? land_highlighted : land;
                
                if(r.contains(x, y))
                    img = markerImage;
                
                //will be drawn later
                if(scaleMarker && r.contains(x, y))
                    continue;
                
                if(false)
                {
                if(scaleMarker && r.contains(x, y))
                {
                    if(x != r.getMaxX()-1 || y != r.getMaxY()-1)
                        continue;
                    else
                        drawItem(g, dim, r.x, r.y, markerImage, r.width);
                    
                    continue;
                }
                }
                
                drawItem(g, dim, x, y, img, 1);
            }
        }
        
        for(Building b: world.getBuildings())
        {
            if(scaleMarker)
            {
                if(b.getLevel() == Building.getLevel(r))
                {
                    drawItem(g, dim, r.x, r.y, markerImage, r.width);
                    scaleMarker = false;
                }
            }
            
            drawItem(g, dim, b.getX(), b.getY(), b.getImage(), b.getSize());
        }
        
        if(scaleMarker)
            drawItem(g, dim, r.x, r.y, markerImage, r.width);
    }
    
    private final World world;
    
    private void drawItem(Graphics2D g, int dim, int x, int y, Image img, int size)
    {
        Point p = new Point();
        
        transform(dim, size * 0.5 + x, size * -0.5 + y, p);
        Point p1 = new Point();
        transform(dim, size * 0.5 + x, size * 1.5 + y, p1);
        
        double tileHeight = dim/FACTOR/ world.getGridSize();
        
        //assumption: image is exactly one tile wide
        int imageTileHeight = img.getWidth(null) / FACTOR;
        
        //image can be higher than a tile
        //substract the part above to place it on the tile
        p.y -= (tileHeight / imageTileHeight) * (img.getHeight(null) - imageTileHeight) * size;
        
        g.setColor(Color.black);
        
        //draws a rectangle around
        if(false)//img != land)
        {
            g.drawRect(p.x-1, p.y-1, 3, 3);
            g.drawRect(p1.x-1, p1.y-1, 3, 3);
            
            g.setColor(Color.red);
            g.drawRect(p.x, p.y, p1.x-p.x, p1.y-p.y);
        }
        
        g.drawImage(img, p.x, p.y, p1.x-p.x, p1.y-p.y, null);
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
