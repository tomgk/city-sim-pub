package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;
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
        Fire building = new Fire(FireType.fire, 16, 99, FireType.Variant.V1, new FireParameters(123, Optional.empty()));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123}
                          """;
        System.out.println(expected);
        System.out.println(output);
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeActualBuilding_WithZone() throws IOException
    {
        Fire building = new Fire(FireType.fire, 16, 99, FireType.Variant.V1, new FireParameters(123, Optional.of(Zones.business)));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99,"variant":"v1","remainingLife": 123,"zone":"zone_business"}
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
        assertEquals(FireType.Variant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.empty(), f.getData().getZone());
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
        assertEquals(FireType.Variant.V1, b.getVariant());
        Fire f = (Fire)b;
        assertEquals(123, f.getData().getRemainingLife());
        assertEquals(Optional.of(Zones.business), f.getData().getZone());
    }
}
