package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.plant.Plant;
import org.exolin.citysim.model.plant.PlantParameters;
import org.exolin.citysim.model.plant.PlantVariant;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class PlantData extends StructureData
{
    @JsonProperty
    private final Optional<String> zone;
    
    public PlantData(Plant b)
    {
        super(b);
        this.zone = b.getDataCopy().getZone().map(ZoneType::getName);
    }

    @JsonCreator
    public PlantData(@JsonProperty("type") String type,
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
        return PlantVariant.valueOf(name);
    }

    @Override
    protected PlantParameters getParameters()
    {
        return new PlantParameters(zone.map(n -> BuildingType.getByName(ZoneType.class, n)));
    }
}
