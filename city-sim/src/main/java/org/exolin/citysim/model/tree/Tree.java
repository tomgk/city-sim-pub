package org.exolin.citysim.model.tree;

import java.math.BigDecimal;
import org.exolin.citysim.model.Building;

/**
 *
 * @author Thomas
 */
public class Tree extends Building<Tree, TreeType, TreeType.Variant>
{
    public Tree(TreeType type, int x, int y, TreeType.Variant variant)
    {
        super(type, x, y, variant);
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
