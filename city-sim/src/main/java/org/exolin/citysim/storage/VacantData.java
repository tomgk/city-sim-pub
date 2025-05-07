package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.vacant.Vacant;
import org.exolin.citysim.model.building.vacant.VacantParameters;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class VacantData extends StructureData
{
    @JsonProperty
    public final Optional<String> zone;
    
    public VacantData(Vacant f)
    {
        super(f);
        VacantParameters data = f.getDataCopy();
        this.zone = data.getZoneType().map(ZoneType::getName);
    }

    @JsonCreator
    public VacantData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("zone") Optional<String> zone)
    {
        super(type, x, y, variant);
        this.zone = zone;
    }

    @Override
    protected StructureVariant getVariant(String name)
    {
        return VacantType.Variant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        return new VacantParameters(zone.map(n -> StructureType.getByName(ZoneType.class, n)));
    }
}
