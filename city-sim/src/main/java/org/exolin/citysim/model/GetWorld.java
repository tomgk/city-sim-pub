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
    
    void addChangeListener(ChangeListener listener);
    void removeChangeListener(ChangeListener listener);
    
    void addWorldListener(GenericWorldListener listener);
    void removeWorldListener(GenericWorldListener listener);
    
    default void addWorldListener(WorldListener listener)
    {
        addWorldListener(new WorldListenerAdapter(listener));
    }
    default void removeWorldListener(WorldListener listener)
    {
        removeWorldListener(new WorldListenerAdapter(listener));
    }
    
    /**
     * Creates an instance where the world stays the same.
     * 
     * @param w world
     * @return static
     */
    static GetWorld ofStatic(World w)
    {
        return new StaticGetWorld(w);
    }
    
    static GetWorld delegate(Supplier<GetWorld> source)
    {
        return new DelegateGetWorld(source);
    }
}

/**
 * Implementation that uses only one {@link World} instance that can't be changed
 */
class StaticGetWorld implements GetWorld
{
    private final World w;

    public StaticGetWorld(World w)
    {
        this.w = w;
    }
    
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

    @Override
    public void addWorldListener(GenericWorldListener listener)
    {
        //just delegate since it can't change
        w.addListener(listener);
    }

    @Override
    public void removeWorldListener(GenericWorldListener listener)
    {
        //just delegate since it can't change
        w.removeListener(listener);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"[world="+w.getName()+"]";
    }
}

/**
 * Implemenetation that delegates to another {@link GetWorld}
 */
class DelegateGetWorld implements GetWorld
{
    private final Supplier<GetWorld> source;

    public DelegateGetWorld(Supplier<GetWorld> source)
    {
        this.source = source;
    }
    
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

    @Override
    public void addWorldListener(GenericWorldListener listener)
    {
        source.get().addWorldListener(listener);
    }

    @Override
    public void removeWorldListener(GenericWorldListener listener)
    {
        source.get().removeWorldListener(listener);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName()+"[source="+source+"]";
    }
}