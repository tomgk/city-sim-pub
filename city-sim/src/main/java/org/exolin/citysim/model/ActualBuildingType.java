package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Thomas
 */
public class ActualBuildingType extends BuildingType<ActualBuilding, ActualBuildingType.Variant>
{
    public enum Variant implements BuildingVariant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    
    public ActualBuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO);
    }
    
    public ActualBuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost, BigDecimal maintenance)
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
    public ActualBuildingType.Variant getDefaultVariant()
    {
        return ActualBuildingType.Variant.DEFAULT;
    }

    @Override
    public ActualBuilding createBuilding(int x, int y, Variant variant)
    {
        return new ActualBuilding(this, x, y, variant);
    }
}
