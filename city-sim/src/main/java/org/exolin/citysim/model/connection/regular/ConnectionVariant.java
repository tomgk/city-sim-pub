package org.exolin.citysim.model.connection.regular;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.exolin.citysim.model.Rotation;
import org.exolin.citysim.model.StructureVariant;
import static org.exolin.citysim.model.connection.regular.ConnectionVariants.VALUES;

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

    public static Set<? extends StructureVariant> values()
    {
        return ConnectionVariants.SET;
    }
    
    public static int getVariantCount()
    {
        return ConnectionVariants.VALUES.size();
    }
}

class ConnectionVariants
{
    public static final List<ConnectionVariant> VALUES = List.of(
            StraightConnectionVariant.CONNECT_X,
            StraightConnectionVariant.CONNECT_Y,
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
    
    public static final Set<ConnectionVariant> SET = Collections.unmodifiableSet(new LinkedHashSet<>(VALUES));
}
