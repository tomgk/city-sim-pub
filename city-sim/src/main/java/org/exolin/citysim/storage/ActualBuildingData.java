package org.exolin.citysim.storage;

import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class ActualBuildingData extends BuildingData
{
    public ActualBuildingData(ActualBuilding b)
    {
        super(b);
    }

    @Override
    public Class<? extends Enum<?>> getVariantClass()
    {
        return ActualBuildingType.Variant.class;
    }
    
    
}
