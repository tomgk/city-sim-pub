package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public interface GetWorld
{
    World get();
    
    static GetWorld ofStatic(World w)
    {
        return () -> w;
    }
}
