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
        void changed(World oldWorld, World newWorld);
    }
    
    void addChangeListenerx(ChangeListener listener);
    void removeChangeListenerx(ChangeListener listener);
    
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
            public void addChangeListenerx(ChangeListener listener)
            {
                //not needed since it can't change
            }

            @Override
            public void removeChangeListenerx(ChangeListener listener)
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
            public void addChangeListenerx(ChangeListener listener)
            {
                source.get().addChangeListenerx(listener);
            }

            @Override
            public void removeChangeListenerx(ChangeListener listener)
            {
                source.get().removeChangeListenerx(listener);
            }
        };
    }
}
