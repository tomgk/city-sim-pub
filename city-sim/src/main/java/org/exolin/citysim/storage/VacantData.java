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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacantData extends StructureData
{
    @JsonProperty
    public final String zone;
    
    public VacantData(Vacant f)
    {
        super(f);
        VacantParameters data = f.getData();
        this.zone = data.getZoneType().map(ZoneType::getName).orElse(null);
    }

    @JsonCreator
    public VacantData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant,
            @JsonProperty("zone") String zone)
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
        return new VacantParameters(Optional.ofNullable(zone).map(n -> StructureType.getByName(ZoneType.class, n)));
    }
}
