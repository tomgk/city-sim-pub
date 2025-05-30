package org.exolin.citysim.bt.connections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class SelfConnectionsTest
{
    @Test
    public void testCount()
    {
        assertEquals(3, SelfConnections.values().size());
    }
}
