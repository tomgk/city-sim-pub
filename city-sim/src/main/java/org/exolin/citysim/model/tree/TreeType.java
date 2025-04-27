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
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.utils.RandomUtils;
import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public class TreeType extends StructureType<Tree, TreeType.Variant, PlainStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT(0, 0),
        LEFT(-1, 0),
        RIGHT(1, 0),
        
        TOP_LEFT(-1, -1),
        TOP_MIDDLE(0, -1),
        TOP_RIGHT(1, -1),
        
        BOTTOM_LEFT(-1, -1),
        BOTTOM_MIDDLE(0, -1),
        BOTTOM_RIGHT(1, -1);
        
        private static final List<Variant> VALUES = List.of(values());
        
        private final int xoffset;
        private final int yoffset;

        private Variant(int xoffset, int yoffset)
        {
            this.xoffset = xoffset;
            this.yoffset = yoffset;
        }

        public int getXoffset()
        {
            return xoffset;
        }

        public int getYoffset()
        {
            return yoffset;
        }
        
        public static Variant random()
        {
            return RandomUtils.random(VALUES);
        }
    }
    
    private final int count;
    
    private static final Map<Integer, TreeType> instances = new LinkedHashMap<>();
    
    private static List<Animation> createVariants(String name, BufferedImage image)
    {
        List<Animation> variants = new ArrayList<>(Variant.VALUES.size());
        
        for(Variant v : Variant.VALUES)
            variants.add(Animation.createUnanimated(name+"_"+v, Utils.createOffsetImage(image, v.getXoffset(), v.getYoffset())));
        
        return variants;
    }
    
    public TreeType(String name, BufferedImage image, int count)
    {
        super(name, createVariants(name, image), 1);
        this.count = count;
        if(instances.putIfAbsent(count, this) != null)
            throw new IllegalArgumentException();
    }
    
    public Optional<TreeType> plusOne()
    {
        return Optional.ofNullable(instances.get(count+1));
    }

    public int getCount()
    {
        return count;
    }
    
    @Override
    public Tree createBuilding(int x, int y, Variant variant, PlainStructureParameters data)
    {
        return new Tree(this, x, y, variant, data);
    }

    @Override
    public Variant getVariantForDefaultImage()
    {
        return Variant.DEFAULT;
    }
}
