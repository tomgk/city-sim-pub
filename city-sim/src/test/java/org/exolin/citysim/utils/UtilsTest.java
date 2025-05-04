package org.exolin.citysim.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class UtilsTest
{
    enum Value
    {
        a, b, c
    }
    
    private static final Value[] values = Value.values();
    
    @Test
    public void testGetPrev()
    {
        assertEquals(Value.c, Utils.getPrev(values, 0));
        assertEquals(Value.a, Utils.getPrev(values, 1));
        assertEquals(Value.b, Utils.getPrev(values, 2));
    }
    
    @Test
    public void testGetNext()
    {
        assertEquals(Value.b, Utils.getNext(values, 0));
        assertEquals(Value.c, Utils.getNext(values, 1));
        assertEquals(Value.a, Utils.getNext(values, 2));
    }
}
