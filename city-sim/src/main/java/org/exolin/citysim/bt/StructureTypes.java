package org.exolin.citysim.bt;

import org.exolin.citysim.bt.buildings.Buildings;
import org.exolin.citysim.bt.buildings.BusinessBuildings;
import org.exolin.citysim.bt.buildings.IndustrialBuildings;
import org.exolin.citysim.bt.buildings.PowerPlants;
import org.exolin.citysim.bt.buildings.ResidentialBuildings;
import org.exolin.citysim.bt.connections.CrossConnections;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class StructureTypes
{
    public static void init()
    {
    }
    
    static
    {
        Class<?> classes[] = {
            Buildings.class,
            BusinessBuildings.class,
            IndustrialBuildings.class,
            ResidentialBuildings.class,
            SelfConnections.class,
            CrossConnections.class,
            Zones.class,
            PowerPlants.class,
            PowerPlants.class,
            FireType.class,
            Vacants.class,
            Plants.class
        };
        
        for(Class<?> clazz: classes)
        {
            try{
                Class.forName(clazz.getName());
            }catch(ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }
    
    public static void main(String[] args)
    {
        for(StructureType t : BuildingType.types())
            System.out.println(t.getName());
    }
}
