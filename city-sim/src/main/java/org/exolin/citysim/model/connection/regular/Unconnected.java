package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum Unconnected implements ConnectionVariant
{
    UNCONNECTED;
    
    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return UNCONNECTED;
    }
}
