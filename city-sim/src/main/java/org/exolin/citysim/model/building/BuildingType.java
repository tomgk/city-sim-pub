package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class BuildingType extends StructureType<Building, BuildingType.Variant, PlainStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    private final UpdateAfterTick updateAfterTick;
    
    /*
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO, UpdateAfterTick.NOTHING, null);
    }*/
    
    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost, BigDecimal maintenance, UpdateAfterTick updateAfterTick)
    {
        super(name, animation, size);
        this.zoneType = Objects.requireNonNull(zoneType);
        zoneType.addBuilding(this);
        this.cost = cost;
        this.maintenance = maintenance;
        this.updateAfterTick = Objects.requireNonNull(updateAfterTick);
    }

    public int getCost()
    {
        return cost;
    }

    public BigDecimal getMaintenance()
    {
        return maintenance;
    }
    
    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public BuildingType.Variant getDefaultVariant()
    {
        return BuildingType.Variant.DEFAULT;
    }

    @Override
    public Building createBuilding(int x, int y, Variant variant, PlainStructureParameters data)
    {
        return new Building(this, x, y, variant, data);
    }

    void updateAfterTick(World world, Building building, int ticks, Object buildingData)
    {
        updateAfterTick.update(world, building, ticks, buildingData);
    }
}
