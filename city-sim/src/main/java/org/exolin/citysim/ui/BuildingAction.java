package org.exolin.citysim.ui;

import org.exolin.citysim.model.BuildingType;

/**
 *
 * @author Thomas
 */
public interface BuildingAction extends Action
{
    BuildingType getBuilding();
}
