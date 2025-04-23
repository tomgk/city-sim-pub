package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import org.exolin.citysim.model.Structure;

/**
 *
 * @author Thomas
 */
public class Tree extends Structure<Tree, TreeType, TreeType.Variant>
{
    public Tree(TreeType type, int x, int y, TreeType.Variant variant)
    {
        super(type, x, y, variant);
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
