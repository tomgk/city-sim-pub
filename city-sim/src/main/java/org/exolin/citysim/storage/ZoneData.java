package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.Zone;
import org.exolin.citysim.model.ZoneType;

/**
 *
 * @author Thomas
 */
public class ZoneData extends BuildingData
{
    public ZoneData(Zone b)
    {
        super(b);
    }

    @JsonCreator
    public ZoneData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected BuildingVariant getVariant(String name)
    {
        return ZoneType.Variant.valueOf(name);
    }
}
