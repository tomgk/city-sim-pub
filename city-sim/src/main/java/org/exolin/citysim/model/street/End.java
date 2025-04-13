package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum End implements StreetVariant
{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    
    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return StreetVariant.rotate(this, rotation,
                        NORTH, EAST, SOUTH, WEST);
    }
}
