package org.exolin.citysim.utils;

import java.util.List;
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
    
    @Test
    public void testRotate()
    {
        assertEquals(List.of(1, 2, 3), Utils.rotate(List.of(1, 2, 3), 0));
        assertEquals(List.of(3, 1, 2), Utils.rotate(List.of(1, 2, 3), 1));
        assertEquals(List.of(2, 3, 1), Utils.rotate(List.of(1, 2, 3), 2));
        assertEquals(List.of(1, 2, 3), Utils.rotate(List.of(1, 2, 3), 3));
        assertEquals(List.of(2, 3, 1), Utils.rotate(List.of(1, 2, 3), -1));
    }
}
