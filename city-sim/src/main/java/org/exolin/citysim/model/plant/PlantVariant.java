package org.exolin.citysim.model.plant;

import java.util.List;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public enum PlantVariant implements StructureVariant
{
    DEFAULT(0, 0),
    LEFT(-1, 0),
    RIGHT(1, 0),

    TOP_LEFT(-1, -1),
    TOP_MIDDLE(0, -1),
    TOP_RIGHT(1, -1),

    BOTTOM_LEFT(-1, -1),
    BOTTOM_MIDDLE(0, -1),
    BOTTOM_RIGHT(1, -1);

    static final List<PlantVariant> VALUES = List.of(values());

    private final int xoffset;
    private final int yoffset;

    private PlantVariant(int xoffset, int yoffset)
    {
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }

    public int getXoffset()
    {
        return xoffset;
    }

    public int getYoffset()
    {
        return yoffset;
    }

    public static PlantVariant random()
    {
        return RandomUtils.random(VALUES);
    }
}
