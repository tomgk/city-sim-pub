package org.exolin.citysim.utils;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 *
 * @author Thomas
 */
public class MathUtils
{
    public static int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static int lcm(int a, int b)
    {
        return a * (b / gcd(a, b));
    }
    
    private static final Supplier<IllegalArgumentException> NO_VALUES =
            () -> new IllegalArgumentException("No values");
    
    public static int gcd(IntStream values)
    {
        return values.reduce(MathUtils::gcd).orElseThrow(NO_VALUES);
    }
    
    public static int lcm(IntStream values)
    {
        return values.reduce(MathUtils::lcm).orElseThrow(NO_VALUES);
    }
}
