package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum TIntersection implements StreetVariant
{
    T_INTERSECTION_1,
    T_INTERSECTION_2,
    T_INTERSECTION_3,
    T_INTERSECTION_4;

    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return StreetVariant.rotate(this, rotation,
                        T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4);
    }
}
