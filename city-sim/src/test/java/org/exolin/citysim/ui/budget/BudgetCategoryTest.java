package org.exolin.citysim.ui.budget;

import org.exolin.citysim.bt.Streets;
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
}
