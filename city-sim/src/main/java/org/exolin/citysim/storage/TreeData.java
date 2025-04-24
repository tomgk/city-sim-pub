package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeType;

/**
 *
 * @author Thomas
 */
public class TreeData extends StructureData
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
    protected StructureVariant getVariant(String name)
    {
        return TreeType.Variant.valueOf(name);
    }

    @Override
    protected StructureParameters getParameters()
    {
        return new PlainStructureParameters();
    }
}
