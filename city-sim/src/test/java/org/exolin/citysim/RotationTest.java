package org.exolin.citysim;

import java.awt.Point;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class RotationTest
{
    private static Point rotate(int gridSize, int x, int y, Rotation rotation)
    {
        Point p = new Point(-1, -1);
        rotation.rotate(gridSize, x, y, p);
        return p;
    }
    
    @Test
    public void testOriginal()
    {
        int gridSize = 10;
        
        assertEquals(new Point(0, 0), rotate(gridSize, 0, 0, Rotation.ORIGINAL));
        assertEquals(new Point(0, 9), rotate(gridSize, 0, 9, Rotation.ORIGINAL));
        assertEquals(new Point(9, 0), rotate(gridSize, 9, 0, Rotation.ORIGINAL));
        assertEquals(new Point(9, 9), rotate(gridSize, 9, 9, Rotation.ORIGINAL));
    }
    
    @Test
    public void test180()
    {
        int gridSize = 10;
        
        assertEquals(new Point(9, 9), rotate(gridSize, 0, 0, Rotation.PLUS_180));
        assertEquals(new Point(9, 0), rotate(gridSize, 0, 9, Rotation.PLUS_180));
        assertEquals(new Point(0, 9), rotate(gridSize, 9, 0, Rotation.PLUS_180));
        assertEquals(new Point(0, 0), rotate(gridSize, 9, 9, Rotation.PLUS_180));
    }
    
    @Test
    public void test90()
    {
        int gridSize = 10;
        
        assertEquals(new Point(9, 0), rotate(gridSize, 0, 0, Rotation.PLUS_90));
        assertEquals(new Point(0, 0), rotate(gridSize, 0, 9, Rotation.PLUS_90));
        assertEquals(new Point(9, 9), rotate(gridSize, 9, 0, Rotation.PLUS_90));
        assertEquals(new Point(0, 9), rotate(gridSize, 9, 9, Rotation.PLUS_90));
    }
    
    @Test
    public void test270()
    {
        int gridSize = 10;
        
        assertEquals(new Point(0, 9), rotate(gridSize, 0, 0, Rotation.PLUS_270));
        assertEquals(new Point(9, 9), rotate(gridSize, 0, 9, Rotation.PLUS_270));
        assertEquals(new Point(0, 0), rotate(gridSize, 9, 0, Rotation.PLUS_270));
        assertEquals(new Point(9, 0), rotate(gridSize, 9, 9, Rotation.PLUS_270));
    }
}
