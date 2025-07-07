package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.vacant.VacantParameters;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Building extends Structure<Building, BuildingType, BuildingType.Variant, EmptyStructureParameters>
{   
    public Building(BuildingType type, int x, int y, BuildingType.Variant version)
    {
        this(type, x, y, version, EmptyStructureParameters.getInstance());
    }
    
    public Building(BuildingType type, int x, int y, BuildingType.Variant version, EmptyStructureParameters data)
    {
        super(type, x, y, version, data);
    }

    @Override
    public Optional<ZoneType> getZoneType(boolean includeEmptyZone)
    {
        return Optional.of(getType().getZoneType());
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
            int FACTOR = 1;
            
            int size = getType().getSize().toInteger();
            int area = size * size * FACTOR;
            
            return BigDecimal.valueOf(area).divide(BigDecimal.TEN);
        }
        
        return BigDecimal.ZERO;
    }

    @Override
    protected void updateAfterTick(World world, int ticks)
    {
        if(getType().getZoneType() == null)
            return;
        
        if(!getType().getZoneType().isUserPlaceableZone())
            return;
        
        int distance = 3;
        int size = getSize().toInteger();
        
        int ystart = getY() - distance;
        int yend = getY() + size + distance;
        int xstart = getX() - distance;
        int xend = getX() + size + distance;
        
        for(int y = ystart; y < yend; ++y)
        {
            for(int x = xstart; x < xend; ++x)
            {
                Structure<?, ?, ?, ?> s = world.getBuildingAt(x, y);
                if(s != null && s.getType() == SelfConnections.street)
                    return;
            }
        }
        
        world.removeBuildingAt(this);
        VacantType vacant = VacantType.getRandom(getSize());
        world.addBuilding(vacant, getX(), getY(), SingleVariant.DEFAULT, new VacantParameters(getTheZoneType()));
    }
}
