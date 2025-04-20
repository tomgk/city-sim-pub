package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class BuildingType extends StructureType<Building, BuildingType.Variant>
{
    public interface UpdateAfterTick
    {
        void update(World world, Building b);
        public static final Nothing NOTHING = new Nothing();
    }
    
    private static class Nothing implements UpdateAfterTick
    {
        @Override
        public void update(World t, Building u)
        {
        }
    }
 
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    private final UpdateAfterTick updateAfterTick;
    
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO, UpdateAfterTick.NOTHING);
    }
    
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
    public Building createBuilding(int x, int y, Variant variant)
    {
        return new Building(this, x, y, variant);
    }

    void updateAfterTick(World world, Building building)
    {
        updateAfterTick.update(world, building);
    }
}
