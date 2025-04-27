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

    public static <T> T random(List<T> list)
    {
        return list.get((int)(Math.random() * list.size()));
    }
}
