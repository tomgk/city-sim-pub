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
}
