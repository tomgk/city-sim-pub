package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.Building;

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
}
