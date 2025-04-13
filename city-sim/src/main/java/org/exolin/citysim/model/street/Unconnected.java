package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum Unconnected implements StreetVariant
{
    UNCONNECTED;
    
    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return UNCONNECTED;
    }
}
