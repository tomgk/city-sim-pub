package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StreetCategoryTest
{
    @Test
    public void testEquals()
    {
        assertEquals(new StreetCategory(Streets.street), new StreetCategory(Streets.street));
        assertEquals(new StreetCategory(Streets.street).hashCode(), new StreetCategory(Streets.street).hashCode());
    }
    
    @Test
    public void testEquals_DifferentType()
    {
        assertNotEquals(new StreetCategory(Streets.street), new StreetCategory(Streets.rail));
    }
    
    @Test
    public void testEquals_DifferentClass()
    {
        assertNotEquals(new StreetCategory(Streets.street), new ZoneCategory(Zones.zone_business));
    }
}
