package org.exolin.citysim.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureSizeTest
{
    @Test
    public void test_toInteger_1()
    {
        assertEquals(1, StructureSize._1.toIntegerx());
    }
    
    @Test
    public void test_toInteger_2()
    {
        assertEquals(2, StructureSize._2.toIntegerx());
    }
    
    @Test
    public void test_toInteger_3()
    {
        assertEquals(3, StructureSize._3.toIntegerx());
    }
    
    @Test
    public void test_toInteger_4()
    {
        assertEquals(4, StructureSize._4.toIntegerx());
    }
    
    @Test
    public void testCount()
    {
        assertEquals(4, StructureSize.values().length);
    }
    
    @Test
    public void testMax()
    {
        assertEquals(4, StructureSize.MAX);
    }
}
