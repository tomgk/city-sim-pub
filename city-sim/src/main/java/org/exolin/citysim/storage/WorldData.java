package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
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
    private final BigDecimal money;
    
    @JsonProperty
    private final List<BuildingData> buildings;

    @JsonCreator
    public WorldData(@JsonProperty("gridSize") int gridSize,
            @JsonProperty("money") BigDecimal money,
            @JsonProperty("buildings") List<BuildingData> buildings)
    {
        this.gridSize = gridSize;
        this.money = money;
        this.buildings = buildings;
    }

    public WorldData(World world)
    {
        this.buildings = world.getBuildings()
                .stream()
                .map(BuildingData::create)
                .toList();
        this.money = world.getMoney();
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

    public BigDecimal getMoney()
    {
        return money;
    }
    
    public World createWorld(String name)
    {
        World w = new World(name, gridSize, money);
        
        for(BuildingData bd : buildings)
            bd.createBuilding(w);
        
        return w;
    }
}
