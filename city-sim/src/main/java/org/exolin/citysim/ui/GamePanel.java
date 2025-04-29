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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.PlaceBuilding;
import org.exolin.citysim.ui.actions.PlaceFire;
import org.exolin.citysim.ui.actions.PlaceTrees;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.TearDownAction;
import org.exolin.citysim.ui.actions.ZonePlacement;
import static org.exolin.citysim.utils.ImageUtils.brighter;
import static org.exolin.citysim.utils.ImageUtils.loadImage;

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
    private Rotation rotation = Rotation.ORIGINAL;
    
    private final Point currentGridPos = new Point();
    
    private Action action;
    
    private boolean debugInfo = true;
    
    private long lastPaint;
    private final Timer repaintTimer;
    
    private final WorldHolder worldHolder;
    
    private final GamePanelListener listener;
    private boolean colorGrid = false;
    
    private WorldView view = WorldView.OVERGROUND;
    
    private long passedTicks = 0;
    
    private void addZone(List<Action> zoneActions, ZoneType zoneType)
    {
        zoneActions.add(new ZonePlacement(worldHolder, zoneType, ZoneType.Variant.LOW_DENSITY));
        zoneActions.add(new ZonePlacement(worldHolder, zoneType, ZoneType.Variant.DEFAULT));
    }
    
    public Map<String, List<Action>> getActions()
    {
        GetWorld getWorld = worldHolder;
        
        Map<String, List<Action>> actions = new LinkedHashMap<>();
        
        List<Action> sactions = new ArrayList<>();
        sactions.add(Action.NONE);
        sactions.add(new TearDownAction(getWorld, false));
        sactions.add(new StreetBuilder(getWorld, street, true));
        sactions.add(new StreetBuilder(getWorld, rail, true));
        sactions.add(new StreetBuilder(getWorld, water, false));
        sactions.add(new PlaceTrees(getWorld));
        sactions.add(new PlaceFire(getWorld));
        actions.put("Special", sactions);
        
        {
            List<Action> zoneActions = new ArrayList<>();
            zoneActions.add(new TearDownAction(getWorld, true));
            addZone(zoneActions, Zones.residential);
            addZone(zoneActions, Zones.business);
            addZone(zoneActions, Zones.industrial);
            actions.put("Zones", zoneActions);
        }
        
        //actions.add(new PlaceBuilding(World.office));
        
        for(BuildingType type : StructureType.actualBuildingTypes())
        {
            String categoryName = type.getZoneType() != null ? type.getZoneType().getName() : "Special buildings";
            
            if(!actions.containsKey(categoryName))
                actions.put(categoryName, new ArrayList<>());
            
            actions.get(categoryName).add(new PlaceBuilding(getWorld, type));
        }
        
        return actions;
    }

    public void setAction(Action action)
    {
        if(action == null)
            throw new NullPointerException();
        
        if(this.action == action)
            return;
        
        Cursor cursor = action.getCursor();
        
        if(cursor == null)
            cursor = Cursor.getDefaultCursor();
        
        this.action = action;
        
        setCursor(cursor);
        listener.onActionChanged(action);
    }
    
    private void updatePos(MouseEvent e)
    {
        double x = e.getX();
        double y = e.getY();
        transformBack(getDim(), x, y, currentGridPos);
        rotation.counterRotate(worldHolder.get().getGridSize(), currentGridPos.x, currentGridPos.y, currentGridPos);
    }
    
    private void execute(Runnable b)
    {
        try{
            b.run();
        }catch(Exception e){
            ErrorDisplay.show(this, e);
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
        execute(() -> action.mouseReleased(currentGridPos));
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
        this.worldHolder = new WorldHolder(world);
        this.listener = listener;
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
        
        repaintTimer = new Timer(10, (ActionEvent e) ->
        {
            updateAfterTick();
        });
    }
    
    public void start()
    {
        repaintTimer.start();
    }
    
    private static final int REFRESH_TIME = 1000;

    public void setTickFactor(SimulationSpeed tickFactor)
    {
        worldHolder.get().setTickFactor(tickFactor);
    }
    
    private synchronized void updateAfterTick()
    {
        execute(() -> {
            try{
                World world = worldHolder.get();

                int ticks = world.getTickFactor().getTickCount();
                world.updateAfterTick(ticks);
                long u = Math.max(world.getLastChange(), lastPaint+REFRESH_TIME);
                passedTicks += ticks;
                if(u >= lastPaint)
                {
                    //System.out.println(new Timestamp(System.currentTimeMillis())+"Timeout repaint");
                    repaint();
                }
            }catch(Exception e){
                ErrorDisplay.show(this, e);
            }
        });
    }
    
    void onZoom(int amount)
    {
        zoom += amount;

        //doesnt work
        if(false)
        {
            World world = worldHolder.get();
            
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
            
            Structure b = worldHolder.get().getBuildingAt(currentGridPos.x, currentGridPos.y);
            List<String> lines = List.of(
                    "Zoom: "+zoom+" | "+f,
                    "Offset: "+xoffset+"/"+yoffset,
                    "Current tile: "+currentGridPos.x+"/"+currentGridPos.y,
                    "On current tile: "+(b != null ? b.getType().getName() : "none"),
                    "View: "+view,
                    "Rotation: "+rotation,
                    "Money: "+worldHolder.get().getMoney(),
                    "Passed Ticks: "+passedTicks
            );
            
            for(int i=0;i<lines.size();++i)
                g.drawString(lines.get(i), 0, start + lineHeight * i);
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
        World world = worldHolder.get();
        
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
        World world = worldHolder.get();
        
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
        World world = worldHolder.get();
        
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
        World world = worldHolder.get();
        
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
        World world = worldHolder.get();
        
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
        
        for(Structure b: world.getBuildings(rotation))
        {
            if(scaleMarker)
            {
                if(b.getLevel() == Structure.getLevel(r))
                {
                    drawItem(g, dim, r.x, r.y, markerImage, r.width);
                    scaleMarker = false;
                }
            }
            
            drawBuilding(g, dim, b);
        }
        
        if(scaleMarker)
            drawItem(g, dim, r.x, r.y, markerImage, r.width);
    }
    
    private void drawBuilding(Graphics2D g, int dim, Structure b)
    {
        Point screenPoint = new Point();
        rotation.rotateTop(worldHolder.get().getGridSize(), b.getX(), b.getY(), b.getSize(), screenPoint);
        
        if(view == WorldView.ZONES && b.getZoneType() != null)
        {
            drawItemN(g, dim, screenPoint.x, screenPoint.y, b.getZoneType().getDefaultImage(), b.getSize());
        }
        else
            drawItem(g, dim, screenPoint.x, screenPoint.y, getCurrentImage(b.getAnimation(rotation)), b.getSize());
    }
    
    private Image getCurrentImage(Animation a)
    {
        long frame = lastPaint/a.getAnimationSpeed()%a.getImageCount();
        return a.getImage((int)frame);
    }
    
    public World getWorld()
    {
        return worldHolder.get();
    }

    public Path getWorldFile()
    {
        return worldHolder.getFile();
    }

    public void setWorldFile(Path worldFile)
    {
        worldHolder.setFile(worldFile);
    }

    public void setWorld(World world, Path worldFile)
    {
        worldHolder.set(world, worldFile);
        setAction(Action.NONE);
        repaint();
    }
    
    private void drawItemN(Graphics2D g, int dim, int x, int y, Image img, int size)
    {
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                drawItem(g, dim, x+xi, y+yi, img, 1);
            }
        }
    }
    
    private void drawItem(Graphics2D g, int dim, int x, int y, Image img, int size)
    {
        World world = worldHolder.get();
        
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
        World world = worldHolder.get();
        
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

    public void toggleDebug()
    {
        debugInfo = !debugInfo;
        repaint();
    }

    public WorldView getView()
    {
        return view;
    }

    public void setView(WorldView view)
    {
        this.view = Objects.requireNonNull(view);
        repaint();
    }

    public Rotation getRotation()
    {
        return rotation;
    }

    public void setRotation(Rotation rotation)
    {
        this.rotation = Objects.requireNonNull(rotation);
        repaint();
    }
}
