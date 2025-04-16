package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;

/**
 *
 * @author Thomas
 */
public class CrossConnectionData extends BuildingData
{
    public CrossConnectionData(CrossConnection b)
    {
        super(b);
    }

    @JsonCreator
    public CrossConnectionData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected BuildingVariant getVariant(String name)
    {
        return CrossConnectionType.Variant.valueOf(name);
    }
    
}
