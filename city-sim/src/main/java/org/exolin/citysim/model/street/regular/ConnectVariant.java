package org.exolin.citysim.model.street.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum ConnectVariant implements StreetVariant
{
    CONNECT_X,
    CONNECT_Y;

    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return StreetVariant.rotate(this, rotation,
                        CONNECT_X, CONNECT_Y, CONNECT_X, CONNECT_Y);
    }
}
