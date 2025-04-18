package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.ab.Building;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.StructureVariant;

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
    
    public BuildingData(Structure b)
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
    
    public static BuildingData create(Structure b)
    {
        if(b.getClass() == SelfConnection.class)
            return new StreetData((SelfConnection)b);
        else if(b.getClass() == Building.class)
            return new ActualBuildingData((Building)b);
        else if(b.getClass() == Zone.class)
            return new ZoneData((Zone)b);
        else if(b.getClass() == CrossConnection.class)
            return new CrossConnectionData((CrossConnection)b);
        else if(b.getClass() == Tree.class)
            return new TreeData((Tree)b);
        else
            throw new UnsupportedOperationException(b.getClass().getName());
    }

    static Class<?> getBuildingDataClass(Class<?> buildingClass)
    {
        if(buildingClass == SelfConnection.class)
            return StreetData.class;
        else if(buildingClass == Building.class)
            return ActualBuildingData.class;
        else if(buildingClass == Zone.class)
            return ZoneData.class;
        else if(buildingClass == Tree.class)
            return TreeData.class;
        else
            throw new UnsupportedOperationException(buildingClass.getName());
    }
    
    protected abstract StructureVariant getVariant(String name);

    void createBuilding(World w)
    {
        StructureType buildingType = StructureType.getByName(type);
        StructureVariant buildingVariant = getVariant(this.variant != null ? this.variant.toUpperCase() : DEFAULT_NAME);
        
        w.addBuilding(buildingType, x, y, buildingVariant);
    }
}
