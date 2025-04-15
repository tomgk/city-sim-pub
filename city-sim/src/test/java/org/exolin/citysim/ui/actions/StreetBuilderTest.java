package org.exolin.citysim.ui.actions;

import java.awt.Point;
import java.math.BigDecimal;
import org.exolin.citysim.Main;
import static org.exolin.citysim.bt.Streets.rail;
import static org.exolin.citysim.bt.Streets.street;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StreetBuilderTest
{
    @Test
    public void testCrossing()
    {
        World w = new World("test", 30, BigDecimal.ONE);
        StreetBuilder streetBuilder = new StreetBuilder(GetWorld.ofStatic(w), street, StreetBuilder.ONLY_LINE);
        StreetBuilder railBuilder = new StreetBuilder(GetWorld.ofStatic(w), rail, StreetBuilder.ONLY_LINE);
        
        streetBuilder.mouseDown(new Point(5, 0));
        streetBuilder.moveMouse(new Point(5, 10));
        streetBuilder.mouseReleased(new Point(5, 10));
        
        railBuilder.mouseDown(new Point(0, 5));
        railBuilder.moveMouse(new Point(10, 5));
        railBuilder.mouseReleased(new Point(10, 5));
        
        Main.play(w);
    }
    
    public static void main(String[] args)
    {
        StreetBuilderTest t = new StreetBuilderTest();
        t.testCrossing();
    }
}
