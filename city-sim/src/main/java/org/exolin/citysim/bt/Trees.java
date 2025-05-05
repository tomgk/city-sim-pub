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
        return new TreeType(isGrass, (isGrass ? "grass_" : "trees_")+count, ImageUtils.loadImage("trees/"+count), count, true);
    }
    
    private static TreeType createDeadTree(boolean isGrass, int count)
    {
        return new TreeType(isGrass, (isGrass ? "dead_grass" : "dead_trees_")+count, ImageUtils.loadImage("trees/dead_"+count), count, false);
    }
    
    public static final List<TreeType> XTREES = IntStream.range(1, 8)
            .mapToObj(count -> createAliveTree(false, count))
            .toList();
    
    public static final List<TreeType> XDEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> createDeadTree(false, count))
            .toList();
    
    public static final List<TreeType> GRASS = IntStream.range(1, 8)
            .mapToObj(count -> createAliveTree(true, count))
            .toList();
    
    public static final List<TreeType> DEAD_GRASS = IntStream.range(1, 8)
            .mapToObj(count -> createDeadTree(true, count))
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
