package org.exolin.citysim.bt.connections;

import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.connection.regular.ConnectVariant;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.connection.regular.XIntersection;

/**
 *
 * @author Thomas
 */
public class SelfConnections
{
    public static final SelfConnectionType street = createTransportType("street", List.of(
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
            createUnanimated("rail/rail_t_4")), 1, 25);
    
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
            createUnanimated("circuit/circuit_t_4")), 1, 25);
    
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
    ), 1, 100);
    
    private static SelfConnectionType createTransportType(String name, List<Animation> variants, int size, int cost)
    {
        List<Animation> add = new ArrayList<>(variants);
        
        //End
        add.add(variants.get(ConnectVariant.CONNECT_X.index()));
        add.add(variants.get(ConnectVariant.CONNECT_Y.index()));
        add.add(variants.get(ConnectVariant.CONNECT_X.index()));
        add.add(variants.get(ConnectVariant.CONNECT_Y.index()));
        //Unconnected
        add.add(variants.get(ConnectVariant.CONNECT_X.index()));
        
        return new SelfConnectionType(name, add, size, cost, ConnectVariant.CONNECT_X);
    }
    
    private static SelfConnectionType createAreaType(String name, List<Animation> variants, int size, int cost)
    {
        return new SelfConnectionType(name, variants, size, cost, XIntersection.X_INTERSECTION);
    }

    static void init()
    {
        StructureTypes.init();
    }
}
