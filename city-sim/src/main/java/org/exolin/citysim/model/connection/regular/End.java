package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum End implements ConnectionVariant
{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    
    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        //return StreetVariant.rotate(this, rotation,
        //                NORTH, EAST, SOUTH, WEST);
        return ConnectionVariant.rotate(this, rotation,
                        new ConnectionVariantType(SOUTH, EAST, NORTH, WEST));
    }
}
