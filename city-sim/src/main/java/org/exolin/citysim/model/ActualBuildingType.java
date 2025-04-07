package org.exolin.citysim.model;

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
    
    public ActualBuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        super(name, animation, size);
        this.zoneType = Objects.requireNonNull(zoneType);
        zoneType.addBuilding(this);
        this.cost = cost;
    }

    public int getCost()
    {
        return cost;
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
