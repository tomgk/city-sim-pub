package org.exolin.citysim.bt;

/**
 *
 * @author Thomas
 */
public class BuildingTypes
{
    public static void init()
    {
        Buildings.init();
        BusinessBuildings.init();
        IndustrialBuildings.init();
        ResidentialBuildings.init();
        Streets.init();
        Zones.init();
    }
}
