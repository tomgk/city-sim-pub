package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.ResidentialBuildings;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import org.exolin.citysim.bt.buildings.IndustrialBuildings;
import org.exolin.citysim.bt.connections.SelfConnections;
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
        assertEquals(new SelfConnectionCategory(SelfConnections.street), BudgetCategory.getFor(SelfConnections.street));
    }
    
    @Test
    public void testGetFor_Rail()
    {
        assertEquals(new SelfConnectionCategory(SelfConnections.rail), BudgetCategory.getFor(SelfConnections.rail));
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
        assertEquals(new ZoneCategory(Zones.industrial), BudgetCategory.getFor(IndustrialBuildings.small_1));
    }
}
