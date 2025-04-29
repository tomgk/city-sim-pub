package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.connections.SelfConnections;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class SelfConnectionCategoryTest
{
    @Test
    public void testEquals()
    {
        assertEquals(new SelfConnectionCategory(SelfConnections.street), new SelfConnectionCategory(SelfConnections.street));
        assertEquals(new SelfConnectionCategory(SelfConnections.street).hashCode(), new SelfConnectionCategory(SelfConnections.street).hashCode());
    }
    
    @Test
    public void testEquals_DifferentType()
    {
        assertNotEquals(new SelfConnectionCategory(SelfConnections.street), new SelfConnectionCategory(SelfConnections.rail));
    }
    
    @Test
    public void testEquals_DifferentClass()
    {
        assertNotEquals(new SelfConnectionCategory(SelfConnections.street), new ZoneCategory(Zones.business));
    }
}
