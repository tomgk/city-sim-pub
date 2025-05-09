package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class BuildingType extends StructureType<Building, BuildingType.Variant, EmptyStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    
    /*
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO, UpdateAfterTick.NOTHING, null);
    }*/
    
    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        super(name, animation, size);
        this.zoneType = Objects.requireNonNull(zoneType);
        zoneType.addBuilding(this);
        this.cost = cost;
        this.maintenance = maintenance;
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
    public BuildingType.Variant getVariantForDefaultImage()
    {
        return BuildingType.Variant.DEFAULT;
    }

    @Override
    public Building createBuilding(int x, int y, Variant variant, EmptyStructureParameters data)
    {
        return new Building(this, x, y, variant, data);
    }
}
