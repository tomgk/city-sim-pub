package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum Curve implements StreetVariant
{
    CURVE_1,
    CURVE_2,
    CURVE_3,
    CURVE_4;

    @Override
    public StreetVariant rotate(Rotation rotation)
    {
        return StreetVariant.rotate(this, rotation,
                        CURVE_1, CURVE_2, CURVE_3, CURVE_4);
    }
}
