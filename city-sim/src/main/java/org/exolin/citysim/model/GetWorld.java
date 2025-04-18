package org.exolin.citysim.model;

/**
 * Allows accessing the current world
 *
 * @author Thomas
 */
public interface GetWorld
{
    /**
     * Return the current world
     * @return current world
     */
    World get();
    
    /**
     * Creates an instance where the world stays the same.
     * 
     * @param w world
     * @return static
     */
    static GetWorld ofStatic(World w)
    {
        return () -> w;
    }
}
