package org.exolin.citysim.ui;

import java.awt.Image;
import java.awt.Rectangle;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class TearDownAction extends AreaAction implements Action
{
    private final World world;

    public TearDownAction(World world)
    {
        this.world = world;
    }

    @Override
    protected void performAction(Rectangle marking)
    {
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
}
