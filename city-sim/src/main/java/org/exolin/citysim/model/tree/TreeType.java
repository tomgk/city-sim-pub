package org.exolin.citysim.model.tree;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class TreeType extends StructureType<Tree, TreeVariant, TreeParameters>
{
    private final boolean isGrass;
    private final int count;
    private final boolean alive;
    
    public static final int COST = 3;

    @Override
    public int getBuildingCost(TreeVariant variant)
    {
        //cost of n trees
        //Not used for count != 1
        return COST * count;
    }
    
    private record Key(int number, boolean alive, boolean isGrass)
    {
        
    }
    
    private static final Map<Key, TreeType> instances = new LinkedHashMap<>();
    
    private static List<Animation> createVariants(String name, BufferedImage image)
    {
        List<Animation> variants = new ArrayList<>(TreeVariant.VALUES.size());
        
        for(TreeVariant v : TreeVariant.VALUES)
        {
            String variantName = name;
            if(v != TreeVariant.DEFAULT)
                variantName += "@"+v;
            
            variants.add(Animation.createUnanimated(variantName, variantName, ImageUtils.createOffsetImage(image, v.getXoffset(), v.getYoffset())));
        }
        
        return variants;
    }
    
    public TreeType(boolean isGrass, String name, BufferedImage image, int count, boolean alive)
    {
        super(name, createVariants(name, image), _1);
        this.isGrass = isGrass;
        this.count = count;
        this.alive = alive;
        if(instances.putIfAbsent(new Key(count, alive, isGrass), this) != null)
            throw new IllegalArgumentException();
    }
    
    public Optional<TreeType> plusOne()
    {
        return Optional.ofNullable(instances.get(new Key(count+1, alive, isGrass)));
    }
    
    public TreeType getDead()
    {
        TreeType tree = instances.get(new Key(count, false, isGrass));
        if(tree == null)
            throw new IllegalArgumentException();
        return tree;
    }

    public int getCount()
    {
        return count;
    }
    
    @Override
    public Tree createBuilding(int x, int y, TreeVariant variant, TreeParameters data)
    {
        return new Tree(this, x, y, variant, data);
    }

    @Override
    public TreeVariant getVariantForDefaultImage()
    {
        return TreeVariant.DEFAULT;
    }

    boolean isAlive()
    {
        return alive;
    }

    public boolean isGrass()
    {
        return isGrass;
    }
}
