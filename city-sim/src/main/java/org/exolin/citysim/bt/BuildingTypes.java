package org.exolin.citysim.bt;

import org.exolin.citysim.model.fire.FireType;

/**
 *
 * @author Thomas
 */
public class BuildingTypes
{
    public static void init()
    {
        Class<?> classes[] = {
            Buildings.class,
            BusinessBuildings.class,
            IndustrialBuildings.class,
            ResidentialBuildings.class,
            SelfConnections.class,
            CrossConnections.class,
            Zones.class,
            Trees.class,
            Plants.class,
            FireType.class,
            Vacants.class,
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
}
