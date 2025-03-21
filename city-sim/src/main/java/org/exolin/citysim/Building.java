package org.exolin.citysim;

import java.awt.Image;

/**
 *
 * @author Thomas
 */
public class Building
{
    private final BuildingType type;
    private final int x;
    private final int y;

    public Building(BuildingType type, int x, int y)
    {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Image getImage()
    {
        return type.getImage();
    }
    
    public int getSize()
    {
        return type.getSize();
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
