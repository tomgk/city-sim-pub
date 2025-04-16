package org.exolin.citysim.model;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.ab.ActualBuildingType;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 * @param <B> building class
 * @param <E> building variant
 */
public abstract class BuildingType<B, E extends BuildingVariant>
{
    public static final int DEFAULT_VARIANT = 0;
    
    private final String name;
    private final List<Animation> images;
    private final int size;
    
    private static final Map<String, BuildingType> instances = new LinkedHashMap<>();
    
    public static Collection<BuildingType> types()
    {
        return Collections.unmodifiableCollection(instances.values());
    }
    
    public static BuildingType<?, ?> getByName(String name)
    {
        BuildingType<?, ?> bt = instances.get(name);
        if(bt == null)
            throw new IllegalArgumentException("no building type "+name);
        return bt;
    }
    
    public abstract B createBuilding(int x, int y, E variant);

    public static List<ActualBuildingType> actualBuildingTypes()
    {
        return instances.values().stream().filter(b -> b instanceof ActualBuildingType).map(b -> (ActualBuildingType)b).toList();
    }
    
    public BuildingType(String name, Animation animation, int size)
    {
        this(name, List.of(animation), size);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, List<Animation> images, int size)
    {
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
