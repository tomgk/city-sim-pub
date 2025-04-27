package org.exolin.citysim.ui.actions;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class TearDownAction extends AreaAction implements ActionWithImage
{
    private final boolean removeZoning;
    
    public TearDownAction(GetWorld world, boolean removeZoning)
    {
        super(world);
        this.removeZoning = removeZoning;
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
                world.removeBuildingAt(marking.x + x, marking.y + y, removeZoning, true);
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
    
    private static final Cursor CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(IMAGE, new Point(0, 27), "bulldozer");

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
