package org.exolin.citysim.model.electricity;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import org.exolin.citysim.bt.buildings.PowerPlants;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.building.Building;

/**
 *
 * @author Thomas
 */
public class ElectricityGrid
{
    private final Set<ElectricityGridArea> areas = new HashSet<>();
    private final Set<Building> plants = new TreeSet<>(comparePlants());
    private final Set<Structure<?, ?, ?, ?>> structures = new LinkedHashSet<>();

    public ElectricityGrid(Building plant)
    {
        if(!PowerPlants.isPlant(plant))
            throw new IllegalArgumentException();
        
        this.plants.add(plant);
    }
    
    public void addStructure(Structure<?, ?, ?, ?> s)
    {
        if(!structures.add(s))
            throw new IllegalArgumentException("duplicate");
    }

    public Set<Structure<?, ?, ?, ?>> getStructures()
    {
        return Collections.unmodifiableSet(structures);
    }

    public int getSupply()
    {
        return plants.stream()
                .mapToInt(PowerPlants::getMegaWatt)
                .sum();
    }

    public Set<ElectricityGridArea> getAreas()
    {
        return Collections.unmodifiableSet(areas);
    }

    public Set<Building> getPlants()
    {
        return Collections.unmodifiableSet(plants);
    }
    
    private static Comparator<Building> comparePlants()
    {
        return Comparator.comparing(b -> {
            return b.getX()+"-"+b.getY();
        });
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
        //merge structures
        structures.addAll(other.structures);

        //set grid to this
        for(ElectricityGridArea o : other.areas)
            o.setElectricityGrid(this);

        return this;
    }
}
