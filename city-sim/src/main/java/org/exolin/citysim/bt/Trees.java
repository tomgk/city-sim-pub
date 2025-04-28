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
    private static TreeType createTree(int count, boolean alive)
    {
        return new TreeType((!alive ? "dead_" : "")+"trees_"+count, ImageUtils.loadImage("trees/"+count), count, alive);
    }
    
    public static final List<TreeType> TREES = IntStream.range(1, 8)
            .mapToObj(count -> createTree(count, true))
            .toList();
    
    public static final List<TreeType> DEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> createTree(count, false))
            .toList();
}
