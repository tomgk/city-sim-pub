package org.exolin.citysim.model;

import org.exolin.citysim.model.debug.ReadonlyValue;
import org.exolin.citysim.model.debug.Value;
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
        ReadonlyValue<Boolean> v = new ReadonlyValue<Boolean>()
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
