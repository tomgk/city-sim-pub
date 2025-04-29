package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.building.vacant.VacantsPack;
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
    
    public static final VacantsPack tore_down1 = new VacantsPack("destruction/tore_down", 1, true);
    public static final VacantsPack tore_down2 = new VacantsPack("destruction/tore_down_1", 1, true);
    public static final VacantsPack tore_down3 = new VacantsPack("destruction/tore_down_2", 1, true);
    public static final VacantsPack tore_down4 = new VacantsPack("destruction/tore_down_3", 1, true);
    
    private static final List<VacantsPack> TORE_DOWN = List.of(tore_down1, tore_down2, tore_down3, tore_down4);
    
    public static VacantsPack tore_down()
    {
        return RandomUtils.random(TORE_DOWN);
    }
    
    public static final VacantsPack abandoned_small_1 = new VacantsPack("destruction/abandoned_small_1", 1, false);
    public static final VacantsPack abandoned_small_2 = new VacantsPack("destruction/abandoned_small_2", 1, false);
    
    public static final VacantsPack abandoned_middle_1 = new VacantsPack("destruction/abandoned_middle_1", 2, false);
    public static final VacantsPack abandoned_middle_2 = new VacantsPack("destruction/abandoned_middle_2", 2, false);
    public static final VacantsPack abandoned_middle_3 = new VacantsPack("destruction/abandoned_middle_3", 2, false);
    public static final VacantsPack abandoned_middle_4 = new VacantsPack("destruction/abandoned_middle_4", 2, false);
    
    public static final VacantsPack abandoned_big_1 = new VacantsPack("destruction/abandoned_big_1", 3, false);
    public static final VacantsPack abandoned_big_2 = new VacantsPack("destruction/abandoned_big_2", 3, false);
}
