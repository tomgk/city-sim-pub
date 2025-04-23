package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class FireData extends StructureData
{
    public FireData(Fire f)
    {
        super(f.getType().getName(), f.getX(), f.getY(), f.getVariant().name());
    }

    @JsonCreator
    public FireData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return FireType.Variant.valueOf(name);
    }
}
