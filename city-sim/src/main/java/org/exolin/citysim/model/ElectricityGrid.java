package org.exolin.citysim.model;

import java.util.HashSet;
import java.util.Set;
import org.exolin.citysim.bt.buildings.Plants;
import org.exolin.citysim.model.building.Building;

/**
 *
 * @author Thomas
 */
public class ElectricityGrid
{
    private final Set<ElectricityGridArea> areas = new HashSet<>();
    private final Set<Building> plants = new HashSet<>();

    public ElectricityGrid(Building plant)
    {
        if(!Plants.isPlant(plant))
            throw new IllegalArgumentException();
    }

    public int getSupply()
    {
        return plants.stream()
                .mapToInt(Plants::getMegaWatt)
                .sum();
    }

    /**
     * Merges two grids and returns the result.
     * 
     * @param other
     * @return 
     */
    public ElectricityGrid merge(ElectricityGrid other)
    {
        //merge plants
        plants.addAll(other.plants);
        //merge areas
        areas.addAll(other.areas);

        //set grid to this
        for(ElectricityGridArea o : other.areas)
            o.setElectricityGrid(this);

        return this;
    }
}
