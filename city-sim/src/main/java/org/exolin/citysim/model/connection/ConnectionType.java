package org.exolin.citysim.model.connection;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureData;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;

/**
 *
 * @author Thomas
 * @param <B>
 * @param <T>
 * @param <E>
 * @param <D>
 */
public abstract class ConnectionType<B extends Connection<B, T, E, D>, T extends ConnectionType<B, T, E, D>, E extends StructureVariant, D extends StructureData<D>> extends StructureType<B, E, D>
{
    public ConnectionType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }

    public ConnectionType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
    }
    public abstract SelfConnectionType getXType();
    public abstract SelfConnectionType getYType();
}
