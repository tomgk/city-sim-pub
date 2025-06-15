package org.exolin.citysim.ui;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Arrays;
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
        World w2 = new World("test2", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        WorldHolder h = new WorldHolder(w);
        
        ChangeListener cl = new ChangeListener()
        {
            @Override
            public void changed(World oldWorld, World newWorld)
            {
                assertSame(oldWorld, w);
                assertSame(newWorld, w2);
            }
        };
        
        h.addChangeListener(cl);
        
        h.set(w2, Paths.get("w2"));
        
        h.removeChangeListener(cl);
        h.set(w, Paths.get("w"));
    }
    
    private static class ExpectedWorldListener implements WorldListener
    {
        private final Deque<Entry<String, Object>> expected = new LinkedList<>();
        
        @Override
        public void onChanged(String name, Object value)
        {
            if(expected.isEmpty())
                fail("Expected no more but got "+name+"="+value);
            
            Entry<String, Object> e = expected.pop();
            assertEquals(e, Map.entry(name, value));
        }
        
        public void setExpected(Entry<String, Object>...entries)
        {
            setExpected(Arrays.asList(entries));
        }
        
        public void setExpected(List<Entry<String, Object>> entries)
        {
            checkFinished();
            expected.clear();
            expected.addAll(entries);
        }
        
        public void checkFinished()
        {
            if(!expected.isEmpty())
                fail();
        }
    }
    
    private static List<Entry<String, Object>> all(World w)
    {
        return List.of(Map.entry("cityName", w.getName()),
                Map.entry("needElectricity", true),
                Map.entry("money", w.getMoney()),
                Map.entry("simSpeed", w.getTickFactor()),
                Map.entry("structureCount", 0),
                Map.entry("lastMoneyUpdate", 0l),
                Map.entry("lastChange.date", World.getLocalDateForTimeMillis(w.getLastChange())),
                Map.entry("lastChange.time", World.getLocalTimeForTimeMillis(w.getLastChange())),
                Map.entry("electricityCoverage", "--/--")
        );
    }
    
    @Test
    public void testAddWorldListener()
    {
        World w = new World("test", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        World w2 = new World("test2", 30, BigDecimal.ONE, SimulationSpeed.SPEED2);
        
        WorldHolder h = new WorldHolder(w);
        
        ExpectedWorldListener l = new ExpectedWorldListener();
        
        h.addWorldListener(l);
        
        l.setExpected(Map.entry("simSpeed", SimulationSpeed.SPEED3));
        w.setTickFactor(SimulationSpeed.SPEED3);
        
        l.setExpected(all(w2));
        h.set(w2, Paths.get("w2"));
        
        l.setExpected(Map.entry("simSpeed", SimulationSpeed.SPEED5));
        w2.setTickFactor(SimulationSpeed.SPEED5);
        
        l.checkFinished();
    }
}
