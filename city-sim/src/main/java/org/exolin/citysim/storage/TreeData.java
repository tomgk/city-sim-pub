package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeType;

/**
 *
 * @author Thomas
 */
public class TreeData extends BuildingData
{
    public TreeData(Tree b)
    {
        super(b);
    }

    @JsonCreator
    public TreeData(@JsonProperty("type") String type,
            @JsonProperty("x") int x, @JsonProperty("y") int y,
            @JsonProperty("variant") String variant)
    {
        super(type, x, y, variant);
    }

    @Override
    protected BuildingVariant getVariant(String name)
    {
        return TreeType.Variant.valueOf(name);
    }
}
