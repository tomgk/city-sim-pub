package org.exolin.citysim.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ValueTest
{
    @Test
    public void testGetType()
    {
        Value<Boolean> v = new Value<Boolean>()
        {
            @Override
            public Boolean get()
            {
                throw new AssertionError("shouldn't be called");
            }

            @Override
            public void set(Boolean value)
            {
                throw new AssertionError("shouldn't be called");
            }
        };
        
        assertEquals(Boolean.class, v.getType());
    }
    
    @Test
    public void testGetType_Readonly()
    {
        Value.Readonly<Boolean> v = new Value.Readonly<Boolean>()
        {
            @Override
            public Boolean get()
            {
                throw new AssertionError("shouldn't be called");
            }
        };
        
        assertEquals(Boolean.class, v.getType());
    }
}
