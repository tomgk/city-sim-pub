package org.exolin.citysim.model.tree;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private final TreeTypeType type;
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
    
    private record Key(int number, boolean alive, TreeTypeType type)
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
    
    public TreeType(TreeTypeType type, String name, BufferedImage image, int count, boolean alive)
    {
        super(name, createVariants(name, image), _1);
        this.type = Objects.requireNonNull(type);
        this.count = count;
        this.alive = alive;
        if(instances.putIfAbsent(new Key(count, alive, type), this) != null)
            throw new IllegalArgumentException();
    }
    
    public Optional<TreeType> plusOne()
    {
        return Optional.ofNullable(instances.get(new Key(count+1, alive, type)));
    }
    
    public TreeType getDead()
    {
        TreeType tree = instances.get(new Key(count, false, type));
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

    public boolean isAlive()
    {
        return alive;
    }

    public TreeTypeType getType()
    {
        return type;
    }
}
