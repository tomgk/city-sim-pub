package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
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
        assertEquals(new SelfConnectionCategory(Streets.street), new SelfConnectionCategory(Streets.street));
        assertEquals(new SelfConnectionCategory(Streets.street).hashCode(), new SelfConnectionCategory(Streets.street).hashCode());
    }
    
    @Test
    public void testEquals_DifferentType()
    {
        assertNotEquals(new SelfConnectionCategory(Streets.street), new SelfConnectionCategory(Streets.rail));
    }
    
    @Test
    public void testEquals_DifferentClass()
    {
        assertNotEquals(new SelfConnectionCategory(Streets.street), new ZoneCategory(Zones.business));
    }
}
