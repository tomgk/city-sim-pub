package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class TreeType extends BuildingType<Tree, TreeType.Variant>
{
    public enum Variant implements BuildingVariant
    {
        DEFAULT
    }
    
    private final int count;
    
    public TreeType(String name, Animation animation, int count)
    {
        super(name, animation, 1);
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }
    
    @Override
    public Tree createBuilding(int x, int y, Variant variant)
    {
        return new Tree(this, x, y, variant);
    }

    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}
