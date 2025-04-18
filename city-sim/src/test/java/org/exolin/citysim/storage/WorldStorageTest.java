package org.exolin.citysim.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.ab.ActualBuildingType;
import static org.exolin.citysim.model.connection.regular.TIntersection.T_INTERSECTION_4;
import static org.exolin.citysim.model.connection.regular.Unconnected.UNCONNECTED;
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
    
    static Structure getBuilding(World w)
    {
        assertEquals(1, w.getBuildings().size());
        return w.getBuildings().get(0);
    }
    
    @Test
    public void testSerializeWorld() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO);
        w.addBuilding(BusinessBuildings.cinema, 16, 5, ActualBuildingType.Variant.DEFAULT);
        w.addBuilding(Streets.street, 15, 5, T_INTERSECTION_4);
        w.addBuilding(Zones.business, 15, 4, ZoneType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, w);
        String expected = """
                          {
                            "gridSize":30,
                            "buildings":[
                                {"type": "zone_business", "x": 15, "y": 4},
                                {"type":"street","x":15,"y":5,"variant":"unconnected"},
                                {"type":"business/cinema","x":16,"y":5}
                            ]
                          }
                          """;
        JSONAssert.assertEquals(expected, output, false);
    }
    
    @Test
    public void testDeserializeWorld() throws IOException
    {
        String input =    """
                          {
                            "gridSize":30,
                            "money": 1234,
                            "buildings":[
                                {"type": "zone_business", "x": 15, "y": 4},
                                {"type":"street","x":15,"y":5,"variant":"t_intersection_4"},
                                {"type":"business/cinema","x":16,"y":5}
                            ]
                          }
                          """;
        InputStream in = createInputStream(input);
        
        World w = WorldStorage.deserialize("Test", in);
        
        assertEquals("Test", w.getName());
        assertEquals(30, w.getGridSize());
        assertEquals(BigDecimal.valueOf(1234), w.getMoney());
        
        List<Structure> buildings = w.getBuildings();
        assertEquals(3, buildings.size());
        
        {
            Structure b = buildings.get(0);
            assertEquals(Zones.business, b.getType());
            assertEquals(15, b.getX());
            assertEquals(4, b.getY());
            assertEquals(ZoneType.Variant.DEFAULT, b.getVariant());
        }
        
        {
            Structure b = buildings.get(1);
            assertEquals(Streets.street, b.getType());
            assertEquals(15, b.getX());
            assertEquals(5, b.getY());
            assertEquals(UNCONNECTED, b.getVariant());
        }
        {
            Structure b = buildings.get(2);
            assertEquals(BusinessBuildings.cinema, b.getType());
            assertEquals(16, b.getX());
            assertEquals(5, b.getY());
            assertEquals(ActualBuildingType.Variant.DEFAULT, b.getVariant());
        }
    }
}
