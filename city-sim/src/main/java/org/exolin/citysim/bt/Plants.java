package org.exolin.citysim.bt;

import java.util.List;
import java.util.stream.IntStream;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.model.plant.PlantVariant;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class Plants
{
    private static PlantType create(PlantTypeType type, boolean isDead, int count)
    {
        String baseName = type.baseName();
        String add = isDead ? "dead_" : "";
        
        String name = baseName+"_"+add+count;
        String fname = baseName+"/"+add+count;
        
        return new PlantType(type, name, ImageUtils.loadImage(fname), count, !isDead);
    }
    
    private static final List<PlantType> TREES = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.TREE, false, count))
            .toList();
    
    private static final List<PlantType> DEAD_TREES = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.TREE, true, count))
            .toList();
    
    private static final List<PlantType> GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.GRASS, false, count))
            .toList();
    
    private static final List<PlantType> DEAD_GRASS = IntStream.range(1, 8)
            .mapToObj(count -> create(PlantTypeType.GRASS, true, count))
            .toList();
    
    public static PlantType getFirst(PlantTypeType type)
    {
        return getx(type).getFirst();
    }
    
    public static PlantType get(PlantTypeType type, int count)
    {
        return getx(type).get(count-1);
    }
    
    public static List<PlantType> getx(PlantTypeType type)
    {
        return type.isGrass() ? GRASS : TREES;
    }
    
    public static PlantType getFirstDead(PlantTypeType type)
    {
        return getDead(type).getFirst();
    }
    
    public static List<PlantType> getDead(PlantTypeType type)
    {
        return type.isGrass() ? DEAD_GRASS : DEAD_TREES;
    }

    public static int getSize()
    {
        return PlantVariant.values().length;
    }
}
