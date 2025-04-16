package org.exolin.citysim.model.street;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.street.regular.StreetType;

/**
 *
 * @author Thomas
 */
public abstract class AnyStreetType<B extends AnyStreet<B, T, E>, T extends AnyStreetType<B, T, E>, E extends BuildingVariant> extends BuildingType<B, E>
{
    public AnyStreetType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }

    public AnyStreetType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
    }
    public abstract StreetType getXType();
    public abstract StreetType getYType();
}
