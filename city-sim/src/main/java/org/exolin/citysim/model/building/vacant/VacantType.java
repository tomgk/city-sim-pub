package org.exolin.citysim.model.building.vacant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.StructureSize;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class VacantType extends StructureType<Vacant, SingleVariant, VacantParameters>
{
    private static final Map<Integer, List<VacantType>> vacants = new LinkedHashMap<>();
    
    public static VacantType getRandom(StructureSize size)
    {
        List<VacantType> forSize = vacants.get(size.toInteger());
        if(forSize == null)
            throw new IllegalArgumentException("nothing for "+size);
        
        return RandomUtils.random(forSize);
    }

    @Override
    public int getBuildingCost(SingleVariant variant)
    {
        return NO_COST;
    }
    
    public static List<VacantType> vacantTypes()
    {
        return types(VacantType.class);
    }

    private void addVacant(VacantType t)
    {
        List<VacantType> forSize = vacants.computeIfAbsent(t.getSize().toInteger(), s -> new ArrayList<>());
        forSize.add(t);
    }
    
    public static boolean isDestroyed(StructureType type)
    {
        if(type instanceof VacantType t)
            return t.isDestroyed();
        else
            return false;
    }
    
    private final boolean destroyed;

    @SuppressWarnings("LeakingThisInConstructor")
    public VacantType(String name, StructureSize size, boolean destroyed)
    {
        super(name, Animation.createUnanimated(name), size);
        this.destroyed = destroyed;
        addVacant(this);
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    @Override
    public SingleVariant getVariantForDefaultImage()
    {
        return SingleVariant.DEFAULT;
    }

    @Override
    public Vacant createBuilding(int x, int y, SingleVariant variant, VacantParameters data)
    {
        return new Vacant(this, x, y, variant, data);
    }
}