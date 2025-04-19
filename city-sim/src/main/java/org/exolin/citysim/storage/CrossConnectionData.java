package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class CrossConnectionData extends StructureData
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
    protected StructureVariant getVariant(String name)
    {
        return CrossConnectionType.Variant.valueOf(name);
    }
    
}
