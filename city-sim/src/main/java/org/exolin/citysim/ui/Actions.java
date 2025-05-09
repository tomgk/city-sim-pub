package org.exolin.citysim.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.PlaceBuilding;
import org.exolin.citysim.ui.actions.PlaceFire;
import org.exolin.citysim.ui.actions.PlaceTrees;
import org.exolin.citysim.ui.actions.PlaceVacant;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.TearDownAction;
import org.exolin.citysim.ui.actions.ZonePlacement;

/**
 *
 * @author Thomas
 */
public class Actions
{
    private static void addZone(GetWorld getWorld, List<Action> zoneActions, ZoneType zoneType)
    {
        zoneActions.add(new ZonePlacement(getWorld, zoneType, ZoneType.Variant.DEFAULT));
    }
    
    public static Map<String, List<Action>> getActions(GetWorld getWorld)
    {
        Map<String, List<Action>> actions = new LinkedHashMap<>();
        
        {
            List<Action> sactions = new ArrayList<>();
            sactions.add(Action.NONE);
            sactions.add(TearDownAction.createTearDown(getWorld));
            sactions.add(new PlaceTrees(getWorld, false));
            sactions.add(new PlaceFire(getWorld));
            actions.put("Special", sactions);
        }
        
        {
            List<Action> sactions = new ArrayList<>();
            sactions.add(new StreetBuilder(getWorld, street, true));
            sactions.add(new StreetBuilder(getWorld, rail, true));
            sactions.add(new StreetBuilder(getWorld, water, false));
            actions.put("Infrastructure", sactions);
        }
        
        {
            List<Action> zoneActions = new ArrayList<>();
            zoneActions.add(TearDownAction.createDezoning(getWorld));
            addZone(getWorld, zoneActions, Zones.residential);
            addZone(getWorld, zoneActions, Zones.low_residential);
            addZone(getWorld, zoneActions, Zones.business);
            addZone(getWorld, zoneActions, Zones.low_business);
            addZone(getWorld, zoneActions, Zones.industrial);
            addZone(getWorld, zoneActions, Zones.low_industrial);
            actions.put("Zones", zoneActions);
        }
        
        //actions.add(new PlaceBuilding(World.office));
        
        for(BuildingType type : StructureType.actualBuildingTypes())
        {
            String categoryName = type.getZoneType() != null ? type.getZoneType().getTitle(): "Special buildings";
            
            if(!actions.containsKey(categoryName))
                actions.put(categoryName, new ArrayList<>());
            
            actions.get(categoryName).add(new PlaceBuilding(getWorld, type));
        }
        
        for(VacantType type : VacantType.vacantTypes())
        {
            for(ZoneType zoneType : ZoneType.types(ZoneType.class))
            {
                String categoryName = zoneType.getTitle();

                if(!actions.containsKey(categoryName))
                    actions.put(categoryName, new ArrayList<>());

                actions.get(categoryName).add(new PlaceVacant(getWorld, type, zoneType));
            }
        }
        
        return actions;
    }
}
