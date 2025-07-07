package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
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
        return SingleVariant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        return EmptyStructureParameters.getInstance();
    }
}
