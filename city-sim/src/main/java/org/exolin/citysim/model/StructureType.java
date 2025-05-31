package org.exolin.citysim.model;

import java.awt.image.BufferedImage;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.utils.ImageUtils;

/**
 * Type of {@link Structure}, for example a particular building or
 * infrastructure like street or rail.
 * 
 * @author Thomas
 * @param <B> building class
 * @param <E> building variant
 * @param <D>
 */
public abstract class StructureType<B, E extends StructureVariant, D extends StructureParameters>
{
    public static final int DEFAULT_VARIANT = 0;
    public static final int NO_COST = 0;
    
    public static Class<? extends StructureVariant> getStructureVariantClass(Class<? extends StructureType> clazz)
    {
        ParameterizedType superClass = (ParameterizedType)clazz.getGenericSuperclass();
        
        for(Type t : superClass.getActualTypeArguments())
        {
            if(t instanceof Class c)
                if(StructureVariant.class.isAssignableFrom(c))
                    return c;
        }
        
        throw new IllegalArgumentException("Could not determine "+StructureVariant.class.getName()+" for "+clazz.getName());
    }
    
    private final String name;
    private final List<Animation> images;
    private final int size;
    
    private static final Map<String, StructureType<?, ?, ?>> instances = new LinkedHashMap<>();
    
    public static Collection<StructureType<?, ?, ?>> types()
    {
        return Collections.unmodifiableCollection(instances.values());
    }
    
    
    public static <T extends StructureType> List<T> types(Class<T> clazz)
    {
        return instances.values()
                .stream()
                .filter(t -> clazz.isInstance(t))
                .map(t -> (T)t)
                .toList();
    }
    
    public static <T extends StructureType<?, ?, ?>> T getByName(Class<T> clazz, String name)
    {
        StructureType<?, ?, ?> t = getByName(name);
        if(!clazz.isInstance(t))
            throw new IllegalArgumentException(name+" is a "+t.getName()+", not a "+clazz.getName());
        return (T)t;
    }
    
    public static StructureType<?, ?, ?> getByName(String name)
    {
        StructureType<?, ?, ?> bt = instances.get(transformName(name));
        if(bt == null)
            throw new IllegalArgumentException("no building type "+name+" found only\n"+
                    instances.keySet().stream().collect(Collectors.joining("  \n")));
        return bt;
    }
    
    public abstract B createBuilding(int x, int y, E variant, D data);

    public static List<BuildingType> actualBuildingTypes()
    {
        return types(BuildingType.class);
    }
    
    public StructureType(String name, Animation animation, int size)
    {
        this(name, List.of(animation), size);
    }
    
    //x -> x
    //x/y -> x/y
    //x/x_y -> x/y
    public static String transformName(String name)
    {
        int p = name.indexOf('/');
        if(p != -1)
        {
            String prefix = name.substring(0, p);
            
            int p2 = name.indexOf('_', p);
            if(p2 != -1)
            {
                String n2 = name.substring(p+1, p2);
                if(prefix.equals(n2))
                    return name.substring(p+1);
            }
            else
                name = name.replace('/', '_');
        }
        return name;
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public StructureType(String name, List<Animation> images, int size)
    {
        name = transformName(name);
        
        if(instances.containsKey(name))
            throw new IllegalArgumentException("duplicate ID");
        
        int expected = StructureVariant.getVariantCount(getStructureVariantClass(getClass()));
        if(expected != images.size())
            throw new IllegalArgumentException("wrong number of images for variants: "
                    + "expected "+expected+" but got "+images.size());
        
        this.name = name;
        this.images = images;
        this.size = size;
        instances.put(name, this);
    }
    
    public String getName()
    {
        return name;
    }
    
    void checkVariant(E variant)
    {
        if(variant.index()< 0 || variant.index() >= images.size())
            throw new IllegalArgumentException("invalid variant "+variant);
    }
    
    public Set<E> getVariants()
    {
        return (Set)StructureVariant.getValues(getStructureVariantClass(getClass()));
    }

    public Animation getImage(E version)
    {
        return images.get(version.index());
    }

    public int getSize()
    {
        return size;
    }
    
    public BufferedImage getDefaultImage()
    {
        return images.get(getVariantForDefaultImage().index()).getDefault();
    }
    
    public abstract E getVariantForDefaultImage();

    public BufferedImage getBrightImage()
    {
        return getBrightImage(DEFAULT_VARIANT);
    }

    public BufferedImage getBrightImage(E variant)
    {
        return getBrightImage(variant.index());
    }

    public BufferedImage getBrightImage(int variant)
    {
        return ImageUtils.brighter(images.get(variant).getDefault());
    }
    
    public abstract int getBuildingCost(E variant);

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"["+name+",size="+size+"]";
    }
}
