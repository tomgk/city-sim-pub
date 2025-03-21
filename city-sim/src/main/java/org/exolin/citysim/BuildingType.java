package org.exolin.citysim;

import java.awt.Image;

/**
 *
 * @author Thomas
 */
public class BuildingType
{
    private final Image image;
    private final int size;

    public BuildingType(Image image, int size)
    {
        this.image = image;
        this.size = size;
    }

    public Image getImage()
    {
        return image;
    }

    public int getSize()
    {
        return size;
    }
}
