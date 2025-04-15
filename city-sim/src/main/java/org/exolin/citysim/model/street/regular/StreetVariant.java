package org.exolin.citysim.model.street.regular;

import java.util.Arrays;
import java.util.List;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.Rotation;
import static org.exolin.citysim.model.street.regular.StreetVariants.VALUES;

/**
 *
 * @author Thomas
 */
public interface StreetVariant extends BuildingVariant
{
    @Override
    public default int index()
    {
        int index = VALUES.indexOf(this);

        if(index == -1)
            throw new IllegalArgumentException();

        return index;
    }

    public static StreetVariant valueOf(String name)
    {
        return VALUES.stream()
                .filter(v -> v.name().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(name));
    }
    
    public StreetVariant rotate(Rotation rotation);

    public static StreetVariant rotate(StreetVariant base, Rotation rotation, StreetVariant v1, StreetVariant v2, StreetVariant v3, StreetVariant v4)
    {
        StreetVariant[] v = new StreetVariant[]{v1, v2, v3, v4};

        int pos = Arrays.asList(v).indexOf(base);
        if(pos == -1)
            throw new IllegalArgumentException();

        pos += rotation.getAmount();
        if(pos >= v.length)
            pos -= v.length;

        return v[pos];
    }
}

class StreetVariants
{
    public static final List<StreetVariant> VALUES = List.of(
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
