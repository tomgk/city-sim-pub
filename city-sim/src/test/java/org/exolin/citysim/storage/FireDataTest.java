package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.fire.Fire;
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
        Fire building = new Fire(FireType.fire, 16, 99, FireType.Variant.DEFAULT, 1);
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"fire","x":16,"y":99}
                          """;
        JSONAssert.assertEquals(expected, output, false);
    }
    
    @Test
    public void testDeserializeActualBuilding_Default() throws IOException
    {
        World w = new World("Test", 100, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"fire","x":16,"y":99}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(FireType.fire, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(FireType.Variant.DEFAULT, b.getVariant());
    }
}
