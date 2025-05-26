package org.exolin.citysim.bt.buildings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class Plants
{
    public static final BuildingType plant_solar = createPlant("Solar Power", createUnanimated("plants/plant_solar"), 4, Zones.plants, 1300, 50);
    public static final BuildingType gas_plant = createPlant("Gas Power", createAnimation("plants/gas_plant", 8), 4, Zones.plants, 2000, 50);
    public static final BuildingType oil_plant = createPlant("Oil Power", createAnimation("plants/oil_plant", 8), 4, Zones.plants, 6600, 220);
    
    public static final String MEGA_WATT = "megaWatt";
    
    private static BuildingType createPlant(String title, Animation animation, int size, ZoneType zoneType, int cost, int megaWatt)
    {
        BuildingType bt = createBuildingType(title, animation, size, zoneType, cost);
        bt.setCustom(MEGA_WATT, megaWatt);
        return bt;
    }
    
    public static boolean isPlant(Structure<?, ?, ?, ?> s)
    {
        return isPlant(s.getType());
    }
    
    public static boolean isPlant(StructureType<?, ?, ?> s)
    {
        if(s instanceof BuildingType b)
            return isPlant(b);
        else
            return false;
    }
    
    public static boolean isPlant(BuildingType b)
    {
        return b.hasCustom(MEGA_WATT);
    }
    
    public static final BuildingType pump = createBuildingType(createAnimation("water_pump/pump", 8), 1, Zones.plants, 0);
    public static final BuildingType protest = createBuildingType(createAnimation("protest/protest", 2), 1, Zones.plants, 0);
    
    public static final List<BuildingType> ALL = Collections.unmodifiableList(Arrays.asList(plant_solar, gas_plant, oil_plant));
    
    public static Electricity getElectricity(Structure<?, ?, ?, ?> b)
    {
        if(b.getZoneType(true).map(ZoneType::isUserPlaceableZone).orElse(false))
            return Electricity.NEEDS;
        else if(b instanceof Building)
            return Electricity.NEEDS;
        else if(b.getType() == street)
            return Electricity.CONDUCTS;
        else if(b.getType() == circuit)
            return Electricity.TRANSFER;
        else
            return Electricity.INSULATOR;
    }
}
