package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public class Github
{
    /**
     * @return {@code true} if executed in a Github Actions environment, {@code false} otherwise
     */
    public static boolean inActions()
    {
        return System.getenv("GITHUB_ACTION") != null;
    }
}
