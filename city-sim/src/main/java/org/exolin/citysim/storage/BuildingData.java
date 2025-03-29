package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.Building;
import org.exolin.citysim.Street;
import static org.exolin.citysim.ZoneType.Variant.DEFAULT;

/**
 *
 * @author Thomas
 */
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
    
    public BuildingData(Building b)
    {
        this.type = b.getType().getName();
        this.x = b.getX();
        this.y = b.getY();
        this.variant = b.getVariant().name().toLowerCase();
        //only remove it from the file if it is named default
        //if it just happens to be the first, don't do it
        if(this.variant.equals("default"))
            this.variant = null;
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
    
    public abstract Class<? extends Enum<?>> getVariantClass();
}
