package org.exolin.citysim.ui.budget;

import org.exolin.citysim.model.ActualBuildingType;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.TreeType;
import org.exolin.citysim.model.ZoneType;
import org.exolin.citysim.model.street.StreetType;

/**
 *
 * @author Thomas
 */
public interface BudgetCategory
{
    String getTitle();
    
    static BudgetCategory getFor(BuildingType type)
    {
        if(type instanceof ActualBuildingType a)
        {
            ZoneType zoneType = a.getZoneType();
            return new ZoneCategory(zoneType);
        }
        else if(type instanceof StreetType s)
            return new StreetCategory(s);
        else if(type instanceof TreeType)
            return null;
        else if(type instanceof ZoneType)
            return null;
        else
            throw new IllegalArgumentException();
    }
}
