package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Plants;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.plant.Plant;
import org.exolin.citysim.model.plant.PlantParameters;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.model.plant.PlantVariant;
import static org.exolin.citysim.storage.WorldStorageTest.createInputStream;
import static org.exolin.citysim.storage.WorldStorageTest.getBuilding;
import static org.exolin.citysim.storage.WorldStorageTest.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Thomas
 */
public class TreeDataTest
{
    @Test
    public void testSerializePlant() throws IOException
    {
        Plant plant = new Plant(Plants.get(PlantTypeType.TREE, 4), 16, 99, PlantVariant.TOP_RIGHT, new PlantParameters(Optional.empty()));
        String output = serialize(WorldStorage::serialize, plant);
        String expected = """
                          {"type":"trees_4","x":16,"y":99,"variant":"top_right"}
                          """;
        System.out.println(output);
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializePlant_WithZone() throws IOException
    {
        Plant plant = new Plant(Plants.get(PlantTypeType.TREE, 4), 16, 99, PlantVariant.TOP_RIGHT, new PlantParameters(Optional.of(Zones.residential)));
        String output = serialize(WorldStorage::serialize, plant);
        String expected = """
                          {"type":"trees_4","x":16,"y":99,"variant":"top_right","zone":"zone_residential"}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeTree() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream(
                          """
                          {"type":"trees_4","x":16,"y":22,"variant":"top_right"}
                          """);
        WorldStorage.deserialize(in, w);
        Structure<?, ?, ?, ?> b = getBuilding(w);
        assertEquals(Plants.get(PlantTypeType.TREE, 4), b.getType());
        assertEquals(16, b.getX());
        assertEquals(22, b.getY());
        assertEquals(PlantVariant.TOP_RIGHT, b.getVariant());
        PlantParameters data = (PlantParameters) b.getDataCopy();
        assertEquals(Optional.empty(), data.getZone());
    }
    
    @Test
    public void testDeserializeTree_WithZone() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream(
                          """
                          {"type":"trees_4","x":16,"y":22,"variant":"top_right","zone":"zone_residential"}
                          """);
        WorldStorage.deserialize(in, w);
        Structure<?, ?, ?, ?> b = getBuilding(w);
        assertEquals(Plants.get(PlantTypeType.TREE, 4), b.getType());
        assertEquals(16, b.getX());
        assertEquals(22, b.getY());
        assertEquals(PlantVariant.TOP_RIGHT, b.getVariant());
        PlantParameters data = (PlantParameters) b.getDataCopy();
        assertEquals(Optional.of(Zones.residential), data.getZone());
    }
}
