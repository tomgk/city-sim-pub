package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Rectangle;
import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.ZoneType;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 */
public class ZonePlacement extends AreaAction implements BuildingAction
{
    private final ZoneType zoneType;
    private final ZoneType.Variant variant;

    public ZonePlacement(GetWorld world, ZoneType building, ZoneType.Variant variant)
    {
        super(world);
        this.zoneType = building;
        this.variant = variant;
    }

    @Override
    public String getName()
    {
        return "zone for "+zoneType.getName();
    }

    @Override
    public String getSubtext()
    {
        if(variant == ZoneType.Variant.LOW_DENSITY)
            return "low density";
        
        return null;
    }

    @Override
    public int getCost()
    {
        return zoneType.getCost(variant);
    }

    @Override
    public BuildingVariant getVariant()
    {
        return variant;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    protected void performAction(Rectangle marking)
    {
        performAction(marking, getWorld.get(), zoneType, variant, zoneType.getCost(variant));
    }

    public static void performAction(Rectangle marking, World world, BuildingType type, BuildingVariant variant, int cost)
    {
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                Building buildingAt = world.getBuildingAt(marking.x + x, marking.y + y);
                if(buildingAt != null)
                {
                    ZoneType buildingZoneType = buildingAt.getZoneType();
                    //keep non zone buildings
                    if(buildingZoneType == null)
                        continue;
                    
                    world.removeBuildingAt(x, y, true, true);
                }

                world.addBuilding(type, marking.x + x, marking.y + y, variant);
                world.reduceMoney(cost);
            }
        }
    }

    @Override
    public BuildingType getBuilding()
    {
        return zoneType;
    }

    @Override
    public Image getIcon()
    {
        return Utils.removeGround(zoneType.getImage(variant).getDefault());
    }

    @Override
    public Image getMarker()
    {
        return zoneType.getImage(variant).getDefault();
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
