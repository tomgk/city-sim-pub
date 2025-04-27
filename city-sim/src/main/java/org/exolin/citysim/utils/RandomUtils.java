package org.exolin.citysim.utils;

import java.util.List;

/**
 *
 * @author Thomas
 */
public class RandomUtils
{
    public static double random()
    {
        return Math.random();
    }
    
    public static int randInt(int max)
    {
        return (int)(random() * max);
    }

    public static <T> T random(List<T> list)
    {
        return list.get(randInt(list.size()));
    }

    public static boolean atLeast(double value)
    {
        return random() < value;
    }
}
