package org.exolin.citysim.ui;

import java.awt.Image;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;

/**
 *
 * @author Thomas
 */
public interface BuildingAction extends Action
{
    BuildingType getBuilding();
    
    default Image getIcon()
    {
        BuildingType building = getBuilding();
        BuildingVariant variant = getVariant();
        return variant != null ? building.getImage(variant).getDefault() : building.getDefaultImage();
    }
    
    default String getSubtext()
    {
        return null;
    }
    default BuildingVariant getVariant()
    {
        return null;
    }
}
