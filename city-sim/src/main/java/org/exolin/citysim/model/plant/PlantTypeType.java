package org.exolin.citysim.model.plant;

import java.util.Objects;

/**
 *
 * @author Thomas
 */
public enum PlantTypeType
{
    TREE("trees"),
    GRASS("grass");
    
    private final String baseName;

    private PlantTypeType(String baseName)
    {
        this.baseName = Objects.requireNonNull(baseName);
    }
    
    public boolean isGrass()
    {
        return this == GRASS;
    }
    
    public String baseName()
    {
        return baseName;
    }
}
