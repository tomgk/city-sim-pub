package org.exolin.citysim.ui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public final class WorldHolder implements GetWorld
{
    private World world;
    private Path file;
    private final List<ChangeListener> listeners = new ArrayList<>();

    public WorldHolder(World world)
    {
        this.world = world;
    }

    @Override
    public World get()
    {
        return world;
    }
    
    public void set(World world, Path file)
    {
        Objects.requireNonNull(world);
        //worldFile can be null
        
        this.world = world;
        this.file = file;
        
        for(ChangeListener l: listeners)
            l.changed(world);
    }

    public Path getFile()
    {
        return file;
    }

    public void setFile(Path file)
    {
        this.file = file;
    }

    @Override
    public void addChangeListener(ChangeListener listener)
    {
        listeners.add(Objects.requireNonNull(listener));
    }

    @Override
    public void removeChangeListener(ChangeListener listener)
    {
        listeners.remove(listener);
    }
}
