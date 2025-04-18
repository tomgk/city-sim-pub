package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum ConnectVariant implements ConnectionVariant
{
    CONNECT_X,
    CONNECT_Y;

    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return ConnectionVariant.rotate(this, rotation, 
                new ConnectionVariantType(CONNECT_X, CONNECT_Y, CONNECT_X, CONNECT_Y));
    }
}
