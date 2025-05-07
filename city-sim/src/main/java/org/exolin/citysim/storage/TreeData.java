package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeVariant;
import org.exolin.citysim.model.zone.ZoneState;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class TreeData extends StructureData
{
    @JsonProperty
    private final Optional<String> zone;
    
    public TreeData(Tree b)
    {
        super(b);
        this.zone = b.getDataCopy().getZone().map(ZoneState::type).map(ZoneType::getName);
    }

    @JsonCreator
    public TreeData(@JsonProperty("type") String type,
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
        return TreeVariant.valueOf(name);
    }

    @Override
    protected TreeParameters getParameters()
    {
        //TODO: variant
        return new TreeParameters(zone.map(n -> BuildingType.getByName(ZoneType.class, n)).map(z-> new ZoneState(z, ZoneType.Variant.DEFAULT)));
    }
}
