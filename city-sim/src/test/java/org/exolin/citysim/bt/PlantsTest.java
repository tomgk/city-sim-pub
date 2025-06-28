package org.exolin.citysim.bt;

import java.util.List;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantTypeType;
import org.exolin.citysim.model.plant.PlantVariant;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
