package org.exolin.citysim.model;

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
}
