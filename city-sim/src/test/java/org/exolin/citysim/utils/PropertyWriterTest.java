package org.exolin.citysim.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class PropertyWriterTest
{
    @Test
    public void testEmpty()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.finish();
        
        assertEquals("test[]", p.toString());
    }
}
