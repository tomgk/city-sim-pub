package org.exolin.citysim.ui.actions;

import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.fire.Fire;
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
        Structure<?, ?, ?, ?> b = w.getBuildingAt(x, y);
        if(b == null)
            Fire.placeFire(w, x, y);
        else
            Fire.replaceWithFire(w, b);
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
