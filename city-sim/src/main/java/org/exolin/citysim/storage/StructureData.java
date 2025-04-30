package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.vacant.Vacant;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.zone.Zone;

/**
 *
 * @author Thomas
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonTypeIdResolver(BuildingDataTypeIdResolver.class)
@JsonIgnoreProperties({ "variantClass"})
public abstract class StructureData
{
    @JsonProperty
    private final String type;
    
    @JsonProperty
    private final int x;
    
    @JsonProperty
    private final int y;
    
    @JsonProperty
    @JsonInclude(Include.NON_NULL)
    private final String variant;
    
    private static final String DEFAULT_NAME = "DEFAULT";

    @JsonCreator
    public StructureData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        this.type = type;
        this.x = x;
        this.y = y;
        this.variant = variant;
    }
    
    public StructureData(Structure b)
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
    
    public static StructureData create(Structure b)
    {
        if(b.getClass() == SelfConnection.class)
            return new SelfConnectionData((SelfConnection)b);
        else if(b.getClass() == Building.class)
            return new BuildingData((Building)b);
        else if(b.getClass() == Zone.class)
            return new ZoneData((Zone)b);
        else if(b.getClass() == CrossConnection.class)
            return new CrossConnectionData((CrossConnection)b);
        else if(b.getClass() == Tree.class)
            return new TreeData((Tree)b);
        else if(b.getClass() == Fire.class)
            return new FireData((Fire)b);
        else if(b.getClass() == Vacant.class)
            return new VacantData((Vacant)b);
        else
            throw new UnsupportedOperationException(b.getClass().getName());
    }

    static Class<?> getBuildingDataClass(Class<?> buildingClass)
    {
        if(buildingClass == SelfConnection.class)
            return SelfConnectionData.class;
        else if(buildingClass == Building.class)
            return BuildingData.class;
        else if(buildingClass == Zone.class)
            return ZoneData.class;
        else if(buildingClass == Tree.class)
            return TreeData.class;
        else if(buildingClass == Fire.class)
            return FireData.class;
        else if(buildingClass == Vacant.class)
            return VacantData.class;
        else
            throw new UnsupportedOperationException(buildingClass.getName());
    }
    
    protected abstract StructureVariant getVariant(String name);
    protected abstract StructureParameters getParameters();

    void createBuilding(World w)
    {
        StructureType buildingType = StructureType.getByName(type);
        StructureVariant buildingVariant;
        try{
            buildingVariant = getVariant(this.variant != null ? this.variant.toUpperCase() : DEFAULT_NAME);
        }catch(IllegalArgumentException e){
            StackTraceElement[] stackTrace = e.getStackTrace();
            StackTraceElement origin = stackTrace[0];
            if(origin.getClassName().equals(Enum.class.getName()) && origin.getMethodName().equals("valueOf"))
            {
                IllegalArgumentException e2 = new IllegalArgumentException("no variant specified and no default variant for "+stackTrace[1].getClassName());
                e2.addSuppressed(e);
                throw e2;
            }
            
            throw e;
        }
        
        w.addBuilding(buildingType, x, y, buildingVariant, getParameters());
    }
}
