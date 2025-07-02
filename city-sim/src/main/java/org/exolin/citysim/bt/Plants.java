package org.exolin.citysim.bt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    
    private static class Set
    {
        private final List<PlantType> alive;
        private final List<PlantType> dead;
        
        public Set(PlantTypeType type)
        {
            alive = IntStream.range(1, 8)
                    .mapToObj(count -> create(type, false, count))
                    .toList();

            dead = IntStream.range(1, 8)
                    .mapToObj(count -> create(type, true, count))
                    .toList();
        }
    }
    
    private static final Map<PlantTypeType, Set> TYPES = new LinkedHashMap<>();
    static
    {
        for(PlantTypeType t : PlantTypeType.values())
            TYPES.put(t, new Set(t));
    }
    
    public static PlantType getFirst(PlantTypeType type)
    {
        return getx(type).getFirst();
    }
    
    public static PlantType get(PlantTypeType type, int count)
    {
        var x = getx(type);
        try{
            return x.get(count-1);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException(count+" out of range [1.."+x.size()+"]");
        }
    }
    
    public static List<PlantType> getx(PlantTypeType type)
    {
        return getSet(type).alive;
    }
    
    private static Set getSet(PlantTypeType type)
    {
        Set s = TYPES.get(type);
        if(s == null)
            throw new IllegalArgumentException();
        return s;
    }
    
    public static PlantType getFirstDead(PlantTypeType type)
    {
        return getDead(type).getFirst();
    }
    
    public static List<PlantType> getDead(PlantTypeType type)
    {
        return getSet(type).dead;
    }

    public static int getSize()
    {
        return PlantVariant.values().length;
    }
}
