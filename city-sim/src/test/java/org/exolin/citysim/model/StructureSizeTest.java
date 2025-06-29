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
        assertEquals(1, StructureSize._1.toInteger());
    }
    
    @Test
    public void test_toInteger_2()
    {
        assertEquals(2, StructureSize._2.toInteger());
    }
    
    @Test
    public void test_toInteger_3()
    {
        assertEquals(3, StructureSize._3.toInteger());
    }
    
    @Test
    public void test_toInteger_4()
    {
        assertEquals(4, StructureSize._4.toInteger());
    }
    
    @Test
    public void test_toString_1()
    {
        assertEquals("StructureSize[1x1]", StructureSize._1.toString());
    }
    
    @Test
    public void test_toString_2()
    {
        assertEquals("StructureSize[2x2]", StructureSize._2.toString());
    }
    
    @Test
    public void test_toString_3()
    {
        assertEquals("StructureSize[3x3]", StructureSize._3.toString());
    }
    
    @Test
    public void test_toString_4()
    {
        assertEquals("StructureSize[4x4]", StructureSize._4.toString());
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
