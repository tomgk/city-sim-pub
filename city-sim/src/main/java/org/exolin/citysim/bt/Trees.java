package org.exolin.citysim.bt;

import java.util.List;
import java.util.stream.IntStream;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class Trees
{
    private static TreeType create(boolean isGrass, boolean isDead, int count)
    {
        String baseName = (isGrass ? "grass" : "trees");
        String add = isDead ? "dead_" : "";
        
        String name = baseName+"_"+add+count;
        String fname = baseName+"/"+add+count;
        
        return new TreeType(isGrass, name, ImageUtils.loadImage(fname), count, !isDead);
    }
    
    public static final List<TreeType> XTREES = IntStream.range(1, 8)
            .mapToObj(count -> create(false, false, count))
            .toList();
    
    public static final List<TreeType> XDEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> create(false, true, count))
            .toList();
    
    public static final List<TreeType> GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(true, false, count))
            .toList();
    
    public static final List<TreeType> DEAD_GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(true, true, count))
            .toList();
    
    public static List<TreeType> get(boolean isGrass)
    {
        return isGrass ? GRASS : XTREES;
    }
    
    public static List<TreeType> getDead(boolean isGrass)
    {
        return isGrass ? DEAD_GRASS : XDEAD_TREES;
    }
}
