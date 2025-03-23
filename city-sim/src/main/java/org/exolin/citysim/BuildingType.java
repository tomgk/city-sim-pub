package org.exolin.citysim;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.exolin.citysim.ui.Utils;

/**
 *
 * @author Thomas
 */
public abstract class BuildingType<B>
{
    private final int id;
    private final Set<Integer> usedIds = new LinkedHashSet<>();
    private final String name;
    private final BufferedImage image;
    private final int size;
    
    private static final List<BuildingType> instances = new ArrayList<>();
    
    public static List<BuildingType> types()
    {
        return instances;
    }
    
    public abstract B createBuilding(int x, int y);
    public B read(Reader reader)
    {
        if(true)
            throw new UnsupportedOperationException("TODO");
        
        return readImpl(reader);
    }
    
    protected abstract B readImpl(Reader reader);

    public static List<ActualBuildingType> actualBuildingTypes()
    {
        return instances.stream().filter(b -> b.isBuilding()).map(b -> (ActualBuildingType)b).toList();
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(int id, String name, BufferedImage image, int size)
    {
        if(!usedIds.add(id))
            throw new IllegalArgumentException("duplicate ID");
        
        this.id = id;
        this.name = name;
        this.image = image;
        this.size = size;
        instances.add(this);
    }

    public int getId()
    {
        return id;
    }
    
    public boolean isBuilding()
    {
        return this instanceof ActualBuildingType;
    }
    
    public String getName()
    {
        return name;
    }

    public Image getImage()
    {
        return image;
    }

    public int getSize()
    {
        return size;
    }

    public BufferedImage getBrightImage()
    {
        return Utils.brighter(image);
    }
}
