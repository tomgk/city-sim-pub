package org.exolin.citysim.model.electricity;

import java.util.Objects;
import org.exolin.citysim.model.building.Building;

/**
 * All ElectricityGridArea that are connected form one ElectricityGrid 
 * @author Thomas
*/
public class ElectricityGridArea
{
    private ElectricityGrid electricityGrid;

    public ElectricityGridArea(Building plant)
    {
        electricityGrid = new ElectricityGrid(plant);
    }

    public ElectricityGrid getElectricityGrid()
    {
        return electricityGrid;
    }
    
    void setElectricityGrid(ElectricityGrid electricityGrid)
    {
        Objects.requireNonNull(electricityGrid);
        this.electricityGrid = electricityGrid;
    }
    
    void connectTo(ElectricityGridArea other)
    {
        Objects.requireNonNull(other);
        electricityGrid = electricityGrid.merge(other.electricityGrid);
    }

    @Override
    public String toString()
    {
        return ElectricityGridArea.class.getName()+"["+Integer.toHexString(hashCode())+"]";
    }
}
