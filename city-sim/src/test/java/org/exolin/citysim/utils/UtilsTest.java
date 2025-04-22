package org.exolin.citysim.utils;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class UtilsTest
{
    @Test
    public void testGetProbabilityForTicks_0()
    {
        assertEquals(0, Utils.getProbabilityForTicks(0.1, 0));
    }
    
    @Test
    public void testGetProbabilityForTicks_1()
    {
        assertEquals(0.1, Utils.getProbabilityForTicks(0.1, 1), 0.0001);
    }
    
    @Test
    public void testGetProbabilityForTicks_2()
    {
        assertEquals(0.19, Utils.getProbabilityForTicks(0.1, 2), 0.0001);
    }
    
    @Test
    public void testGetProbabilityForTicks_3()
    {
        assertEquals(0.271, Utils.getProbabilityForTicks(0.1, 3), 0.0001);
    }
    
    @Test
    public void testGetProbabilityForTicks_10()
    {
        assertEquals(0.6513215599, Utils.getProbabilityForTicks(0.1, 10), 0.0001);
    }
    
    @Test
    public void testGetProbabilityForTicks_100()
    {
        assertEquals(0.9999734386, Utils.getProbabilityForTicks(0.1, 100), 0.0001);
    }
    
    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testGetProbabilityForTicks_Negative()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Utils.getProbabilityForTicks(0.1, -1));
    }
}
