package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public abstract class PlaceStructure implements BuildingAction
{
    private final GetWorld world;
    private final Rectangle marking = new Rectangle();

    public PlaceStructure(GetWorld world)
    {
        this.world = world;
    }

    @Override
    public abstract StructureType getBuilding();

    @Override
    public String getName()
    {
        return "build "+getBuilding().getName();
    }

    @Override
    public String toString()
    {
        return getName();
    }
    
    protected abstract void addBuilding(World w, int x, int y);

    @Override
    public void mouseDown(Point gridPoint)
    {
        World w = world.get();
        addBuilding(w, marking.x, marking.y);
    }

    @Override
    public Rectangle getSelection()
    {
        return marking;
    }

    @Override
    public void moveMouse(Point gridPoint)
    {
        marking.x = gridPoint.x;
        marking.y = gridPoint.y;
        StructureType type = getBuilding();
        marking.width = type.getSize().toIntegerx();
        marking.height = type.getSize().toIntegerx();
    }


    @Override
    public void mouseReleased(Point gridPoint)
    {

    }

    @Override
    public Image getMarker()
    {
        return getBuilding().getBrightImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return true;
    }

    @Override
    public abstract int getCost();
}
