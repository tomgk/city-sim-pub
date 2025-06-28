package org.exolin.citysim.bt;

import java.util.List;
import java.util.stream.IntStream;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.tree.TreeTypeType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class Trees
{
    private static TreeType create(TreeTypeType type, boolean isDead, int count)
    {
        String baseName = type.baseName();
        String add = isDead ? "dead_" : "";
        
        String name = baseName+"_"+add+count;
        String fname = baseName+"/"+add+count;
        
        return new TreeType(type, name, ImageUtils.loadImage(fname), count, !isDead);
    }
    
    public static final List<TreeType> XTREES = IntStream.range(1, 8)
            .mapToObj(count -> create(TreeTypeType.TREE, false, count))
            .toList();
    
    public static final List<TreeType> XDEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> create(TreeTypeType.TREE, true, count))
            .toList();
    
    public static final List<TreeType> GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(TreeTypeType.GRASS, false, count))
            .toList();
    
    public static final List<TreeType> DEAD_GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(TreeTypeType.GRASS, true, count))
            .toList();
    
    public static List<TreeType> get(TreeTypeType type)
    {
        return type.isGrass() ? GRASS : XTREES;
    }
    
    public static List<TreeType> getDead(TreeTypeType type)
    {
        return type.isGrass() ? DEAD_GRASS : XDEAD_TREES;
    }
}
