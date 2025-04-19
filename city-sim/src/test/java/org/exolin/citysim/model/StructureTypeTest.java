package org.exolin.citysim.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureTypeTest
{
    @Test
    public void testTransformName_Simple()
    {
        assertEquals("water_1", StructureType.transformName("water_1"));
    }
    
    @Test
    public void testTransformName_SubDir()
    {
        assertEquals("water_1", StructureType.transformName("water/1"));
    }
    
    @Test
    public void testTransformName_SubDir_Duplicate()
    {
        assertEquals("water_1", StructureType.transformName("water/water_1"));
    }
    /*
    @Test
    public void testTransformName()
    {
        fail();
    }*/
}
