package org.exolin.citysim;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas
 */
public class BuildingType
{
    private final BufferedImage image;
    private final int size;

    public BuildingType(BufferedImage image, int size)
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

    public BufferedImage getBrightImage()
    {
        return Utils.brighter(image);
    }
}
