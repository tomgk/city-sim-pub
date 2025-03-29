package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.Building;
import org.exolin.citysim.Street;

/**
 *
 * @author Thomas
 */
public class BuildingData
{
    @JsonProperty
    public String type;
    
    @JsonProperty
    public int x;
    
    @JsonProperty
    public int y;
    
    @JsonProperty
    public int variant;
    
    public BuildingData(Building b)
    {
        this.type = b.getType().getName();
        this.x = b.getX();
        this.y = b.getY();
        this.variant = b.getVariant();
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
}
