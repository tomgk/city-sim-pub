package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import org.exolin.citysim.bt.Vacants;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.vacant.Vacant;
import org.exolin.citysim.model.building.vacant.VacantParameters;
import org.exolin.citysim.model.building.vacant.VacantType;
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
public class VacantDataTest
{
    @Test
    public void testSerializeActualBuilding_WithZone() throws IOException
    {
        Vacant building = new Vacant(Vacants.abandoned_big_1, 16, 99, VacantType.Variant.DEFAULT, new VacantParameters(Optional.of(Zones.business)));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"destruction/abandoned_big_1","x":16,"y":99,"zone":"zone_business"}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testSerializeActualBuilding_NoZone() throws IOException
    {
        Vacant building = new Vacant(Vacants.abandoned_big_1, 16, 99, VacantType.Variant.DEFAULT, new VacantParameters(Optional.empty()));
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"destruction/abandoned_big_1","x":16,"y":99}
                          """;
        JSONAssert.assertEquals(expected, output, true);
    }
    
    @Test
    public void testDeserializeActualBuilding_WithZone() throws IOException
    {
        World w = new World("Test", 120, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"destruction/abandoned_big_1","x":16,"y":99,"zone":"zone_business"}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Vacants.abandoned_big_1, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(VacantType.Variant.DEFAULT, b.getVariant());
        Vacant f = (Vacant)b;
        assertEquals(Optional.of(Zones.business), f.getDataCopy().getZoneType());
    }
    
    @Test
    public void testDeserializeActualBuilding_NoZone() throws IOException
    {
        World w = new World("Test", 120, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        InputStream in = createInputStream("""
                                           {"type":"destruction/abandoned_big_1","x":16,"y":99}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(Vacants.abandoned_big_1, b.getType());
        assertEquals(16, b.getX());
        assertEquals(99, b.getY());
        assertEquals(VacantType.Variant.DEFAULT, b.getVariant());
        Vacant f = (Vacant)b;
        assertEquals(Optional.empty(), f.getDataCopy().getZoneType());
    }
}
