package org.exolin.citysim.bt;

import java.util.List;
import java.util.stream.IntStream;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.TreeType;

/**
 *
 * @author Thomas
 */
public class Trees
{
    public static TreeType createTree(int count)
    {
        return new TreeType("trees_"+count, Animation.createUnanimated("trees/"+count), count);
    }
    
    public static final List<TreeType> TREES = IntStream.range(1, 8)
            .mapToObj(Trees::createTree)
            .toList();
}
