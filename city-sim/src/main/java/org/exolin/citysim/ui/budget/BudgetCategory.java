package org.exolin.citysim.ui.budget;

import java.util.Objects;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.ErrorDisplay;

/**
 *
 * @author Thomas
 */
public interface BudgetCategory
{
    String getTitle();
    
    static BudgetCategory getFor(StructureType type)
    {
        Objects.requireNonNull(type);
        
        if(type instanceof BuildingType a)
            return new ZoneCategory(a.getZoneType());
        else if(type instanceof SelfConnectionType s)
            return new SelfConnectionCategory(s);
        else if(type instanceof CrossConnectionType s)
            //TODO: ignores Y
            return new SelfConnectionCategory(s.getXType());
        else if(type instanceof PlantType)
            return null;
        else if(type instanceof ZoneType)
            return null;
        else if(type instanceof VacantType)
            return null;
        else
        {
            ErrorDisplay.showOneTimeError(new UnsupportedOperationException("Not supported: "+type.getClass().getName()));
            return null;
        }
    }
    
    boolean isIncome();
}
