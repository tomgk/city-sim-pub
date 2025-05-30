package org.exolin.citysim.model;

import java.awt.Point;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import static org.exolin.citysim.model.Worlds.placeStreet;
import static org.exolin.citysim.model.Worlds.placeZone;
import org.exolin.citysim.model.fire.Fire;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.storage.WorldStorage;
import org.exolin.citysim.storage.WorldStorageTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Thomas
 */
public class WorldTest
{
    @Test
    public void testGetBuilding()
    {
        World w = new World("Test", 30, BigDecimal.valueOf(100000), SimulationSpeed.PAUSED);
        
        assertEquals(3, BusinessBuildings.cinema.getSize());
        
        //[10, 7] .. [12, 9]
        Structure<?, ?, ?, ?> b = w.addBuilding(BusinessBuildings.cinema, 10, 7);
        
        //-----------------
        
        assertSame(b, w.getBuildingAt(10, 7));
        assertSame(b, w.getBuildingAt(11, 7));
        assertSame(b, w.getBuildingAt(12, 7));
        
        assertSame(b, w.getBuildingAt(10, 8));
        assertSame(b, w.getBuildingAt(11, 8));
        assertSame(b, w.getBuildingAt(12, 8));
        
        assertSame(b, w.getBuildingAt(10, 9));
        assertSame(b, w.getBuildingAt(11, 9));
        assertSame(b, w.getBuildingAt(12, 9));
        
        //-----------------
        /*
        assertNull(w.getBuildingAt(10, 7));
        assertNull(w.getBuildingAt(11, 7));
        assertNull(w.getBuildingAt(12, 7));
        */
        
        //to the left (x-1)
        assertNull(w.getBuildingAt(9, 7));
        assertNull(w.getBuildingAt(9, 8));
        assertNull(w.getBuildingAt(9, 9));
        
        //to the right (x+size)
        assertNull(w.getBuildingAt(13, 7));
        assertNull(w.getBuildingAt(13, 8));
        assertNull(w.getBuildingAt(13, 9));
        
        //above (y-1)
        assertNull(w.getBuildingAt(10, 6));
        assertNull(w.getBuildingAt(11, 6));
        assertNull(w.getBuildingAt(12, 6));
        
        //below (y+size)
        assertNull(w.getBuildingAt(10, 10));
        assertNull(w.getBuildingAt(11, 10));
        assertNull(w.getBuildingAt(12, 10));
        
        //to the top left (x-1, y-1)
        assertNull(w.getBuildingAt(9, 6));
        //to the top right (x-1, y+size)
        assertNull(w.getBuildingAt(13, 6));
        //to the bottom left (x+size, y-1)
        assertNull(w.getBuildingAt(9, 13));
        //to the bottom right (x+size, y+size)
        assertNull(w.getBuildingAt(13, 13));
    }
    
