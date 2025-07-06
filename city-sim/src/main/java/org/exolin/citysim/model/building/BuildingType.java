package org.exolin.citysim.model.building;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.CustomKey;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureSize;
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
        DEFAULT("0°"), ROTATED("90°");
        
        private final String info;

        private Variant(String info)
        {
            this.info = info;
        }

        @Override
        public Optional<String> getInfo()
        {
            return Optional.of(info);
        }
    }
    
    private final String title;
    private final ZoneType zoneType;
    private final int cost;
    private final BigDecimal maintenance;
    
    private final Map<CustomKey, Object> custom = new LinkedHashMap<>();
    
    public void setCustom(CustomKey name, Object value)
    {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        if(custom.putIfAbsent(name, value) != null)
            throw new IllegalStateException();
    }
    
    @Override
    public <T> T getCustom(CustomKey name, Class<T> type)
    {
        Object val = custom.get(name);
        if(val == null)
            throw noCustom(name);
        try{
            return type.cast(val);
        }catch(ClassCastException e){
            throw new IllegalArgumentException(getName()+"."+name+" is of type "+val.getClass().getName()+" not "+type.getName());
        }
    }
    
    @Override
    public Set<CustomKey> customKeys()
    {
        return Collections.unmodifiableSet(custom.keySet());
    }
    
    @Override
    public boolean hasCustom(CustomKey name)
    {
        return custom.containsKey(name);
    }
    
    /*
    public BuildingType(String name, Animation animation, int size, ZoneType zoneType, int cost)
    {
        this(name, animation, size, zoneType, cost, BigDecimal.ZERO, UpdateAfterTick.NOTHING, null);
    }*/
    
    
    public BuildingType(String name, String title, Animation animation, StructureSize size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        this(name, title, List.of(animation, animation), size, zoneType, cost, maintenance);
    }
    
    public BuildingType(String name, String title, Animation animation, Animation animation2, StructureSize size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        this(name, title, List.of(animation, animation2), size, zoneType, cost, maintenance);
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    private BuildingType(String name, String title, List<Animation> animations, StructureSize size, ZoneType zoneType, int cost, BigDecimal maintenance)
    {
        super(name, animations, size);
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

    @Override
    public int getBuildingCost(Variant variant)
    {
        return getCost();
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
