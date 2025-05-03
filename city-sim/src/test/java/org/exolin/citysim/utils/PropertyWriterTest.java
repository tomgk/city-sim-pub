package org.exolin.citysim.utils;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
    
    @Test
    public void testAdd_int()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.add("value", 1);
        p.finish();
        
        assertEquals("test[value=1]", p.toString());
    }
    
    @Test
    public void testAdd_String()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.add("name", "abc");
        p.finish();
        
        assertEquals("test[name=abc]", p.toString());
    }
    
    @Test
    public void testAddOptional_Boolean_false()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.addOptional("advanced", false);
        p.finish();
        
        assertEquals("test[]", p.toString());
    }
    
    @Test
    public void testAddOptional_Boolean_true()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.addOptional("advanced", true);
        p.finish();
        
        assertEquals("test[advanced=true]", p.toString());
    }
    
    @Test
    public void testAddOptional_Object_false()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.addOptional("object", Optional.empty());
        p.finish();
        
        assertEquals("test[]", p.toString());
    }
    
    @Test
    public void testAddOptional_Object_true()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.addOptional("object", Optional.of("beans"));
        p.finish();
        
        assertEquals("test[object=beans]", p.toString());
    }
    
    @Test
    public void testPrematureToString()
    {
        PropertyWriter p = new PropertyWriter("test");
        try{
            p.toString();
            fail();
        }catch(IllegalStateException e){
            assertEquals("not finished yet", e.getMessage());
        }
    }
    
    @Test
    public void testDoubleFinish()
    {
        PropertyWriter p = new PropertyWriter("test");
        p.finish();
        try{
            p.finish();
            fail();
        }catch(IllegalStateException e){
            assertEquals("already finished", e.getMessage());
        }
    }
}
