package org.exolin.citysim.utils;

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
    
    public static void main(String[] args)
    {
        System.out.println(gcd(15, 25));
        System.out.println(lcm(15, 25));
    }
}
