package org.exolin.citysim.model.plant;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class PlantType extends StructureType<Plant, PlantVariant, PlantParameters>
{
    private final PlantTypeType type;
    private final int count;
    private final boolean alive;
    
    public static final int COST = 3;

    @Override
    public int getBuildingCost(PlantVariant variant)
    {
        //cost of n plants
        //Not used for count != 1
        return COST * count;
    }
    
    private record Key(int number, boolean alive, PlantTypeType type)
    {
        
    }
    
    private static final Map<Key, PlantType> instances = new LinkedHashMap<>();
    
    private static List<Animation> createVariants(String name, BufferedImage image)
    {
        List<Animation> variants = new ArrayList<>(PlantVariant.VALUES.size());
        
        for(PlantVariant v : PlantVariant.VALUES)
        {
            String variantName = name;
            if(v != PlantVariant.DEFAULT)
                variantName += "@"+v;
            
            variants.add(Animation.createUnanimated(variantName, variantName, ImageUtils.createOffsetImage(image, v.getXoffset(), v.getYoffset())));
        }
        
        return variants;
    }
    
    public PlantType(PlantTypeType type, String name, BufferedImage image, int count, boolean alive)
    {
        super(name, createVariants(name, image), _1);
        this.type = Objects.requireNonNull(type);
        this.count = count;
        this.alive = alive;
        if(instances.putIfAbsent(new Key(count, alive, type), this) != null)
            throw new IllegalArgumentException();
    }
    
    public Optional<PlantType> plusOne()
    {
        return Optional.ofNullable(instances.get(new Key(count+1, alive, type)));
    }
    
    public PlantType getDead()
    {
        PlantType plant = instances.get(new Key(count, false, type));
        if(plant == null)
            throw new IllegalArgumentException();
        return plant;
    }

    public int getCount()
    {
        return count;
    }
    
    @Override
    public Plant createBuilding(int x, int y, PlantVariant variant, PlantParameters data)
    {
        return new Plant(this, x, y, variant, data);
    }

    @Override
    public PlantVariant getVariantForDefaultImage()
    {
        return PlantVariant.DEFAULT;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public PlantTypeType getType()
    {
        return type;
    }
}
