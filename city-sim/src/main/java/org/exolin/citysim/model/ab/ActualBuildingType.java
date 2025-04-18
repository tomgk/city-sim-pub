package org.exolin.citysim.model.ab;

import java.math.BigDecimal;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class ActualBuildingType extends StructureType<ActualBuilding, ActualBuildingType.Variant>
{
    public enum Variant implements StructureVariant
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
    
    @SuppressWarnings("LeakingThisInConstructor")
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
