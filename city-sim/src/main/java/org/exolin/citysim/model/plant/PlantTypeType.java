package org.exolin.citysim.model.plant;

import java.util.Objects;

/**
 *
 * @author Thomas
 */
public enum PlantTypeType
{
    TREE("trees", 3),
    GRASS("grass", 1);
    
    private final int cost;
    private final String baseName;

    private PlantTypeType(String baseName, int cost)
    {
        this.baseName = Objects.requireNonNull(baseName);
        this.cost = cost;
    }
    
    public String baseName()
    {
        return baseName;
    }

    public int getCost()
    {
        return cost;
    }
}
