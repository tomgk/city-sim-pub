package org.exolin.citysim.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class WorldListenerAdapterTest
{
    @Test
    public void test()
    {
        World w = new World("test", 30, BigDecimal.ONE, SimulationSpeed.SPEED1);
        WorldListenerAdapter l = new WorldListenerAdapter(new WorldListener(){});
        w.addListener(l);
        w.triggerAllChanges(l);
    }
}
