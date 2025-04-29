package org.exolin.citysim.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class EmptyStructureParametersTest
{
    @Test
    public void testGetInstance()
    {
        assertSame(EmptyStructureParameters.getInstance(), EmptyStructureParameters.getInstance());
    }
    
    @Test
    public void testCopy()
    {
        assertSame(EmptyStructureParameters.getInstance(), EmptyStructureParameters.getInstance().copy());
    }
    
    @Test
    public void testConstructTwice() throws Throwable
    {
        Constructor<EmptyStructureParameters> c = EmptyStructureParameters.class.getDeclaredConstructor();
        c.setAccessible(true);
        try{
            c.newInstance();
            fail();
        }catch(InvocationTargetException e){
            try{
                throw e.getTargetException();
            }catch(IllegalStateException ex){
                assertEquals("only allowed once", ex.getMessage());
            }
        }
    }
}
