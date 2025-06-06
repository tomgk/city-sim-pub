package org.exolin.citysim.model.connection;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureSize;
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
public abstract class ConnectionType<B extends Connection<B, T, E, D>, T extends ConnectionType<B, T, E, D>, E extends StructureVariant, D extends StructureParameters<D>> extends StructureType<B, E, D>
{
    public ConnectionType(String name, Animation animation, StructureSize size)
    {
        super(name, animation, size);
    }

    public ConnectionType(String name, List<Animation> images, StructureSize size)
    {
        super(name, images, size);
    }
    
    public enum Direction
    {
        X{
            @Override
            public SelfConnectionType get(ConnectionType<?, ?, ?, ?> t)
            {
                return t.getXType();
            }
        },
        Y{
            @Override
            public SelfConnectionType get(ConnectionType<?, ?, ?, ?> t)
            {
                return t.getYType();
            }
        };
        
        public abstract SelfConnectionType get(ConnectionType<?, ?, ?, ?> t);
    }
    
    public abstract SelfConnectionType getXType();
    public abstract SelfConnectionType getYType();
    
    public final SelfConnectionType getTypeInDirection(Direction d)
    {
        return d.get(this);
    }
}
