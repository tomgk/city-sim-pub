package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.street.StreetType;

/**
 *
 * @author Thomas
 */
public class Streets
{
    public static final StreetType street = createStreetType("street", List.of(
            createUnanimated("street/street_1"),
            createUnanimated("street/street_2"),
            
            createAnimation("street/street_x_intersection", 5),
            
            createUnanimated("street/street_curve_1"),
            createUnanimated("street/street_curve_2"),
            createUnanimated("street/street_curve_3"),
            createUnanimated("street/street_curve_4"),
            
            createUnanimated("street/street_t_1"),
            createUnanimated("street/street_t_2"),
            createUnanimated("street/street_t_3"),
            createUnanimated("street/street_t_4")), 1, 10);
    
    public static final StreetType rail = createStreetType("rail", List.of(
            createUnanimated("rail/rail_1"),
            createUnanimated("rail/rail_2"),
            
            createUnanimated("rail/rail_x_intersection"),
            
            createUnanimated("rail/rail_curve_1"),
            createUnanimated("rail/rail_curve_2"),
            createUnanimated("rail/rail_curve_3"),
            createUnanimated("rail/rail_curve_4"),
            
            createUnanimated("rail/rail_t_1"),
            createUnanimated("rail/rail_t_2"),
            createUnanimated("rail/rail_t_3"),
            createUnanimated("rail/rail_t_4")), 1, 25);
    
    private static StreetType createStreetType(String name, List<Animation> variants, int size, int cost)
    {
        return new StreetType(name, variants, size, cost);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
