package org.exolin.citysim.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.SingleVariant;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.BuildingType;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.fire.FireVariant;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Thomas
 */
public class WorldStorageTest
{
    public interface Serializer<T>
    {
        void write(T value, OutputStream out) throws IOException;
    }
    
    public static <T> String serialize(World object) throws IOException
    {
        return serialize(WorldStorage::serialize, object);
    }
    
    public static <T> String serialize(Serializer<T> serializer, T object) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.write(object, out);
        return out.toString(StandardCharsets.UTF_8);
    }
    
    static InputStream createInputStream(String data)
    {
        return new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
    }
    
    static Structure<?, ?, ?, ?> getBuilding(World w)
    {
        assertEquals(1, w.getStructures().size());
        return w.getStructures().get(0);
    }
    
    @Test
    public void testSerializeWorld() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        w.addBuilding(BusinessBuildings.cinema, 16, 5, BuildingType.Variant.DEFAULT);
        w.addBuilding(SelfConnections.street, 15, 5, T_INTERSECTION_4);
        w.addBuilding(Zones.business, 15, 4);
        w.addBuilding(FireType.fire, 29, 28, FireVariant.V1, new FireParameters(134, Optional.empty(), false, Optional.empty()));
        String output = serialize(WorldStorage::serialize, w);
        String expected = """
                          {
                            "gridSize":30,
                            "money": 0,
                            "speed": "paused",
                            "buildings":[
                                {"type": "zone_business", "x": 15, "y": 4},
                                {"type":"street","x":15,"y":5,"variant":"unconnected"},
                                {"type":"business_cinema","x":16,"y":5},
                                {"type": "fire", "x": 29, "y": 28, "variant":"v1", "remainingLife": 134, "returnToZone": false}
                            ]
                          }
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeWorld() throws IOException
    {
        String input =    """
                          {
                            "gridSize":30,
                            "money": 1234,
                            "speed": "paused",
                            "buildings":[
                                {"type": "zone_business", "x": 15, "y": 4},
                                {"type":"street","x":15,"y":5,"variant":"t_intersection_4"},
                                {"type":"business/cinema","x":16,"y":5},
                                {"type": "fire", "x": 29, "y": 28, "variant":"v1", "remainingLife": 134}
                            ]
                          }
                          """;
        InputStream in = createInputStream(input);
        
        World w = WorldStorage.deserialize("Test", in);
        
        assertEquals("Test", w.getName());
        assertEquals(SimulationSpeed.PAUSED, w.getTickFactor());
        assertEquals(30, w.getGridSize());
        assertEquals(BigDecimal.valueOf(1234), w.getMoney());
        
        List<Structure<?, ?, ?, ?>> buildings = w.getStructures();
        assertEquals(4, buildings.size());
        
        {
            Structure<?, ?, ?, ?> b = buildings.get(0);
            assertEquals(Zones.business, b.getType());
            assertEquals(15, b.getX());
            assertEquals(4, b.getY());
            assertEquals(SingleVariant.DEFAULT, b.getVariant());
        }
        
        {
            Structure<?, ?, ?, ?> b = buildings.get(1);
            assertEquals(SelfConnections.street, b.getType());
            assertEquals(15, b.getX());
            assertEquals(5, b.getY());
            assertEquals(UNCONNECTED, b.getVariant());
        }
        {
            Structure<?, ?, ?, ?> b = buildings.get(2);
            assertEquals(BusinessBuildings.cinema, b.getType());
            assertEquals(16, b.getX());
            assertEquals(5, b.getY());
            assertEquals(BuildingType.Variant.DEFAULT, b.getVariant());
        }
        {
            Structure<?, ?, ?, ?> b = buildings.get(3);
            assertEquals(FireType.fire, b.getType());
            assertEquals(29, b.getX());
            assertEquals(28, b.getY());
            assertEquals(FireVariant.V1, b.getVariant());
            Fire f = (Fire)b;
            assertEquals(134, f.getDataCopy().getRemainingLife());
            assertEquals(Optional.empty(), f.getDataCopy().getZone());
            assertEquals(false, f.getDataCopy().isReturnToZone());
        }
    }
}
