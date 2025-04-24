package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import org.exolin.citysim.model.PlainStructureData;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Tree extends Structure<Tree, TreeType, TreeType.Variant, PlainStructureData>
{
    public Tree(TreeType type, int x, int y, TreeType.Variant variant)
    {
        this(type, x, y, variant, new PlainStructureData());
    }
    
    public Tree(TreeType type, int x, int y, TreeType.Variant variant, PlainStructureData data)
    {
        super(type, x, y, variant, data);
    }
    
    public int getCount()
    {
        return getType().getCount();
    }

    @Override
    public BigDecimal getMaintenance()
    {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTaxRevenue()
    {
        return BigDecimal.ZERO;
    }
}
