package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public class Github
{
    public static boolean inActions()
    {
        return System.getenv("GITHUB_ACTION") != null;
    }
}