    @Test
    public void testPlaceBiggerBuilding_OnBigger() throws IOException
    {
        World w = new World("test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        
        w.addBuilding(BusinessBuildings.car_cinema, 1, 1);
        
        //should remove the other building
        w.addBuilding(BusinessBuildings.car_cinema, 3, 3);
        
        String expected = """
                          {
                            "gridSize" : 30,
                            "money" : 0,
                            "buildings" : [ {
                              "type" : "business_car-cinema",
                              "x" : 3,
                              "y" : 3
                            } ],
                            "speed" : "paused"
                          }
                          """;
        
        WorldStorage.serialize(w, System.out);
        
        JSONAssert.assertEquals(WorldStorageTest.serialize(w), expected, true);
    }
    
    @Test
    public void testPlaceBiggerBuilding_OnMultipleSmaller() throws IOException, InterruptedException
    {
        World w = new World("test", 30, BigDecimal.ZERO, SimulationSpeed.PAUSED);
        
        /*
        0
         xxxx
         xxxx
         xxxx
         xxxx
        */
        Fire.placeMultiple(w, Zones.business, 1, 1, 4, 4, ZoneType.Variant.DEFAULT, EmptyStructureParameters.getInstance());
        
        //WorldStorage.serialize(w, System.out);
        
        //System.out.println();
        //System.out.println("---------------");
        
        /*
        0
         xxxx
         xxxx
         xxBBBB
         xxBBBB
           BBBB
        */
        w.addBuilding(BusinessBuildings.car_cinema, 3, 3);
        
        assertEquals(13, w.getStructures().size());
        
        //WorldStorage.serialize(w, System.out);
        
        String expected = """
                          {
                            "gridSize" : 30,
                            "money" : 0,
                            "buildings" : [ {
                              "type" : "zone_business",
                              "x" : 1,
                              "y" : 1
                            }, {
                              "type" : "zone_business",
                              "x" : 2,
                              "y" : 1
                            }, {
                              "type" : "zone_business",
                              "x" : 1,
                              "y" : 2
                            }, {
                              "type" : "zone_business",
                              "x" : 3,
                              "y" : 1
                            }, {
                              "type" : "zone_business",
                              "x" : 2,
                              "y" : 2
                            }, {
                              "type" : "zone_business",
                              "x" : 1,
                              "y" : 3
                            }, {
                              "type" : "zone_business",
                              "x" : 4,
                              "y" : 1
                            }, {
                              "type" : "zone_business",
                              "x" : 3,
                              "y" : 2
                            }, {
                              "type" : "zone_business",
                              "x" : 2,
                              "y" : 3
                            }, {
                              "type" : "zone_business",
                              "x" : 1,
                              "y" : 4
                            }, {
                              "type" : "zone_business",
                              "x" : 4,
                              "y" : 2
                            }, {
                              "type" : "zone_business",
                              "x" : 2,
                              "y" : 4
                            }, {
                              "type" : "business_car-cinema",
                              "x" : 3,
                              "y" : 3
                            } ],
                            "speed" : "paused"
                          }
                          """;
        
        //WorldStorage.serialize(w, System.out);
        
        JSONAssert.assertEquals(WorldStorageTest.serialize(w), expected, true);
    }
    
    @Test
    @Disabled
    public void test()
    {
        World w = new World("Test", 30, BigDecimal.ONE, SimulationSpeed.PAUSED);
        
        GetWorld getWorld = GetWorld.ofStatic(w);
        
        placeZone(w, Zones.residential, ZoneType.Variant.DEFAULT, 0, 0, 2, 2);
        placeStreet(w, 0, 2, 2, 2);
        
        w.updateAfterTick(1, 1);
        
        System.out.println(w.getStructures().stream().map(Object::toString).collect(Collectors.joining("\n")));
        assertEquals("xxxx", w.getStructures());
    }
    
    @Test
    public void testElectricity()
    {
        World w = Worlds.ElectricityWorld();
        w.updateAfterTick(0, 0);
        
        Map<ElectricityGrid, List<Structure<?, ?, ?, ?>>> grids = w.getElectricityGrids();
        assertEquals(2, grids.size());
        
        ArrayList<ElectricityGrid> gridz = new ArrayList<>(grids.keySet());
        gridz.sort(Comparator.comparing(g -> grids.get(g).size()).reversed());
        
        ElectricityGrid grid1 = gridz.get(0);
        ElectricityGrid grid2 = gridz.get(1);
        
        assertEquals(1, grid1.getPlants().size());
        assertEquals(1, grid2.getPlants().size());
        
        List<Structure<?, ?, ?, ?>> structures1 = grids.get(grid1);
        List<Structure<?, ?, ?, ?>> structures2 = grids.get(grid2);
        
        assertEquals(8, structures1.size());
        assertEquals(7, structures2.size());
        
        List<Point> expectedLocations1 = List.of(p(0, 0),
                                                 p(4, 0),
                                                 p(5, 0),
                                                 p(6, 0),
                                                 p(7, 0),
                                                 p(8, 0),
                                                 p(9, 0),
                                                 p(10, 0));
        List<Point> actualLocations1 = getLocations(structures1);
        assertEquals(expectedLocations1, actualLocations1);
        
        List<Point> expectedLocations2 = List.of(p(5, 5),
                                                 p(9, 5),
                                                 p(10, 5),
                                                 p(11, 5),
                                                 p(12, 5),
                                                 p(13, 5),
                                                 p(14, 5));
        List<Point> actualLocations2 = getLocations(structures2);
        assertEquals(expectedLocations2, actualLocations2);
    }
    
    @Test
    public void testElectricity2()
    {
        World w = Worlds.ElectricityWorld2();
        w.updateAfterTick(0, 0);
        
        Map<ElectricityGrid, List<Structure<?, ?, ?, ?>>> grids = w.getElectricityGrids();
        assertEquals(1, grids.size());
        
        ElectricityGrid grid1 = grids.keySet().iterator().next();
        
        assertEquals(1, grid1.getPlants().size());
        
        List<Structure<?, ?, ?, ?>> structures1 = grids.get(grid1);
        
        assertEquals(24, structures1.size());
        
        List<Point> expectedLocations1 = List.of(p(0, 0),
                                                 p(4, 0),
                                                 p(5, 0),
                                                 p(5, 5),
                                                 p(6, 0),
                                                 p(7, 0),
                                                 p(8, 0),
                                                 p(9, 0),
                                                 p(9, 5),
                                                 p(10, 0),
                                                 p(10, 1),
                                                 p(10, 2),
                                                 p(10, 3),
                                                 p(10, 4),
                                                 p(10, 5),
                                                 p(10, 6),
                                                 p(10, 7),
                                                 p(10, 8),
                                                 p(10, 9),
                                                 p(10, 10),
                                                 p(11, 5),
                                                 p(12, 5),
                                                 p(13, 5),
                                                 p(14, 5));
        List<Point> actualLocations1 = getLocations(structures1);
        printLocations(actualLocations1);
        assertEquals(expectedLocations1, actualLocations1);
    }
    
    @Test
    @Disabled
    public void testElectricity3()
    {
        World w = Worlds.ElectricityWorld2();
        w.updateAfterTick(0, 0);
        
        Structure<?, ?, ?, ?> building = w.getBuildingAt(14, 5);
        
        assertNotNull(building);
        
        //succeds but doesn't work when playing the game
        assertTrue(w.hasElectricity(building));
        
        w.getStructuresWithElectricity().forEach((k,v) -> System.out.println(k+"="+v));
        
        fail();
    }
    
    private Point p(int x, int y)
    {
        return new Point(x, y);
    }
    
    private void printLocations(List<Point> points)
    {
        String str = points.stream()
                .map(p -> "p("+p.x+", "+p.y+")")
                .collect(Collectors.joining(",\n", "List.of(", ")"));
        System.out.println(str);
    }
    
    private List<Point> getLocations(List<Structure<?, ?, ?, ?>> structures)
    {
        return structures.stream()
                .map(b -> p(b.getX(), b.getY()))
                .sorted(Comparator.comparing((Point p) -> p.x).thenComparing((Point p) -> p.y))
                .toList();
    }
}
