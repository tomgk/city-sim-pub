package org.exolin.citysim.bt;

import java.util.List;
import java.util.stream.IntStream;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class Trees
{
    private static PlantType create(PlantTypeType type, boolean isDead, int count)
    {
        String baseName = type.baseName();
        String add = isDead ? "dead_" : "";
        
        String name = baseName+"_"+add+count;
        String fname = baseName+"/"+add+count;
        
        return new PlantType(type, name, ImageUtils.loadImage(fname), count, !isDead);
    }
    
    public static final List<PlantType> XTREES = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.TREE, false, count))
            .toList();
    
    public static final List<PlantType> XDEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.TREE, true, count))
            .toList();
    
    public static final List<PlantType> GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.GRASS, false, count))
            .toList();
    
    public static final List<PlantType> DEAD_GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.GRASS, true, count))
            .toList();
    
    public static List<PlantType> get(PlantTypeType type)
    {
        return type.isGrass() ? GRASS : XTREES;
    }
    
    public static List<PlantType> getDead(PlantTypeType type)
    {
        return type.isGrass() ? DEAD_GRASS : XDEAD_TREES;
    }
}
