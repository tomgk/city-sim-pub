package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class PlaceBuilding implements BuildingAction
{
    private final GetWorld world;
    private final ActualBuildingType type;
    private final Rectangle marking = new Rectangle();

    public PlaceBuilding(GetWorld world, ActualBuildingType type)
    {
        this.world = world;
        this.type = type;
    }

    @Override
    public BuildingType getBuilding()
    {
        return type;
    }

    @Override
    public String getName()
    {
        return "build "+type.getName();
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public void mouseDown(Point gridPoint)
    {
        World w = world.get();
        w.addBuilding(type, marking.x, marking.y);
        w.reduceMoney(type.getCost());
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
        marking.width = type.getSize();
        marking.height = type.getSize();
    }


    @Override
    public void mouseReleased(Point gridPoint)
    {

    }

    @Override
    public Image getMarker()
    {
        return type.getBrightImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return true;
    }
}
