package org.exolin.citysim.ui;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.exolin.citysim.model.ChangeListener;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.WorldListener;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class WorldHolderTest
{
    @Test
    public void testAddChangeListener()
    {
        World w = new World("test", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        WorldHolder h = new WorldHolder(w);
        h.addChangeListener(new ChangeListener()
        {
            @Override
            public void changed(World oldWorld, World newWorld)
            {
                assertSame(oldWorld, w);
                assertEquals("test2", newWorld.getName());
            }
        });
        
        World w2 = new World("test2", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        h.set(w2, Paths.get("w2"));
    }
    /*
    private static class ExpectedWorldListener implements WorldListener
    {
        
    }*/
    
    @Test
    public void testAddWorldListener()
    {
        World w = new World("test", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        WorldHolder h = new WorldHolder(w);
        
        Deque<Entry<String, Object>> expected = new LinkedList<>(List.of(
                Map.entry("simSpeed", SimulationSpeed.SPEED3)/*,
                
                Map.entry("cityName", "test2"),
                Map.entry("needElectricity", true),
                Map.entry("money", BigDecimal.ONE),
                
                Map.entry("simSpeed", SimulationSpeed.SPEED2)*/
        ));
        
        h.addWorldListener(new WorldListener()
        {
            @Override
            public void onChanged(String name, Object value)
            {
                Entry<String, Object> e = expected.pop();
                assertEquals(e, Map.entry(name, value));
            }
        });
        
        w.setTickFactor(SimulationSpeed.SPEED3);
        
        World w2 = new World("test2", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        //h.set(w2, Paths.get("w2"));
    }
}
