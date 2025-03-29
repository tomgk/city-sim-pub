package org.exolin.citysim.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.Street;
import org.exolin.citysim.StreetType;
import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.bt.Streets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class WorldStorageTest
{
    private interface Serializer<T>
    {
        void write(T value, OutputStream out) throws IOException;
    }
    
    private static <T> String serialize(Serializer<T> serializer, T object) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.write(object, out);
        return out.toString(StandardCharsets.UTF_8);
    }
    
    @Test
    public void testSerializeActualBuilding() throws IOException
    {
        ActualBuilding building = new ActualBuilding(BusinessBuildings.cinema, 16, 99, ActualBuildingType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, building);
        String expected = "{\"type\":\"cinema\",\"x\":16,\"y\":99,\"variant\":0}";
        Assertions.assertEquals(expected, output);
    }
    
    @Test
    public void testSerializeStreet() throws IOException
    {
        Street street = new Street(Streets.street, 16, 99, StreetType.Variant.T_INTERSECTION_4);
        String output = serialize(WorldStorage::serialize, street);
        String expected = "{\"type\":\"street\",\"x\":16,\"y\":99,\"variant\":10}";
        Assertions.assertEquals(expected, output);
    }
}
