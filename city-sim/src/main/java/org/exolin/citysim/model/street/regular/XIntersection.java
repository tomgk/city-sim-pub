package org.exolin.citysim.model.street.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum XIntersection implements StreetVariant
{
    X_INTERSECTION;

    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return this;
    }
}
