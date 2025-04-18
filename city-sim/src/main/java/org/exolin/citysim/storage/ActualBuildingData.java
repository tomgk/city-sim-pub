package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.ab.Building;
import org.exolin.citysim.model.ab.BuildingType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class ActualBuildingData extends BuildingData
{
    public ActualBuildingData(Building b)
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
    protected StructureVariant getVariant(String name)
    {
        return BuildingType.Variant.valueOf(name);
    }
}
