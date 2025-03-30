package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class ActualBuildingData extends BuildingData
{
    public ActualBuildingData(ActualBuilding b)
    {
        super(b);
    }

    @JsonCreator
    public ActualBuildingData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    public Class<? extends Enum<?>> getVariantClass()
    {
        return ActualBuildingType.Variant.class;
    }
    
    
}
