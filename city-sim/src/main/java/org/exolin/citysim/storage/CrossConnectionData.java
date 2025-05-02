package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.connection.cross.CrossConnection;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;

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
            @JsonProperty("variant") Optional<String> variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return CrossConnectionType.Variant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        return EmptyStructureParameters.getInstance();
    }
}
