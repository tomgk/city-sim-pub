package org.exolin.citysim.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.ActualBuildingType;
import org.exolin.citysim.Building;
import org.exolin.citysim.Street;
import org.exolin.citysim.StreetType;
import org.exolin.citysim.World;
import org.exolin.citysim.Zone;
import org.exolin.citysim.ZoneType;
import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

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
    
    private static InputStream createInputStream(String data)
    {
        return new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    }
    
    private Building getBuilding(World w)
    {
        assertEquals(1, w.getBuildings().size());
        return w.getBuildings().get(0);
    }
    
    @Test
    public void testSerializeZone() throws IOException
    {
        Zone zone = new Zone(Zones.zone_residential, 16, 99, ZoneType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, zone);
        String expected = "{\"type\":\"zone_residential\",\"x\":16,\"y\":99}";
        JSONAssert.assertEquals(expected, output, false);
    }
    
    @Test
    public void testDeserializeZone() throws IOException
    {
        World w = World.create(30);
        InputStream in = createInputStream("{\"type\":\"zone_residential\",\"x\":16,\"y\":5}");
        WorldStorage.deserialize(in, w);
        Building b = getBuilding(w);
        assertEquals(Zones.zone_residential, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(ZoneType.Variant.DEFAULT, b.getVariant());
    }
    
    @Test
    public void testSerializeActualBuilding_Default() throws IOException
    {
        ActualBuilding building = new ActualBuilding(BusinessBuildings.cinema, 16, 99, ActualBuildingType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, building);
        String expected = "{\"type\":\"cinema\",\"x\":16,\"y\":99}";
        assertEquals(expected, output);
    }
    
    @Test
    public void testDeserializeActualBuilding_Default() throws IOException
    {
        World w = World.create(30);
        InputStream in = createInputStream("{\"type\":\"cinema\",\"x\":16,\"y\":5}");
        WorldStorage.deserialize(in, w);
        Building b = getBuilding(w);
        assertEquals(BusinessBuildings.cinema, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(ActualBuildingType.Variant.DEFAULT, b.getVariant());
    }
    
    @Test
    public void testSerializeStreet() throws IOException
    {
        Street street = new Street(Streets.street, 16, 99, StreetType.Variant.T_INTERSECTION_4);
        String output = serialize(WorldStorage::serialize, street);
        String expected = "{\"type\":\"street\",\"x\":16,\"y\":99,\"variant\":\"t_intersection_4\"}";
        assertEquals(expected, output);
    }
    
    @Test
    public void testDeserializeStreet() throws IOException
    {
        World w = World.create(30);
        InputStream in = createInputStream("{\"type\":\"street\",\"x\":16,\"y\":5,\"variant\":\"t_intersection_4\"}");
        WorldStorage.deserialize(in, w);
        Building b = getBuilding(w);
        assertEquals(Streets.street, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(StreetType.Variant.T_INTERSECTION_4, b.getVariant());
    }
    
    @Test
    public void testSerializeWorld() throws IOException
    {
        World w = World.Empty();
        w.addBuilding(BusinessBuildings.cinema, 16, 5, ActualBuildingType.Variant.DEFAULT);
        w.addBuilding(Streets.street, 15, 5, StreetType.Variant.T_INTERSECTION_4);
        String output = serialize(WorldStorage::serialize, w);
        String expected = "{\"gridSize\":30,\"buildings\":[{\"type\":\"street\",\"x\":15,\"y\":5,\"variant\":\"t_intersection_4\"},{\"type\":\"cinema\",\"x\":16,\"y\":5}]}";
        assertEquals(expected, output);
    }
    
    @Test
    public void testDeserializeWorld() throws IOException
    {
        InputStream in = createInputStream("{\"gridSize\":30,\"buildings\":[{\"type\":\"street\",\"x\":15,\"y\":5,\"variant\":\"t_intersection_4\"},{\"type\":\"cinema\",\"x\":16,\"y\":5}]}");
        
        World w = WorldStorage.deserialize(in);
        
        assertEquals(30, w.getGridSize());
        
        List<Building> buildings = w.getBuildings();
        assertEquals(2, buildings.size());
        
        {
            Building b = buildings.get(0);
            assertEquals(Streets.street, b.getType());
            assertEquals(15, b.getX());
            assertEquals(5, b.getY());
            assertEquals(StreetType.Variant.T_INTERSECTION_4, b.getVariant());
        }
        {
            Building b = buildings.get(1);
            assertEquals(BusinessBuildings.cinema, b.getType());
            assertEquals(16, b.getX());
            assertEquals(5, b.getY());
            assertEquals(ActualBuildingType.Variant.DEFAULT, b.getVariant());
        }
    }
}
