package org.exolin.citysim.model.connection.regular;

import java.util.List;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.StructureVariant;
import static org.exolin.citysim.model.connection.regular.StreetVariants.VALUES;

/**
 *
 * @author Thomas
 */
public interface ConnectionVariant extends StructureVariant
{
    @Override
    public default int index()
    {
        int index = VALUES.indexOf(this);

        if(index == -1)
            throw new IllegalArgumentException();

        return index;
    }

    public static ConnectionVariant valueOf(String name)
    {
        return VALUES.stream()
                .filter(v -> v.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(name));
    }
    
    public ConnectionVariant rotate(Rotation rotation);
}

class StreetVariants
{
    public static final List<ConnectionVariant> VALUES = List.of(
            ConnectVariant.CONNECT_X,
            ConnectVariant.CONNECT_Y,
            XIntersection.X_INTERSECTION,
            Curve.CURVE_1,
            Curve.CURVE_2,
            Curve.CURVE_3,
            Curve.CURVE_4,
            TIntersection.T_INTERSECTION_1,
            TIntersection.T_INTERSECTION_2,
            TIntersection.T_INTERSECTION_3,
            TIntersection.T_INTERSECTION_4,
            End.NORTH,
            End.WEST,
            End.SOUTH,
            End.EAST,
            Unconnected.UNCONNECTED
    );
}
