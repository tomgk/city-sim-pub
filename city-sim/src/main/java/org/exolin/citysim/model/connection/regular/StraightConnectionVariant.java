package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum StraightConnectionVariant implements ConnectionVariant
{
    CONNECT_X,
    CONNECT_Y;
    
    private static final ConnectionVariantType<StraightConnectionVariant> TYPE = 
            new ConnectionVariantType<>(CONNECT_X, CONNECT_Y, CONNECT_X, CONNECT_Y);

    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return TYPE.rotate(this, rotation);
    }
}
