package org.exolin.citysim.bt.buildings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import org.exolin.citysim.model.connection.Connection;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
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
    
    public static int getMegaWatt(Building b)
    {
        return getMegaWatt(b.getType());
    }
    
    public static int getMegaWatt(BuildingType type)
    {
        return type.getCustom(MEGA_WATT, Integer.class);
    }
    
    public static final BuildingType pump = createBuildingType(createAnimation("water_pump/pump", 8), 1, Zones.plants, 0);
    public static final BuildingType protest = createBuildingType(createAnimation("protest/protest", 2), 1, Zones.plants, 0);
    
    public static final List<BuildingType> ALL = Collections.unmodifiableList(Arrays.asList(plant_solar, gas_plant, oil_plant));
    
    private static Electricity getElectricity(SelfConnectionType type)
    {
        if(type == street)
            return Electricity.CONDUCTS;
        else if(type == circuit)
            return Electricity.TRANSFER;
        else
            return Electricity.INSULATOR;
    }
    
    public static Electricity getElectricity(Structure<?, ?, ?, ?> b, ConnectionType.Direction d)
    {
        Objects.requireNonNull(b);
        Objects.requireNonNull(d);
        return getElectricity0(b, d);
    }
    
    public static Electricity getAnyElectricity(Structure<?, ?, ?, ?> b)
    {
        Electricity ex = getElectricity0(b, ConnectionType.Direction.X);
        Electricity ey = getElectricity0(b, ConnectionType.Direction.Y);
        if(ConnectionType.Direction.values().length != 2)
            throw new AssertionError();
        
        return Electricity.greater(ex, ey);
    }
    
    private static Electricity getElectricity0(Structure<?, ?, ?, ?> b, ConnectionType.Direction d)
    {
        if(b.getZoneType(true).map(ZoneType::isUserPlaceableZone).orElse(false))
            return Electricity.NEEDS;
        else if(b instanceof Building)
            return Electricity.NEEDS;
        else if(b instanceof Connection<?, ?, ?, ?> c)
        {
            ConnectionType<?, ?, ?, ?> t = c.getType();
            return getElectricity(t.getTypeInDirection(d));
        }
        else
            return Electricity.INSULATOR;
    }
}
