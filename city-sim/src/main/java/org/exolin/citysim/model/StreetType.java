package org.exolin.citysim.model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street, StreetType.StreetVariant>
{
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
                TIntersection.T_INTERSECTION_4
        );
        
        public static StreetVariant valueOf(String name)
        {
            return VALUES.stream()
                    .filter(v -> v.name().equals(name))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(name));
        }
        
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
    
    public enum ConnectVariant implements StreetVariant
    {
        CONNECT_X,
        CONNECT_Y
    }
    
    public enum XIntersection implements StreetVariant
    {
        X_INTERSECTION
    }
    
    public enum Curve implements StreetVariant
    {
        CURVE_1,
        CURVE_2,
        CURVE_3,
        CURVE_4,
    }
    
    public enum TIntersection implements StreetVariant
    {
        T_INTERSECTION_1,
        T_INTERSECTION_2,
        T_INTERSECTION_3,
        T_INTERSECTION_4;
    }
    
    public StreetType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
        
        if(images.size() != StreetVariant.VALUES.size())
            throw new IllegalArgumentException("incorrect image count");
    }

    @Override
    public Class<StreetVariant> getVariantClass()
    {
        return StreetVariant.class;
    }
    
    @Override
    public Street createBuilding(int x, int y, StreetType.StreetVariant variant)
    {
        return new Street(this, x, y, variant);
    }
}
