package org.exolin.citysim.ui.budget;

import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.ab.BuildingType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public interface BudgetCategory
{
    String getTitle();
    
    static BudgetCategory getFor(StructureType type)
    {
        if(type instanceof BuildingType a)
        {
            ZoneType zoneType = a.getZoneType();
            return new ZoneCategory(zoneType);
        }
        else if(type instanceof SelfConnectionType s)
            return new StreetCategory(s);
        else if(type instanceof TreeType)
            return null;
        else if(type instanceof ZoneType)
            return null;
        else
            throw new IllegalArgumentException();
    }
    
    boolean isIncome();
}
