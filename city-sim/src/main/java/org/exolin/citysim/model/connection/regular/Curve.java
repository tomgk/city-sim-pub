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

    private static final ConnectionVariantType<Curve> TYPE =
            new ConnectionVariantType<>(CURVE_1, CURVE_2, CURVE_3, CURVE_4);
    
    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        return TYPE.rotate(this, rotation);
    }
}
