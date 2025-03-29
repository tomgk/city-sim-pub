package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class WorldData
{
    @JsonProperty
    private final int gridSize;
    
    @JsonProperty
    private final List<BuildingData> buildings;

    public WorldData(World world)
    {
        this.buildings = world.getBuildings()
                .stream()
                .map(BuildingData::create)
                .toList();
        
        this.gridSize = world.getGridSize();
    }

    public List<BuildingData> getBuildings()
    {
        return buildings;
    }

    public int getGridSize()
    {
        return gridSize;
    }
}
