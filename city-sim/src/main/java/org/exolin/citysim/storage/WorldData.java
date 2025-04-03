package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.exolin.citysim.model.World;

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

    @JsonCreator
    public WorldData(@JsonProperty("gridSize") int gridSize, @JsonProperty("buildings") List<BuildingData> buildings)
    {
        this.gridSize = gridSize;
        this.buildings = buildings;
    }

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

    public World createWorld()
    {
        World w = new World(gridSize);
        
        for(BuildingData bd : buildings)
            bd.createBuilding(w);
        
        return w;
    }
}
