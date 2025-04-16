package org.exolin.citysim.model.connection;

import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 * @param <B>
 * @param <T>
 * @param <E>
 */
public abstract class Connection<B extends Connection<B, T, E>, T extends ConnectionType<B, T, E>, E extends BuildingVariant> extends Building<B, T, E>
{
    public Connection(T type, int x, int y, E variant)
    {
        super(type, x, y, variant);
    }
    
}
