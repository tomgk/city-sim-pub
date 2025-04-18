package org.exolin.citysim.ui.actions;

import java.awt.Image;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public interface BuildingAction extends Action
{
    StructureType getBuilding();
    
    default Image getIcon()
    {
        StructureType building = getBuilding();
        StructureVariant variant = getVariant();
        return variant != null ? building.getImage(variant).getDefault() : building.getDefaultImage();
    }
    
    default String getSubtext()
    {
        return null;
    }
    default StructureVariant getVariant()
    {
        return null;
    }
}
