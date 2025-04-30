package org.exolin.citysim.ui.actions;

import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.vacant.VacantParameters;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class PlaceVacant extends PlaceStructure
{
    private final VacantType vacantType;
    private final ZoneType zoneType;

    public PlaceVacant(GetWorld world, VacantType vacantType, ZoneType zoneType)
    {
        super(world);
        this.vacantType = vacantType;
        this.zoneType = zoneType;
    }

    @Override
    protected void addBuilding(World w, int x, int y)
    {
        w.addBuilding(vacantType, x, y, VacantType.Variant.DEFAULT, new VacantParameters(zoneType));
        w.reduceMoney(0);
    }

    @Override
    public StructureType getBuilding()
    {
        return vacantType;
    }

    @Override
    public int getCost()
    {
        return 0;
    }
}
