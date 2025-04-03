package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    
    private final Set<String> usedNames = new LinkedHashSet<>();
    private final String name;
    private final List<Animation> images;
    private final int size;
    
    private static final List<BuildingType> instances = new ArrayList<>();
    
    public abstract Class<E> getVariantClass();
    
    public static List<BuildingType> types()
    {
        return instances;
    }
    
    public static BuildingType<?, ?> getByName(String name)
    {
        Objects.requireNonNull(name);
        return instances.stream()
                .filter(b -> b.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("no building type "+name));
    }
    
    public abstract B createBuilding(int x, int y, E variant);

    public static List<ActualBuildingType> actualBuildingTypes()
    {
        return instances.stream().filter(b -> b.isBuilding()).map(b -> (ActualBuildingType)b).toList();
    }
    
    public BuildingType(String name, Animation animation, int size)
    {
        this(name, List.of(animation), size);
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, List<Animation> images, int size)
    {
        if(!usedNames.add(name))
            throw new IllegalArgumentException("duplicate ID");
        
        this.name = name;
        this.images = images;
        this.size = size;
        instances.add(this);
    }
    
    public boolean isBuilding()
    {
        return this instanceof ActualBuildingType;
    }
    
    public String getName()
    {
        return name;
    }
    
    void checkVariant(E variant)
    {
        if(variant.ordinal() < 0 || variant.ordinal() >= images.size())
            throw new IllegalArgumentException("invalid variant "+variant);
    }

    public Animation getImage(E version)
    {
        return images.get(version.ordinal());
    }

    public int getSize()
    {
        return size;
    }
    
    public BufferedImage getDefaultImage()
    {
        return getImage(getVariantClass().getEnumConstants()[0]).getDefault();
    }

    public BufferedImage getBrightImage()
    {
        return getBrightImage(DEFAULT_VARIANT);
    }

    public BufferedImage getBrightImage(Enum<?> variant)
    {
        return getBrightImage(variant.ordinal());
    }

    public BufferedImage getBrightImage(int variant)
    {
        return Utils.brighter(images.get(variant).getDefault());
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"["+name+"]";
    }
}
