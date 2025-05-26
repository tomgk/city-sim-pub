package org.exolin.citysim.bt.buildings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ElectricityTest
{
    @Test
    public void testNeeds()
    {
        assertTrue(Electricity.NEEDS.conducts());
        assertTrue(Electricity.NEEDS.transfers());
        assertTrue(Electricity.NEEDS.needs());
    }
    
    @Test
    public void testTransfer()
    {
        assertTrue(Electricity.TRANSFER.conducts());
        assertTrue(Electricity.TRANSFER.transfers());
        assertFalse(Electricity.TRANSFER.needs());
    }
    
    @Test
    public void testConducts()
    {
        assertTrue(Electricity.CONDUCTS.conducts());
        assertFalse(Electricity.CONDUCTS.transfers());
        assertFalse(Electricity.CONDUCTS.needs());
    }
    
    @Test
    public void testInsulator()
    {
        assertFalse(Electricity.INSULATOR.conducts());
        assertFalse(Electricity.INSULATOR.transfers());
        assertFalse(Electricity.INSULATOR.needs());
    }
}
