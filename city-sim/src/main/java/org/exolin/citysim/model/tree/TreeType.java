package org.exolin.citysim.model.tree;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class TreeType extends StructureType<Tree, TreeVariant, PlainStructureParameters>
{
    private final int count;
    private final boolean alive;
    
    private static final Map<Integer, TreeType> aliveInstances = new LinkedHashMap<>();
    private static final Map<Integer, TreeType> deadInstances = new LinkedHashMap<>();
    
    private static List<Animation> createVariants(String name, BufferedImage image)
    {
        List<Animation> variants = new ArrayList<>(TreeVariant.VALUES.size());
        
        for(TreeVariant v : TreeVariant.VALUES)
            variants.add(Animation.createUnanimated(name+"_"+v, ImageUtils.createOffsetImage(image, v.getXoffset(), v.getYoffset())));
        
        return variants;
    }

    private static Map<Integer, TreeType> getInstances(boolean alive)
    {
        return alive ? aliveInstances : deadInstances;
    }
    
    public TreeType(String name, BufferedImage image, int count, boolean alive)
    {
        super(name, createVariants(name, image), 1);
        this.count = count;
        this.alive = alive;
        if(getInstances(alive).putIfAbsent(count, this) != null)
            throw new IllegalArgumentException();
    }
    
    public Optional<TreeType> plusOne()
    {
        return Optional.ofNullable(getInstances(alive).get(count+1));
    }

    public int getCount()
    {
        return count;
    }
    
    @Override
    public Tree createBuilding(int x, int y, TreeVariant variant, PlainStructureParameters data)
    {
        return new Tree(this, x, y, variant, data);
    }

    @Override
    public TreeVariant getVariantForDefaultImage()
    {
        return TreeVariant.DEFAULT;
    }
}
