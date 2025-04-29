package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.building.vacant.VacantsPack;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Building extends Structure<Building, BuildingType, BuildingType.Variant, PlainStructureParameters>
{   
    public Building(BuildingType type, int x, int y, BuildingType.Variant version)
    {
        this(type, x, y, version, new PlainStructureParameters());
    }
    
    public Building(BuildingType type, int x, int y, BuildingType.Variant version, PlainStructureParameters data)
    {
        super(type, x, y, version, data);
    }

    @Override
    public ZoneType getZoneType()
    {
        return getType().getZoneType();
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return getType().getMaintenance();
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        //TODO
        
        //very trivial temporary way to get a number
        ZoneType zt = getType().getZoneType();
        if(zt != null && zt.isUserPlaceableZone())
        {
            int size = getType().getSize();
            int area = size * size;
            
            return BigDecimal.valueOf(area).divide(BigDecimal.TEN);
        }
        
        return BigDecimal.ZERO;
    }

    @Override
    protected void updateAfterTick(World world, int ticks)
    {
        int distance = 3;
        int size = getSize();
        
        int ystart = getY() - distance;
        int yend = getY() + size + distance;
        int xstart = getX() - distance;
        int xend = getX() + size + distance;
        
        for(int y = ystart; y < yend; ++y)
        {
            for(int x = xstart; x < xend; ++x)
            {
                Structure s = world.getBuildingAt(x, y);
                if(s != null && s.getType() == SelfConnections.street)
                    return;
            }
        }
        
        world.removeBuildingAt(this);
        VacantType vacant = VacantsPack.getRandom(getZoneType(), getSize());
        world.addBuilding(vacant, getX(), getY());
    }
}
