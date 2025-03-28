package org.exolin.citysim.bt;

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
            Streets.class,
            Zones.class
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
