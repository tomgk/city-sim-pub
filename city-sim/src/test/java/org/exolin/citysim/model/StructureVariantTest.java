package org.exolin.citysim.model;

import java.util.EnumSet;
import java.util.Set;
import org.exolin.citysim.model.connection.regular.ConnectVariant;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureVariantTest
{
    @Test
    public void testConnectVariant_Values()
    {
        Set<? extends StructureVariant> values = StructureVariant.getValues(ConnectVariant.class);
        assertEquals(EnumSet.of(CONNECT_X, CONNECT_Y), values);
    }
    
    @Test
    public void testConnectVariant_Values_()
    {
        try{
            StructureVariant.getValues(ConnectionVariant.class);
            fail();
        }catch(UnsupportedOperationException e){
            assertEquals(ConnectionVariant.class.getName(), e.getMessage());
        }
    }
}
