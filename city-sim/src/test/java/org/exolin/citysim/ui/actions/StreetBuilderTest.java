package org.exolin.citysim.ui.actions;

import java.awt.Point;
import java.io.IOException;
import java.math.BigDecimal;
import org.exolin.citysim.Main;
import static org.exolin.citysim.bt.SelfConnections.rail;
import static org.exolin.citysim.bt.SelfConnections.street;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.SimulationSpeed;
import org.exolin.citysim.model.World;
import org.exolin.citysim.storage.WorldStorage;
import static org.exolin.citysim.storage.WorldStorageTest.serialize;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Thomas
 */
public class StreetBuilderTest
{
    @Test
    public World testCrossing() throws IOException
    {
        World w = new World("test", 30, BigDecimal.ONE, SimulationSpeed.PAUSED);
        StreetBuilder streetBuilder = new StreetBuilder(GetWorld.ofStatic(w), street, StreetBuilder.ONLY_LINE);
        StreetBuilder railBuilder = new StreetBuilder(GetWorld.ofStatic(w), rail, StreetBuilder.ONLY_LINE);
        
        streetBuilder.mouseDown(new Point(5, 0));
        streetBuilder.moveMouse(new Point(5, 10));
        streetBuilder.mouseReleased(new Point(5, 10));
        
        railBuilder.mouseDown(new Point(0, 5));
        railBuilder.moveMouse(new Point(10, 5));
        railBuilder.mouseReleased(new Point(10, 5));
        
        String output = serialize(WorldStorage::serialize, w);
        String expected = """
                          {
                            "gridSize" : 30,
                            "money" : -384,
                            "buildings" : [ {
                              "type" : "street",
                              "x" : 5,
                              "y" : 0,
                              "variant" : "east"
                            }, {
                              "type" : "rail",
                              "x" : 0,
                              "y" : 5,
                              "variant" : "south"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 1,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 1,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 2,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 2,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 3,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 3,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 4,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 4,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "rail_street",
                              "x" : 5,
                              "y" : 5
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 6,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 6,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 7,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 7,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 8,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 8,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 9,
                              "variant" : "connect_y"
                            }, {
                              "type" : "rail",
                              "x" : 9,
                              "y" : 5,
                              "variant" : "connect_x"
                            }, {
                              "type" : "street",
                              "x" : 5,
                              "y" : 10,
                              "variant" : "west"
                            }, {
                              "type" : "rail",
                              "x" : 10,
                              "y" : 5,
                              "variant" : "north"
                            } ]
                          }
                          """;
        JSONAssert.assertEquals(expected, output, false);
        
        return w;
    }
    
    public static void main(String[] args) throws IOException
    {
        StreetBuilderTest t = new StreetBuilderTest();
        Main.play(t.testCrossing());
    }
}
