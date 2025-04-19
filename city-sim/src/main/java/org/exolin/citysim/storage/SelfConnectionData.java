package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class SelfConnectionData extends StructureData
{
    public SelfConnectionData(SelfConnection b)
    {
        super(b);
    }

    @JsonCreator
    public SelfConnectionData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return ConnectionVariant.valueOf(name);
    }
}
