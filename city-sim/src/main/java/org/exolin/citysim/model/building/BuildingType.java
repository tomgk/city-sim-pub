package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
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
    
    private final String title;
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    
    private final Map<String, Object> custom = new LinkedHashMap<>();
    
    public void setCustom(String name, Object value)
    {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if(custom.putIfAbsent(name, value) != null)
            throw new IllegalStateException();
    }
    
    public <T> T getCustom(String name, Class<T> type)
    {
        Object val = custom.get(name);
        if(val == null)
            throw new IllegalArgumentException("no "+name+" in "+getName());
        try{
            return type.cast(val);
        }catch(ClassCastException e){
            throw new IllegalArgumentException(getName()+"."+name+" is of type "+val.getClass().getName()+" not "+type.getName());
        }
    }
    
    public boolean hasCustom(String name)
    {
        return custom.containsKey(name);
    }
    
    /*
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO, UpdateAfterTick.NOTHING, null);
    }*/
    
    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, String title, Animation animation, int size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        super(name, animation, size);
        this.title = Objects.requireNonNull(title);
        this.zoneType = Objects.requireNonNull(zoneType);
        zoneType.addBuilding(this);
        this.cost = cost;
        this.maintenance = maintenance;
    }

    public String getTitle()
    {
        return title;
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
