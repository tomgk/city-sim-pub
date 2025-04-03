package org.exolin.citysim;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class StreetType extends BuildingType<Street, StreetType.Variant>
{
    public interface StreetVariant extends BuildingVariant
    {
        
    }
    
    public enum Variant implements StreetVariant
    {
        CONNECT_X,
        CONNECT_Y,
        X_INTERSECTION,
        CURVE_1,
        CURVE_2,
        CURVE_3,
        CURVE_4,
        T_INTERSECTION_1,
        T_INTERSECTION_2,
        T_INTERSECTION_3,
        T_INTERSECTION_4;
        
        public static Variant rotate(Variant base, Rotation rotation, Variant v1, Variant v2, Variant v3, Variant v4)
        {
            Variant[] v = new Variant[]{v1, v2, v3, v4};
            
            int pos = Arrays.asList(v).indexOf(base);
            if(pos == -1)
                throw new IllegalArgumentException();
            
            pos += rotation.getAmount();
            if(pos >= v.length)
                pos -= v.length;
            
            return v[pos];
        }
    }
    
    public StreetType(String name, List<Animation> images, int size)
    {
        super(name, images, size);
        
        if(images.size() != Variant.values().length)
            throw new IllegalArgumentException("incorrect image count");
    }

    @Override
    public Class<Variant> getVariantClass()
    {
        return Variant.class;
    }
    
    @Override
    public Street createBuilding(int x, int y, StreetType.Variant variant)
    {
        return new Street(this, x, y, variant);
    }
}
