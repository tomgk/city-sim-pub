package org.exolin.citysim.bt;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ZonesTest
{
    @Test
    public void test() throws Throwable
    {
        try{
            Zones.BASIC_ZONES.size();
        }catch(ExceptionInInitializerError e){
            throw e.getException();
        }
    }
}
