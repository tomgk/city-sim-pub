package org.exolin.citysim.ui.actions;

import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.sim.RemoveMode;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class TearDownAction extends AreaAction implements ActionWithImage
{
    private final boolean removeZoning;
    
    private TearDownAction(GetWorld world, boolean removeZoning)
    {
        super(world);
        this.removeZoning = removeZoning;
    }
    
    public static TearDownAction createTearDown(GetWorld world)
    {
        return new TearDownAction(world, false);
    }
    
    public static TearDownAction createDezoning(GetWorld world)
    {
        return new TearDownAction(world, true);
    }

    @Override
    public int getCost()
    {
        return removeZoning ? 0 : 1;
    }

    @Override
    protected void performAction(Rectangle marking)
    {
        World world = this.getWorld.get();
        
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                world.removeBuildingAt(marking.x + x, marking.y + y, removeZoning ? RemoveMode.REMOVE_ZONING : RemoveMode.TEAR_DOWN);
            }
        }
    }

    @Override
    public Image getMarker()
    {
        return null;
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }

    @Override
    public String getName()
    {
        return removeZoning ? "remove zoning" : "tear down";
    }
    
    private static final Image IMAGE = ImageUtils.loadImage("tools/bulldozer");
    
    private static final Cursor CURSOR;
    static
    {
        if(!GraphicsEnvironment.isHeadless())
            CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(IMAGE, new Point(0, 27), "bulldozer");
        else
            CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    }

    @Override
    public Cursor getCursor()
    {
        return CURSOR;
    }

    @Override
    public Image getImage()
    {
        return IMAGE;
    }
}
