package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StreetType;

/**
 *
 * @author Thomas
 */
public class Streets
{
    public static final StreetType street = createStreetType("street", List.of(
            "street_1", "street_2",
            "street_x_intersection",
            "street_curve_1", "street_curve_2", "street_curve_3", "street_curve_4",
            "street_t_1", "street_t_2", "street_t_3", "street_t_4"), 1);
    
    private static StreetType createStreetType(String name, List<String> variants, int size)
    {
        return new StreetType(name, Animation.noAnimation(variants), size);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
