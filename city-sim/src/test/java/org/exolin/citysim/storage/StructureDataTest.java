package org.exolin.citysim.storage;

import java.lang.reflect.Field;
import java.util.Optional;
import org.exolin.citysim.bt.buildings.Plants;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.StraightConnectionVariant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureDataTest
{
    private <T> T getField(Object o, String name, Class<T> type)
    {
        return getField(o, o.getClass(), name, type);
    }
    
    private <T> T getField(Object o, Class<?> clazz, String name, Class<T> type)
    {
        try{
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return type.cast(f.get(o));
        }catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }catch(NoSuchFieldException e){
            if(clazz == Object.class)
                throw new RuntimeException(e);
            
            return getField(o, clazz.getSuperclass(), name, type);
        }
    }
    
    @Test
    public void testConstruct_VariantDefault()
    {
        Building b = new Building(Plants.gas_plant, 4, 7, BuildingType.Variant.DEFAULT);
        BuildingData bd = new BuildingData(b);
        
        assertEquals(Optional.empty(), getField(bd, "variant", Optional.class));
    }
    
    @Test
    public void testConstruct_VariantNonDefault()
    {
        SelfConnection b = new SelfConnection(SelfConnections.street, 4, 7, StraightConnectionVariant.CONNECT_X);
        SelfConnectionData bd = new SelfConnectionData(b);
        
        assertEquals(Optional.of("connect_x"), getField(bd, "variant", Optional.class));
    }
}
