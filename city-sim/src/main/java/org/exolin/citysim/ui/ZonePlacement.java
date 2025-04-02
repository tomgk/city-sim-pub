package org.exolin.citysim.ui;

import java.awt.Image;
import java.awt.Rectangle;
import org.exolin.citysim.Building;
import org.exolin.citysim.BuildingType;
import org.exolin.citysim.GetWorld;
import org.exolin.citysim.World;
import org.exolin.citysim.ZoneType;

/**
 *
 * @author Thomas
 */
public class ZonePlacement extends AreaAction implements BuildingAction
{
    private final BuildingType building;

    public ZonePlacement(GetWorld world, ZoneType building)
    {
        super(world);
        this.building = building;
    }

    @Override
    public String getName()
    {
        return "zone for "+building.getName();
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    protected void performAction(Rectangle marking)
    {
        World world = getWorld.get();
        
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                Building buildingAt = world.getBuildingAt(marking.x + x, marking.y + y);
                if(buildingAt != null)
                {
                    ZoneType zoneType = buildingAt.getZoneType();
                    //keep non zone buildings
                    if(zoneType == null)
                        continue;
                    
                    world.removeBuildingAt(x, y, true, true);
                }

                world.addBuilding(building, marking.x + x, marking.y + y);
            }
        }
    }

    @Override
    public BuildingType getBuilding()
    {
        return building;
    }

    @Override
    public Image getMarker()
    {
        return building.getDefaultImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
