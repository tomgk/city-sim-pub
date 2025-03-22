package org.exolin.citysim;

import org.exolin.citysim.ui.Utils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class BuildingType
{
    private final String name;
    private final BufferedImage image;
    private final int size;
    
    private static final List<BuildingType> instances = new ArrayList<>();
    
    public static List<BuildingType> types()
    {
        return instances;
    }

    public static List<ActualBuildingType> actualBuildingTypes()
    {
        return instances.stream().filter(b -> b.isBuilding()).map(b -> (ActualBuildingType)b).toList();
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public BuildingType(String name, BufferedImage image, int size)
    {
        this.name = name;
        this.image = image;
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
