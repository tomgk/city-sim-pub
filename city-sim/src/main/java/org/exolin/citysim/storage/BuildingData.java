package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.ab.Building;
import org.exolin.citysim.model.ab.BuildingType;

/**
 *
 * @author Thomas
 */
public class BuildingData extends StructureData
{
    public BuildingData(Building b)
    {
        super(b);
    }

    @JsonCreator
    public BuildingData(@JsonProperty("type") String type,
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
