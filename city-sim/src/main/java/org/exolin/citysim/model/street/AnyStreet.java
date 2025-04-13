package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 */
public abstract class AnyStreet<B, T extends BuildingType<B, E>, E extends BuildingVariant> extends Building<B, T, E>
{
    public AnyStreet(T type, int x, int y, E variant)
    {
        super(type, x, y, variant);
    }
}
