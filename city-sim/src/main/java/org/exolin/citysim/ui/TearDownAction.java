package org.exolin.citysim.ui;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import org.exolin.citysim.GetWorld;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class TearDownAction extends AreaAction implements Action
{
    public TearDownAction(GetWorld world)
    {
        super(world);
    }

    @Override
    protected void performAction(Rectangle marking)
    {
        World world = this.getWorld.get();
        
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                world.removeBuildingAt(marking.x + x, marking.y + y);
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
        return "tear down";
    }
    
    private static final Cursor CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(Utils.loadImage("tools/bulldozer"), new Point(0, 27), "bulldozer");

    @Override
    public Cursor getCursor()
    {
        return CURSOR;
    }
}
