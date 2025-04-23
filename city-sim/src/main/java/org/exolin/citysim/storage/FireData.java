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
    private final int remaining;
    
    public FireData(Fire f)
    {
        super(f.getType().getName(), f.getX(), f.getY(), f.getVariant().name());
        this.remaining = f.getRemaining();
    }

    @JsonCreator
    public FireData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("remaining") int remaining)
    {
        super(type, x, y, variant);
        this.remaining = remaining;
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return FireType.Variant.valueOf(name);
    }
}
