package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class FireData extends StructureData
{
    @JsonProperty
    public int remainingLife;
    
    public FireData(Fire f)
    {
        super(f.getType().getName(), f.getX(), f.getY(), f.getVariant().name());
        FireParameters data = f.getData();
        this.remainingLife = data.getRemainingLife();
    }

    @JsonCreator
    public FireData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("remainingLife") int remainingLife)
    {
        super(type, x, y, variant);
        this.remainingLife = remainingLife;
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return FireType.Variant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        return new FireParameters(remainingLife);
    }
}
