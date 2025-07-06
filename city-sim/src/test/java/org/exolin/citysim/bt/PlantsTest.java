package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.model.plant.PlantVariant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class PlantsTest
{
    @BeforeAll
    public static void init() throws Throwable
    {
        try{
            Plants.getFirst(PlantTypeType.TREE);
        }catch(ExceptionInInitializerError e){
            throw e.getException();
        }
    }
    
    private List<String> getFileNames(List<PlantType> type)
    {
        return type.stream()
                .map(t -> t.getImage(PlantVariant.DEFAULT).getUnaminatedFileName())
                .toList();
    }
    
    @Test
    public void testTrees_FileNames()
    {
        assertEquals(List.of(
                "trees_1",
                "trees_2",
                "trees_3",
                "trees_4",
                "trees_5",
                "trees_6",
                "trees_7"
        ), getFileNames(Plants.getx(PlantTypeType.TREE)));
    }
    
    @Test
    public void testDeadTrees_FileNames()
    {
        assertEquals(List.of(
                "trees_dead_1",
                "trees_dead_2",
                "trees_dead_3",
                "trees_dead_4",
                "trees_dead_5",
                "trees_dead_6",
                "trees_dead_7"
        ), getFileNames(Plants.getDead(PlantTypeType.TREE)));
    }
    
    @Test
    public void testGrass_FileNames()
    {
        assertEquals(List.of(
                "grass_1",
                "grass_2",
                "grass_3",
                "grass_4",
                "grass_5",
                "grass_6",
                "grass_7"
        ), getFileNames(Plants.getx(PlantTypeType.GRASS)));
    }
    
    @Test
    public void testDeadGrass_FileNames()
    {
        assertEquals(List.of(
                "grass_dead_1",
                "grass_dead_2",
                "grass_dead_3",
                "grass_dead_4",
                "grass_dead_5",
                "grass_dead_6",
                "grass_dead_7"
        ), getFileNames(Plants.getDead(PlantTypeType.GRASS)));
    }
    
    @Test
    public void get_Tree()
    {
        for(int i=1;i<=7;++i)
        {
            PlantType pt = Plants.get(PlantTypeType.TREE, i);
            assertEquals(PlantTypeType.TREE, pt.getType());
            assertEquals(i, pt.getCount());
            assertTrue(pt.isAlive());
        }
    }
    
    @Test
    public void get_Grass()
    {
        for(int i=1;i<=7;++i)
        {
            PlantType pt = Plants.get(PlantTypeType.GRASS, i);
            assertEquals(PlantTypeType.GRASS, pt.getType());
            assertEquals(i, pt.getCount());
            assertTrue(pt.isAlive());
        }
    }
    
    @Test
    public void get_below()
    {
        var e = assertThrows(IllegalArgumentException.class, () -> Plants.get(PlantTypeType.TREE, 0));
        assertEquals("0 out of range [1..7]", e.getMessage());
    }
    
    @Test
    public void get_above()
    {
        var e = assertThrows(IllegalArgumentException.class, () -> Plants.get(PlantTypeType.TREE, 8));
        assertEquals("8 out of range [1..7]", e.getMessage());
    }
}
