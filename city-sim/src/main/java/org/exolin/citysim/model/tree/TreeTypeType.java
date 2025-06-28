package org.exolin.citysim.model.tree;

import java.util.Objects;

/**
 *
 * @author Thomas
 */
public enum TreeTypeType
{
    TREE("trees"),
    GRASS("grass");
    
    private final String baseName;

    private TreeTypeType(String baseName)
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
