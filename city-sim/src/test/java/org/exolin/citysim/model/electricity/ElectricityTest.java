package org.exolin.citysim.model.electricity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.Worlds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class ElectricityTest
{
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
        //printLocations(actualLocations1);
        assertEquals(expectedLocations1, actualLocations1);
    }
    
    @Test
    public void testElectricity3()
    {
        World w = Worlds.ElectricityWorld3();
        w.updateAfterTick(0, 0);
        
        Structure<?, ?, ?, ?> building = w.getBuildingAt(14, 5);
        
        assertNotNull(building);
        
        w.getStructuresWithElectricity().forEach((k,v) -> System.out.println(k+"="+v));
        
        assertTrue(w.hasElectricity(building));
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
