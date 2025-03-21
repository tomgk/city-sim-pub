package org.exolin.citysim;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class BuildingType
{
    private final String name;
    private final BufferedImage image;
    private final int size;

    public BuildingType(String name, BufferedImage image, int size)
    {
        this.name = name;
        this.image = image;
        this.size = size;
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
