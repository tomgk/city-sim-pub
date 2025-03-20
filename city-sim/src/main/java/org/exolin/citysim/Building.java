package org.exolin.citysim;

import java.awt.Image;

/**
 *
 * @author Thomas
 */
public class Building
{
    private final Image image;
    private final int x;
    private final int y;

    public Building(Image image, int x, int y)
    {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage()
    {
        return image;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getLevel()
    {
        return x + y;
    }
}
