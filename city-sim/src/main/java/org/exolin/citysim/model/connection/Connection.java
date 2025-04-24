package org.exolin.citysim.model.connection;

import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureData;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 * @param <B>
 * @param <T>
 * @param <E>
 * @param <D>
 */
public abstract class Connection<B extends Connection<B, T, E, D>, T extends ConnectionType<B, T, E, D>, E extends StructureVariant, D extends StructureData> extends Structure<B, T, E, D>
{
    public Connection(T type, int x, int y, E variant)
    {
        super(type, x, y, variant);
    }
    
}
