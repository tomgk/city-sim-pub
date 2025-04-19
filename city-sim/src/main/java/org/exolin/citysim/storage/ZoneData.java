package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class ZoneData extends StructureData
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
    protected StructureVariant getVariant(String name)
    {
        return ZoneType.Variant.valueOf(name);
    }
}
