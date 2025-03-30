package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.Zone;
import org.exolin.citysim.ZoneType;

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
    public Class<? extends Enum<?>> getVariantClass()
    {
        return ZoneType.Variant.class;
    }
}
