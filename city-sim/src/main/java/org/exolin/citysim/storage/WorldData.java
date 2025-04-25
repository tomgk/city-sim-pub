package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.exolin.citysim.model.SimulationSpeed;
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
    private final List<StructureData> buildings;
    
    @JsonProperty
    private final String speed;

    @JsonCreator
    public WorldData(@JsonProperty("gridSize") int gridSize,
            @JsonProperty("money") BigDecimal money,
            @JsonProperty("buildings") List<StructureData> buildings,
            @JsonProperty("speed") String speed)
    {
        this.gridSize = gridSize;
        this.money = money;
        this.buildings = buildings;
        this.speed = speed;
    }

    public WorldData(World world)
    {
        this.buildings = world.getBuildings()
                .stream()
                .map(StructureData::create)
                .toList();
        this.money = world.getMoney();
        this.gridSize = world.getGridSize();
        this.speed = world.getTickFactor().name().toLowerCase();
    }

    public List<StructureData> getBuildings()
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
        World w = new World(name, gridSize, money, SimulationSpeed.valueOf(speed.toUpperCase()));
        
        for(StructureData bd : buildings)
            bd.createBuilding(w);
        
        return w;
    }
}
