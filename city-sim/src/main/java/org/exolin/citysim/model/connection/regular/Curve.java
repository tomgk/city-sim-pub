package org.exolin.citysim.model.connection.regular;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum Curve implements ConnectionVariant
{
    CURVE_1,
    CURVE_2,
    CURVE_3,
    CURVE_4;

    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return ConnectionVariant.rotate(this, rotation,
                        CURVE_1, CURVE_2, CURVE_3, CURVE_4);
    }
}
