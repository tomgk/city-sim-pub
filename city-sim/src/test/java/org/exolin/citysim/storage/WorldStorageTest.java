package org.exolin.citysim.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.Street;
import org.exolin.citysim.StreetType;
import org.exolin.citysim.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class WorldStorageTest
{
    @Test
    public void testSerializeActualBuilding() throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WorldStorage.serialize(new ActualBuilding(World.cinema, 16, 99, ActualBuildingType.Variant.DEFAULT), out);
        String output = out.toString(StandardCharsets.UTF_8);
        String expected = "{\"type\":\"cinema\",\"x\":16,\"y\":99,\"variant\":0}";
        Assertions.assertEquals(expected, output);
    }
    
    @Test
    public void testSerializeStreet() throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WorldStorage.serialize(new Street(World.street, 16, 99, StreetType.Variant.T_INTERSECTION_4), out);
        String output = out.toString(StandardCharsets.UTF_8);
        String expected = "{\"type\":\"street\",\"x\":16,\"y\":99,\"variant\":10}";
        Assertions.assertEquals(expected, output);
    }
}
