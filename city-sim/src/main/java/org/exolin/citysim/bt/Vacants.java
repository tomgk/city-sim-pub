package org.exolin.citysim.bt;

import java.util.List;
import static org.exolin.citysim.model.StructureSize._1;
import static org.exolin.citysim.model.StructureSize._2;
import static org.exolin.citysim.model.StructureSize._3;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class Vacants
{
    static
    {
        StructureTypes.init();
    }
    
    public static final VacantType tore_down1 = new VacantType("destruction/tore_down", _1, true);
    public static final VacantType tore_down2 = new VacantType("destruction/tore_down_1", _1, true);
    public static final VacantType tore_down3 = new VacantType("destruction/tore_down_2", _1, true);
    public static final VacantType tore_down4 = new VacantType("destruction/tore_down_3", _1, true);
    
    private static final List<VacantType> TORE_DOWN = List.of(tore_down1, tore_down2, tore_down3, tore_down4);
    
    public static VacantType tore_down()
    {
        return RandomUtils.random(TORE_DOWN);
    }
    
    public static final VacantType abandoned_small_1 = new VacantType("destruction/abandoned_small_1", _1, false);
    public static final VacantType abandoned_small_2 = new VacantType("destruction/abandoned_small_2", _1, false);
    
    public static final VacantType abandoned_middle_1 = new VacantType("destruction/abandoned_middle_1", _2, false);
    public static final VacantType abandoned_middle_2 = new VacantType("destruction/abandoned_middle_2", _2, false);
    public static final VacantType abandoned_middle_3 = new VacantType("destruction/abandoned_middle_3", _2, false);
    public static final VacantType abandoned_middle_4 = new VacantType("destruction/abandoned_middle_4", _2, false);
    
    public static final VacantType abandoned_big_1 = new VacantType("destruction/abandoned_big_1", _3, false);
    public static final VacantType abandoned_big_2 = new VacantType("destruction/abandoned_big_2", _3, false);
}
