package org.exolin.citysim.bt.buildings;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.CustomKey;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureSize;
import static org.exolin.citysim.model.StructureSize._1;
import static org.exolin.citysim.model.StructureSize._4;
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
    public static final CustomKey MEGA_WATT = CustomKey.createKey("megaWatt");
    
    //TODO: maintenance
    
    /**
     * https://github.com/tomgk/city-sim-pub/issues/162
     */
    
    public static final BuildingType solar_plant = createPlant(
            "Solar Power",
            createUnanimated("plants/plant_solar"),
            _4,
            1300,
            50,
            BigDecimal.valueOf(26)
    );
    
    public static final BuildingType microwave_plant = createPlant(
            "Microwave Power",
            createUnanimated("plants/microwave_plant"),
            _4,
            28000,
            1600,
            BigDecimal.valueOf(17.5)
    );
    
    public static final BuildingType fusion_plant = createPlant(
            "Fusion Power",
            createUnanimated("plants/fusion_plant"),
            _4,
            40000,
            2500,
            BigDecimal.valueOf(16)
    );
    
    
    public static final BuildingType nuclear_plant = createPlant(
            "Nuclear Power",
            createUnanimated("plants/nuclear_plant"),
            _4,
            15000,
            500,
            BigDecimal.valueOf(30)
    );
    
    public static final BuildingType gas_plant = createPlant(
            "Gas Power",
            createAnimation("plants/gas_plant", 8),
            _4,
            2000,
            50,
            BigDecimal.valueOf(40)
    );
    
    public static final BuildingType oil_plant = createPlant(
            "Oil Power",
            createAnimation("plants/oil_plant", 8),
            _4,
            6600,
            220,
            BigDecimal.valueOf(30)
    );
    
    public static final BuildingType coal_plant = createPlant(
            "Coal Power",
            createAnimation("plants/coal_plant", 8),
            _4,
            4000,
            200,
            BigDecimal.valueOf(25)
    );
    
    public static final BuildingType wind_plant = createPlant(
            "Wind Power",
            createAnimation("plants/wind_power", 8),
            _1,
            100,
            4,
            BigDecimal.valueOf(20)
    );
    
    public static final BuildingType hydro_plant = createPlant2(
            "Hydro Power",
            createUnanimated("plants/hydro_x"),
            createUnanimated("plants/hydro_y"),
            _1,
            400,
            20,
            BigDecimal.valueOf(20)
    );
    
    public static final List<BuildingType> ALL = List.of(
            solar_plant,
            microwave_plant,
            nuclear_plant,
            fusion_plant,
            gas_plant,
            oil_plant,
            coal_plant,
            wind_plant
    );
    
    private static BuildingType createPlant2(String title, Animation animation, Animation animation2, StructureSize size, int cost, int megaWatt, BigDecimal maintenance)
    {
        BuildingType bt = createBuildingType(title, animation, animation2, size, Zones.plants, cost, maintenance);
        bt.setCustom(MEGA_WATT, megaWatt);
        return bt;
    }
    
    private static BuildingType createPlant(String title, Animation animation, StructureSize size, int cost, int megaWatt, BigDecimal maintenance)
    {
        BuildingType bt = createBuildingType(title, animation, size, Zones.plants, cost, maintenance);
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
    
    public static int getMegaWatt(StructureType<?, ?, ?> type)
    {
        if(type instanceof BuildingType bt)
            return getMegaWatt(bt);
        else
            throw new IllegalArgumentException("Not a plant: "+type.getName());
    }
    
    public static int getMegaWatt(BuildingType type)
    {
        return type.getCustom(MEGA_WATT, Integer.class);
    }
    
    public static final BuildingType pump = createBuildingType(createAnimation("water_pump/pump", 8), _1, Zones.plants, 0);
    public static final BuildingType protest = createBuildingType(createAnimation("protest/protest", 2), _1, Zones.plants, 0);
    
    public static Electricity getElectricity(SelfConnectionType type)
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
