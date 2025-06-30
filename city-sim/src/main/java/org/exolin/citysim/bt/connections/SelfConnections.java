package org.exolin.citysim.bt.connections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.StructureSize;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.connection.regular.StraightConnectionVariant;
import org.exolin.citysim.model.connection.regular.XIntersection;

/**
 *
 * @author Thomas
 */
public class SelfConnections
{
    private static final List<SelfConnectionType> VALUES = new ArrayList<>();
    
    public static final SelfConnectionType street = createAreaType("street", List.of(
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
            createUnanimated("street/street_t_4"),
            
            createUnanimated("street/street_1"),//end 1
            createUnanimated("street/street_2"),//end 2
            createUnanimated("street/street_1"),//end 3
            createUnanimated("street/street_2"),//end 4
            createUnanimated("street/street_1")//unconnected
            ), _1, 10);
    
    public static final SelfConnectionType rail = createTransportType("rail", List.of(
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
            createUnanimated("rail/rail_t_4")), _1, 25);
    
    public static final SelfConnectionType circuit = createTransportType("circuit", List.of(
            createUnanimated("circuit/circuit_1"),
            createUnanimated("circuit/circuit_2"),
            
            createUnanimated("circuit/circuit_x_intersection"),
            
            createUnanimated("circuit/circuit_curve_1"),
            createUnanimated("circuit/circuit_curve_2"),
            createUnanimated("circuit/circuit_curve_3"),
            createUnanimated("circuit/circuit_curve_4"),
            
            createUnanimated("circuit/circuit_t_1"),
            createUnanimated("circuit/circuit_t_2"),
            createUnanimated("circuit/circuit_t_3"),
            createUnanimated("circuit/circuit_t_4")), _1, 25);
    
    public static final SelfConnectionType water = createAreaType("water", List.of(
            createAnimation("water/water_1", 4),
            createAnimation("water/water_2", 4),
            
            createAnimation("water/water_x_intersection", 4),
            
            createAnimation("water/water_curve_1", 4),
            createAnimation("water/water_curve_2", 4),
            createAnimation("water/water_curve_3", 4),
            createAnimation("water/water_curve_4", 4),
            
            createAnimation("water/water_t_1", 4),
            createAnimation("water/water_t_2", 4),
            createAnimation("water/water_t_3", 4),
            createAnimation("water/water_t_4", 4),
            
            createAnimation("water/water_end_1", 4),
            createAnimation("water/water_end_4", 4),
            createAnimation("water/water_end_3", 4),
            createAnimation("water/water_end_2", 4),
            
            createAnimation("water/water_unconnected", 4)
    ), _1, 100);

    public static List<SelfConnectionType> values()
    {
        return Collections.unmodifiableList(VALUES);
    }
    
    private static SelfConnectionType createTransportType(String name, List<Animation> variants, StructureSize size, int cost)
    {
        List<Animation> add = new ArrayList<>(variants);
        
        //End
        add.add(variants.get(StraightConnectionVariant.CONNECT_X.index()));
        add.add(variants.get(StraightConnectionVariant.CONNECT_Y.index()));
        add.add(variants.get(StraightConnectionVariant.CONNECT_X.index()));
        add.add(variants.get(StraightConnectionVariant.CONNECT_Y.index()));
        //Unconnected
        add.add(variants.get(StraightConnectionVariant.CONNECT_X.index()));
        
        SelfConnectionType t = new SelfConnectionType(name, add, size, cost, StraightConnectionVariant.CONNECT_X);
        VALUES.add(t);
        return t;
    }
    
    private static SelfConnectionType createAreaType(String name, List<Animation> variants, StructureSize size, int cost)
    {
        SelfConnectionType t = new SelfConnectionType(name, variants, size, cost, XIntersection.X_INTERSECTION);
        VALUES.add(t);
        return t;
    }

    static void init()
    {
        StructureTypes.init();
    }
}
