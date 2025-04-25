package org.exolin.citysim.ui.actions;

import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class PlaceBuilding extends PlaceStructure
{
    private final BuildingType type;

    public PlaceBuilding(GetWorld world, BuildingType type)
    {
        super(world);
        this.type = type;
    }
    
    @Override
    protected void addBuilding(World w, int x, int y)
    {
        w.addBuilding(type, x, y);
        w.reduceMoney(type.getCost());
    }

    @Override
    public StructureType getBuilding()
    {
        return type;
    }

    @Override
    public int getCost()
    {
        return type.getCost();
    }
}
