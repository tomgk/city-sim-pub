package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireVariant;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(Include.NON_ABSENT)
public class FireData extends StructureData
{
    @JsonProperty
    public final int remainingLife;
    
    @JsonProperty
    public final Optional<String> zone;
    
    @JsonProperty
    public final boolean returnToZone;
    
    @JsonProperty
    public final Optional<String> afterBurn;
    
    public FireData(Fire f)
    {
        super(f);
        FireParameters data = f.getDataCopy();
        this.remainingLife = data.getRemainingLife();
        this.zone = f.getTheZoneType().map(ZoneType::getName);
        this.returnToZone = data.isReturnToZone();
        this.afterBurn = data.getAfterBurn().map(StructureType::getName);
    }

    @JsonCreator
    public FireData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("remainingLife") int remainingLife,
            @JsonProperty("zone") Optional<String> zone,
            @JsonProperty("returnToZone") boolean returnToZone,
            @JsonProperty("afterBurn") Optional<String> afterBurn)
    {
        super(type, x, y, variant);
        this.remainingLife = remainingLife;
        this.zone = zone;
        this.returnToZone = returnToZone;
        this.afterBurn = afterBurn;
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return FireVariant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        Optional<ZoneType> zoneType = zone
                .map(n -> BuildingType.getByName(ZoneType.class, n));
        Optional<StructureType> afterBurnType = afterBurn
                .map(StructureType::getByName);
        
        return new FireParameters(remainingLife, zoneType, returnToZone, afterBurnType);
    }
}
