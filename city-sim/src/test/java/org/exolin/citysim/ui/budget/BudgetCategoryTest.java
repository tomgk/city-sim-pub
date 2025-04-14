package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.bt.IndustrialBuildings;
import org.exolin.citysim.bt.ResidentialBuildings;
import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class BudgetCategoryTest
{
    @Test
    public void testGetFor_Street()
    {
        assertEquals(new StreetCategory(Streets.street), BudgetCategory.getFor(Streets.street));
    }
    
    @Test
    public void testGetFor_Rail()
    {
        assertEquals(new StreetCategory(Streets.rail), BudgetCategory.getFor(Streets.rail));
    }
    
    @Test
    public void testGetFor_Zone()
    {
        assertEquals(null, BudgetCategory.getFor(Zones.business));
    }
    
    @Test
    public void testGetFor_Tree()
    {
        assertEquals(null, BudgetCategory.getFor(Trees.TREES.getFirst()));
    }
    
    @Test
    public void testGetFor_ResidentialBuildings()
    {
        assertEquals(new ZoneCategory(Zones.residential), BudgetCategory.getFor(ResidentialBuildings.house_1));
    }
    
    @Test
    public void testGetFor_BusinessBuilding()
    {
        assertEquals(new ZoneCategory(Zones.business), BudgetCategory.getFor(BusinessBuildings.small_1));
    }
    
    @Test
    public void testGetFor_IndustrialBuildings()
    {
        assertEquals(new ZoneCategory(Zones.industrial), BudgetCategory.getFor(IndustrialBuildings.industrial_small_1));
    }
}
