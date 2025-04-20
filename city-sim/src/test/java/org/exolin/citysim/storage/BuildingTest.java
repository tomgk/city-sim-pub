package org.exolin.citysim.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.exolin.citysim.bt.BusinessBuildings;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
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
public class BuildingTest
{
    @Test
    public void testSerializeActualBuilding_Default() throws IOException
    {
        Building building = new Building(BusinessBuildings.cinema, 16, 99, BuildingType.Variant.DEFAULT);
        String output = serialize(WorldStorage::serialize, building);
        String expected = """
                          {"type":"business_cinema","x":16,"y":99}
                          """;
        JSONAssert.assertEquals(expected, output, false);
    }
    
    @Test
    public void testDeserializeActualBuilding_Default() throws IOException
    {
        World w = new World("Test", 30, BigDecimal.ZERO);
        InputStream in = createInputStream("""
                                           {"type":"business/cinema","x":16,"y":5}
                                           """);
        WorldStorage.deserialize(in, w);
        Structure b = getBuilding(w);
        assertEquals(BusinessBuildings.cinema, b.getType());
        assertEquals(16, b.getX());
        assertEquals(5, b.getY());
        assertEquals(BuildingType.Variant.DEFAULT, b.getVariant());
    }
}
