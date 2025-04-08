package org.exolin.citysim.model;

import java.math.BigDecimal;

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
