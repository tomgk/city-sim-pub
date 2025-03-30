package org.exolin.citysim.ui;

import java.nio.file.Path;
import java.util.Objects;
import org.exolin.citysim.GetWorld;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public final class WorldHolder implements GetWorld
{
    private World world;
    private Path file;

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
    }

    public Path getFile()
    {
        return file;
    }

    public void setFile(Path file)
    {
        this.file = file;
    }
}
