package org.exolin.citysim.bt.buildings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ElectricityTest
{
    //check for test completeness
    @Test
    public void testTest()
    {
        assertEquals(4, Electricity.values().length);
    }
    
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
    
    @Test
    public void testGreater_Insulator()
    {
        assertEquals(Electricity.INSULATOR, Electricity.greater(Electricity.INSULATOR, Electricity.INSULATOR));
        assertEquals(Electricity.NEEDS, Electricity.greater(Electricity.INSULATOR, Electricity.NEEDS));
        assertEquals(Electricity.CONDUCTS, Electricity.greater(Electricity.INSULATOR, Electricity.CONDUCTS));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.INSULATOR, Electricity.TRANSFER));
    }
    
    @Test
    public void testGreater_Needs()
    {
        assertEquals(Electricity.NEEDS, Electricity.greater(Electricity.NEEDS, Electricity.INSULATOR));
        assertEquals(Electricity.NEEDS, Electricity.greater(Electricity.NEEDS, Electricity.NEEDS));
        assertEquals(Electricity.CONDUCTS, Electricity.greater(Electricity.NEEDS, Electricity.CONDUCTS));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.NEEDS, Electricity.TRANSFER));
    }
    
    @Test
    public void testGreater_Conducts()
    {
        assertEquals(Electricity.CONDUCTS, Electricity.greater(Electricity.CONDUCTS, Electricity.INSULATOR));
        assertEquals(Electricity.CONDUCTS, Electricity.greater(Electricity.CONDUCTS, Electricity.NEEDS));
        assertEquals(Electricity.CONDUCTS, Electricity.greater(Electricity.CONDUCTS, Electricity.CONDUCTS));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.CONDUCTS, Electricity.TRANSFER));
    }
    
    @Test
    public void testGreater_Transfer()
    {
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.TRANSFER, Electricity.INSULATOR));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.TRANSFER, Electricity.NEEDS));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.TRANSFER, Electricity.CONDUCTS));
        assertEquals(Electricity.TRANSFER, Electricity.greater(Electricity.TRANSFER, Electricity.TRANSFER));
    }
}
