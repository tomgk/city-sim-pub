package org.exolin.citysim.model.electricity;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.buildings.PowerPlants;
import org.exolin.citysim.model.BuildingMap;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.zone.Zone;

/**
 *
 * @author Thomas
 */
public class Electricity
{
    private final Map<Structure<?, ?, ?, ?>, ElectricityGridArea> structuresWithElectricity = new IdentityHashMap<>();

    public Map<Structure<?, ?, ?, ?>, ElectricityGridArea> getStructuresWithElectricity()
    {
        return Collections.unmodifiableMap(structuresWithElectricity);
    }
    
    public Map<ElectricityGrid, List<Structure<?, ?, ?, ?>>> getElectricityGrids()
    {
        return structuresWithElectricity.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        (Entry<Structure<?, ?, ?, ?>, ElectricityGridArea> e) -> e.getValue().getElectricityGrid(),
                        Collectors.mapping((Entry<Structure<?, ?, ?, ?>, ElectricityGridArea> e) -> e.getKey(), Collectors.toList())));
    }
    
    private void updateElectricityGrid(BuildingMap w)
    {
        structuresWithElectricity.clear();
        
        w.getStructures().stream()
                .filter(PowerPlants::isPlant)
                .forEach(plant -> {
                    //each plant starts as an own grid
                    ElectricityGridArea grid = new ElectricityGridArea((Building)plant);
                    onFindStructureWithElectricity(w, grid, plant);
                });
        
        //System.out.println(structures);
    }
    
    private void onFindStructureWithElectricity(BuildingMap w, ElectricityGridArea grid, Structure<?, ?, ?, ?> s)
    {
        Objects.requireNonNull(s);
        
        //remember, and if already captured, skip
        ElectricityGridArea existingGrid = structuresWithElectricity.get(s);
        if(existingGrid != null)
        {
            grid.connectTo(existingGrid);
            
            return;
        }
        
        structuresWithElectricity.put(s, grid);
        grid.getElectricityGrid().addStructure(s);
        
        int x = s.getX();
        int y = s.getY();
        int size = s.getSize().toInteger();
        for(int yi=-1;yi<size+1;++yi)
        {
            for(int xi=-1;xi<size+1;++xi)
            {
                //exclude diagonal
                if(xi != 0 && yi != 0)
                    continue;
                
                ConnectionType.Direction d = xi != 0 ? ConnectionType.Direction.X : ConnectionType.Direction.Y;
                
                Structure<?, ?, ?, ?> neighbor = w. getBuildingAt(x+xi, y+yi);
                
                //check if it is actually the current building
                if(neighbor == s)
                    continue;
                
                //TODO: conduction over street should only work with one tile
                //TODO: circuit over street doesn't work
                if(neighbor != null && PowerPlants.getElectricity(neighbor, d).transfers())
                    onFindStructureWithElectricity(w, grid, neighbor);
            }
        }
    }
    
    record Coverage(int covered, int total)
    {
        Coverage merge(Coverage c)
        {
            return new Coverage(covered+c.covered, total+c.total);
        }
        
        String getPercentage()
        {
            if(total == 0)
                return "--/--";
            
            return covered+"/"+total;
        }
    }
    
    private static final Coverage EMPTY_COVERAGE = new Coverage(0, 0);
    private Coverage currentCoverage = EMPTY_COVERAGE;
    
    private void updateElectricityCoverageStats(BuildingMap w, Consumer<String> electricityCoverChange)
    {
        Coverage previous = currentCoverage;
        
        currentCoverage = w.getStructures().stream()
                .filter(f -> f instanceof Building || f instanceof Zone || f.getZoneType(true).isPresent())
                .map(s -> {
                    boolean covered = hasElectricity(s);
                    int size = s.getSize().toInteger();
                    return new Coverage(covered ? size : 0, size);
                })
                .reduce(Coverage::merge)
                .orElse(EMPTY_COVERAGE);
        
        if(!previous.equals(currentCoverage))
            electricityCoverChange.accept(getElectricityCoverage());
    }
    
    public void updateStats(BuildingMap w, Consumer<String> electricityCoverChange)
    {
        updateElectricityGrid(w);
        updateElectricityCoverageStats(w, electricityCoverChange);
    }
    
    public String getElectricityCoverage()
    {
        return currentCoverage.getPercentage();
    }
    
    public boolean hasElectricity(Structure<?, ?, ?, ?> s)
    {
        return structuresWithElectricity.containsKey(s);
    }
}
