package org.exolin.citysim.model;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.ab.BuildingType;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 * @param <B> building class
 * @param <E> building variant
 */
public abstract class StructureType<B, E extends StructureVariant>
{
    public static final int DEFAULT_VARIANT = 0;
    
    private final String name;
    private final List<Animation> images;
    private final int size;
    
    private static final Map<String, StructureType> instances = new LinkedHashMap<>();
    
    public static Collection<StructureType> types()
    {
        return Collections.unmodifiableCollection(instances.values());
    }
    
    public static StructureType<?, ?> getByName(String name)
    {
        StructureType<?, ?> bt = instances.get(transformName(name));
        if(bt == null)
            throw new IllegalArgumentException("no building type "+name);
        return bt;
    }
    
    public abstract B createBuilding(int x, int y, E variant);

    public static List<BuildingType> actualBuildingTypes()
    {
        return instances.values().stream().filter(b -> b instanceof BuildingType).map(b -> (BuildingType)b).toList();
    }
    
    public StructureType(String name, Animation animation, int size)
    {
        this(name, List.of(animation), size);
    }
    
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
        return images.get(getDefaultVariant().index()).getDefault();
    }
    
    public abstract E getDefaultVariant();

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
        return Utils.brighter(images.get(variant).getDefault());
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"["+name+",size="+size+"]";
    }
}
