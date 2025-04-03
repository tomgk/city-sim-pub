package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.ActualBuilding;
import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.BuildingVariant;

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
    protected BuildingVariant getVariant(String name)
    {
        return ActualBuildingType.Variant.valueOf(name);
    }
}
