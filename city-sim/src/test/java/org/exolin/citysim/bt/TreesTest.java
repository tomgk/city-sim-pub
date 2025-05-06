package org.exolin.citysim.bt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class TreesTest
{
    @BeforeAll
    public static void init() throws Throwable
    {
        try{
            Trees.XTREES.get(0);
        }catch(ExceptionInInitializerError e){
            throw e.getException();
        }
    }
    
    @Test
    public void test()
    {
        
    }
}
