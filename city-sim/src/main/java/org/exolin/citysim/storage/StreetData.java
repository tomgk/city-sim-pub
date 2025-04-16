package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;

/**
 *
 * @author Thomas
 */
public class StreetData extends BuildingData
{
    public StreetData(SelfConnection b)
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
    protected BuildingVariant getVariant(String name)
    {
        return ConnectionVariant.valueOf(name);
    }
}
