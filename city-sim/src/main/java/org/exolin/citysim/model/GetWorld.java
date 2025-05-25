package org.exolin.citysim.model;

import java.util.function.Supplier;

/**
 * Allows accessing the current world.
 * Necessary, so that the world can be replaced
 * without searching every use of {@link World}.
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
    
    interface ChangeListener
    {
        void changed(World newWorld);
    }
    
    void addChangeListener(ChangeListener listener);
    void removeChangeListener(ChangeListener listener);
    
    /**
     * Creates an instance where the world stays the same.
     * 
     * @param w world
     * @return static
     */
    static GetWorld ofStatic(World w)
    {
        return new GetWorld()
        {
            @Override
            public World get()
            {
                return w;
            }

            @Override
            public void addChangeListener(ChangeListener listener)
            {
                //not needed since it can't change
            }

            @Override
            public void removeChangeListener(ChangeListener listener)
            {
                //not needed since it can't change
            }
        };
    }
    
    static GetWorld delegate(Supplier<GetWorld> source)
    {
        return new GetWorld()
        {
            @Override
            public World get()
            {
                return source.get().get();
            }

            @Override
            public void addChangeListener(ChangeListener listener)
            {
                source.get().addChangeListener(listener);
            }

            @Override
            public void removeChangeListener(ChangeListener listener)
            {
                source.get().removeChangeListener(listener);
            }
        };
    }
}
