package org.exolin.citysim.bt;

import org.exolin.citysim.model.building.vacant.VacantsPack;

/**
 *
 * @author Thomas
 */
public class Vacants
{
    static
    {
        BuildingTypes.init();
    }
    
    public static final VacantsPack tore_down = new VacantsPack("destruction/tore_down", 1);
    public static final VacantsPack abandoned_small_1 = new VacantsPack("destruction/abandoned_small_1", 1);
    public static final VacantsPack abandoned_small_2 = new VacantsPack("destruction/abandoned_small_2", 1);
    
    public static final VacantsPack abandoned_middle_1 = new VacantsPack("destruction/abandoned_middle_1", 2);
    public static final VacantsPack abandoned_middle_2 = new VacantsPack("destruction/abandoned_middle_2", 2);
    public static final VacantsPack abandoned_middle_3 = new VacantsPack("destruction/abandoned_middle_3", 2);
    public static final VacantsPack abandoned_middle_4 = new VacantsPack("destruction/abandoned_middle_4", 2);
    
    public static final VacantsPack abandoned_big_1 = new VacantsPack("destruction/abandoned_big_1", 3);
    public static final VacantsPack abandoned_big_2 = new VacantsPack("destruction/abandoned_big_2", 3);
}
