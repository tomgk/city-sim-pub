package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum TIntersection implements ConnectionVariant
{
    T_INTERSECTION_1,
    T_INTERSECTION_2,
    T_INTERSECTION_3,
    T_INTERSECTION_4;

    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return ConnectionVariant.rotate(this, rotation,
                        new ConnectionVariantType(T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4));
    }
}
