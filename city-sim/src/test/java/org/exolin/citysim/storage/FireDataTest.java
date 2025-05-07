package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.fire.FireVariant;
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
public class FireDataTest
{
    @Test
    public void testSerializeActualBuilding_Default() throws IOException
    {
        Fire building = new Fire(FireType.fire, 16, 99, FireVariant.V1, new FireParameters(123, Optional.empty(), false, Optional.empty()));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123, "returnToZone": false}
                          """;
        //System.out.println(expected);
        //System.out.println(output);
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeActualBuilding_WithZone() throws IOException
    {
        Fire building = new Fire(FireType.fire, 16, 99, FireVariant.V1, new FireParameters(123, Optional.of(Zones.business), false, Optional.empty()));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business", "returnToZone": false}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeActualBuilding_ReturnToZone() throws IOException
    {
        Fire building = new Fire(FireType.fire, 16, 99, FireVariant.V1, new FireParameters(123, Optional.of(Zones.business), true, Optional.empty()));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business", "returnToZone": true}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeActualBuilding_AfterBurn() throws IOException
    {
        Fire building = new Fire(FireType.fire, 16, 99, FireVariant.V1, new FireParameters(123, Optional.of(Zones.business), false, Optional.of(Trees.XDEAD_TREES.get(3))));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business", "returnToZone": false, "afterBurn": "trees_dead_4"}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeActualBuilding_Default() throws IOException
    {
        World w = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(FireType.fire, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(FireVariant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.empty(), f.getData().getZone());
        assertEquals(false, f.getData().isReturnToZone());
    }
    
    @Test
    public void testDeserializeActualBuilding_WithZone() throws IOException
    {
        World w = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business"}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(FireType.fire, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(FireVariant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.of(Zones.business), f.getData().getZone());
        assertEquals(false, f.getData().isReturnToZone());
    }
    
    @Test
    public void testDeserializeActualBuilding_ReturnToZone() throws IOException
    {
        World w = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business", "returnToZone": true}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(FireType.fire, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(FireVariant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.of(Zones.business), f.getData().getZone());
        assertEquals(true, f.getData().isReturnToZone());
    }
    
    @Test
    public void testDeserializeActualBuilding_AfterBurn() throws IOException
    {
        World w = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business", "returnToZone": true, "afterBurn": "trees_dead_2"}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(FireType.fire, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(FireVariant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.of(Zones.business), f.getData().getZone());
        assertEquals(true, f.getData().isReturnToZone());
        assertEquals(Optional.of(Trees.XDEAD_TREES.get(1)), f.getData().getAfterBurn());
    }
}
