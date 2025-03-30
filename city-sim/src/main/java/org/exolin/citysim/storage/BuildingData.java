package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import java.util.Objects;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.Building;
import org.exolin.citysim.BuildingType;
import org.exolin.citysim.Street;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonTypeIdResolver(BuildingDataTypeIdResolver.class)
@JsonIgnoreProperties({ "variantClass"})
public abstract class BuildingData
{
    @JsonProperty
    public String type;
    
    @JsonProperty
    public int x;
    
    @JsonProperty
    public int y;
    
    @JsonProperty
    @JsonInclude(Include.NON_NULL)
    public String variant;
    
    private static final String DEFAULT_NAME = "DEFAULT";

    @JsonCreator
    public BuildingData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.variant = variant;
    }
    
    public BuildingData(Building b)
    {
        this.type = b.getType().getName();
        this.x = b.getX();
        this.y = b.getY();
        //only remove it from the file if it is named default
        //if it just happens to be the first, don't do it
        if(b.getVariant().name().equals(DEFAULT_NAME))
            this.variant = null;
        else
            this.variant = b.getVariant().name().toLowerCase();
    }
    
    public static BuildingData create(Building b)
    {
        if(b.getClass() == Street.class)
            return new StreetData((Street)b);
        else if(b.getClass() == ActualBuilding.class)
            return new ActualBuildingData((ActualBuilding)b);
        else
            throw new UnsupportedOperationException(b.getClass().getName());
    }

    static Class<?> getBuildingDataClass(Class<?> buildingClass)
    {
        if(buildingClass == Street.class)
            return StreetData.class;
        else if(buildingClass == ActualBuilding.class)
            return ActualBuildingData.class;
        else
            throw new UnsupportedOperationException(buildingClass.getName());
    }
    
    protected abstract Class<? extends Enum<?>> getVariantClass();

    void createBuilding(World w)
    {
        BuildingType buildingType = BuildingType.getByName(type);
        Enum buildingVariant = Enum.valueOf((Class)getVariantClass(), this.variant != null ? this.variant.toUpperCase() : DEFAULT_NAME);
        
        w.addBuilding(buildingType, x, y, buildingVariant);
    }
}
