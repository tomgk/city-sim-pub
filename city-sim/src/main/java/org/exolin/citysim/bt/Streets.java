package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.street.StreetType;

/**
 *
 * @author Thomas
 */
public class Streets
{
    public static final StreetType street = createStreetType("street", List.of(
            Animation.createUnanimated("street_1"), Animation.createUnanimated("street_2"),
            Animation.create("street_x_intersection", 5),
            
            Animation.createUnanimated("street_curve_1"),
            Animation.createUnanimated("street_curve_2"),
            Animation.createUnanimated("street_curve_3"),
            Animation.createUnanimated("street_curve_4"),
            
            Animation.createUnanimated("street_t_1"),
            Animation.createUnanimated("street_t_2"),
            Animation.createUnanimated("street_t_3"),
            Animation.createUnanimated("street_t_4")), 1);
    
    private static StreetType createStreetType(String name, List<Animation> variants, int size)
    {
        return new StreetType(name, variants, size);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
