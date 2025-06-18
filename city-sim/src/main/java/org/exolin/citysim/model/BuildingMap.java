package org.exolin.citysim.model;

import java.util.List;

/**
 *
 * @author Thomas
 */
public interface BuildingMap
{
    public Structure<?, ?, ?, ?> getBuildingAt(int x, int y);
    public List<Structure<?, ?, ?, ?>> getStructures();
}
