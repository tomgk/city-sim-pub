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

    private static final ConnectionVariantType<TIntersection> TYPE =
            new ConnectionVariantType<>(T_INTERSECTION_1, T_INTERSECTION_2, T_INTERSECTION_3, T_INTERSECTION_4);
    
    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return TYPE.rotate(this, rotation);
    }
}
