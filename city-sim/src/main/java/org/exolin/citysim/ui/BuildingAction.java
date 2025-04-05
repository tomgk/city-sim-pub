package org.exolin.citysim.ui;

import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 */
public interface BuildingAction extends Action
{
    BuildingType getBuilding();
    default String getSubtext()
    {
        return null;
    }
    default BuildingVariant getVariant()
    {
        return null;
    }
}
