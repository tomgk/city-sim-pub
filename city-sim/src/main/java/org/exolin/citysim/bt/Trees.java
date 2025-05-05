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
    private static TreeType createAliveTree(boolean isGrass, int count)
    {
        return new TreeType(isGrass, "trees_"+count, ImageUtils.loadImage("trees/"+count), count, true);
    }
    
    private static TreeType createDeadTree(boolean isGrass, int count)
    {
        return new TreeType(isGrass, "dead_trees_"+count, ImageUtils.loadImage("trees/dead_"+count), count, false);
    }
    
    public static final List<TreeType> TREES = IntStream.range(1, 8)
            .mapToObj(count -> createAliveTree(false, count))
            .toList();
    
    public static final List<TreeType> DEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> createDeadTree(false, count))
            .toList();
}
