package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(Include.NON_NULL)
public class FireData extends StructureData
{
    @JsonProperty
    public int remainingLife;
    
    @JsonProperty
    public String zone;
    
    @JsonProperty
    public boolean returnToZone;
    
    public FireData(Fire f)
    {
        super(f);
        FireParameters data = f.getData();
        this.remainingLife = data.getRemainingLife();
        this.zone = Optional.ofNullable(f.getZoneType()).map(ZoneType::getName).orElse(null);
    }

    @JsonCreator
    public FireData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("remainingLife") int remainingLife,
            @JsonProperty("zone") String zone,
            @JsonProperty("returnToZone") boolean returnToZone)
    {
        super(type, x, y, variant);
        this.remainingLife = remainingLife;
        this.zone = zone;
        this.returnToZone = returnToZone;
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return FireType.Variant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        Optional<ZoneType> zoneType = Optional.ofNullable(zone)
                .map(n -> BuildingType.getByName(ZoneType.class, n));
        
        return new FireParameters(remainingLife, zoneType, returnToZone);
    }
}
