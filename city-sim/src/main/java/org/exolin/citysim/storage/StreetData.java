package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.Street;
import org.exolin.citysim.StreetType;

/**
 *
 * @author Thomas
 */
public class StreetData extends BuildingData
{
    public StreetData(Street b)
    {
        super(b);
    }

    @JsonCreator
    public StreetData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    public Class<? extends Enum<?>> getVariantClass()
    {
        return StreetType.Variant.class;
    }
}
