package org.exolin.citysim.ui.actions;

import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class PlaceFire extends PlaceStructure
{
    public PlaceFire(GetWorld world)
    {
        super(world);
    }

    @Override
    protected void addBuilding(World w, int x, int y)
    {
        w.addBuilding(FireType.fire, x, y, FireType.Variant.random(), new FireParameters(10000));//TODO: constant
    }

    @Override
    public StructureType getBuilding()
    {
        return FireType.fire;
    }

    @Override
    public int getCost()
    {
        return 0;
    }
}
