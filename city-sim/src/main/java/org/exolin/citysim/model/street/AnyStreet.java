package org.exolin.citysim.model.street;

import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 * @param <B>
 * @param <T>
 * @param <E>
 */
public abstract class AnyStreet<B extends AnyStreet<B, T, E>, T extends AnyStreetType<B, T, E>, E extends BuildingVariant> extends Building<B, T, E>
{
    public AnyStreet(T type, int x, int y, E variant)
    {
        super(type, x, y, variant);
    }
    
}
