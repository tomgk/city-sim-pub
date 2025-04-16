package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum XIntersection implements ConnectionVariant
{
    X_INTERSECTION;

    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return this;
    }
}
