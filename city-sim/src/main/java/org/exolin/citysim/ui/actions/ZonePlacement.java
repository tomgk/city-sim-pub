package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Optional;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.plant.Plant;
import org.exolin.citysim.model.sim.RemoveMode;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class ZonePlacement extends AreaAction implements BuildingAction
{
    private final ZoneType zoneType;
    
    public static boolean DEBUG_PLANTZONE = false;

    public ZonePlacement(GetWorld world, ZoneType building)
    {
        super(world);
        this.zoneType = building;
    }

    @Override
    public String getName()
    {
        return "zone for "+zoneType.getName();
    }

    @Override
    public String getSubtext()
    {
        //if(variant == ZoneType.Variant.LOW_DENSITY)
        //    return "low density";
        
        return null;
    }

    @Override
    public int getCost()
    {
        return zoneType.getBuildingCost();
    }

    @Override
    public SingleVariant getVariant()
    {
        return SingleVariant.DEFAULT;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    protected void performAction(Rectangle marking)
    {
        performAction(marking, getWorld.get(), zoneType, getVariant(), zoneType.getBuildingCost(), false);
    }

    public static void performAction(Rectangle marking, World world, StructureType type, StructureVariant variant, int cost, boolean replaceEverything)
    {
        for(int y=0;y<marking.height;++y)
        {
            for(int x=0;x<marking.width;++x)
            {
                Structure<?, ?, ?, ?> buildingAt = world.getBuildingAt(marking.x + x, marking.y + y);
                if(buildingAt != null)
                {
                    Optional<ZoneType> buildingZoneType;
                    if(buildingAt instanceof Zone z)
                        buildingZoneType = Optional.of(z.getType());
                    //putting a zone on a plant => keed & zone tree
                    //maybe here for #109
                    else if(type instanceof ZoneType z && buildingAt instanceof Plant t)
                    {
                        t.setZone(Optional.of(z));
                        world.onUpdated(t);
                        if(DEBUG_PLANTZONE) System.out.println(t);
                        continue;
                    }
                    else
                        buildingZoneType = buildingAt.getTheZoneType();
                    
                    //keep non zone buildings
                    if(buildingZoneType.isEmpty() && !replaceEverything)
                        continue;
                    //don't change if already right zone
                    else if(buildingZoneType.equals(Optional.of(type)))
                        continue;
                    
                    world.removeBuildingAt(marking.x + x, marking.y + y, RemoveMode.CLEAR);
                }

                world.addBuilding(type, marking.x + x, marking.y + y, variant);
                world.reduceMoney(cost);
            }
        }
    }

    @Override
    public StructureType getBuilding()
    {
        return zoneType;
    }

    @Override
    public Image getIcon()
    {
        return ImageUtils.removeGround(zoneType.getImage(getVariant()).getDefault());
    }

    @Override
    public Image getMarker()
    {
        return zoneType.getImage(getVariant()).getDefault();
    }

    @Override
    public boolean scaleMarker()
    {
        return false;
    }
}
