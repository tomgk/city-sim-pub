package org.exolin.citysim.model;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.List;
import static org.exolin.citysim.bt.BusinessBuildings.car_cinema;
import static org.exolin.citysim.bt.BusinessBuildings.cinema;
import static org.exolin.citysim.bt.BusinessBuildings.office;
import static org.exolin.citysim.bt.BusinessBuildings.office2;
import static org.exolin.citysim.bt.BusinessBuildings.office3;
import static org.exolin.citysim.bt.BusinessBuildings.parkbuilding;
import static org.exolin.citysim.bt.Streets.street;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.ZonePlacement;

/**
 *
 * @author Thomas
 */
public class Worlds
{
    private static final int DEFAULT_GRID_SIZE = 30;
    private static final BigDecimal DEFAULT_MONEY = BigDecimal.valueOf(100000);
    
    public static World World1()
    {
        World w = new World("World1", DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        //w.enableOverlap();
        w.addBuilding(office, 6, 3);
        w.addBuilding(office, 9, 12);
        w.addBuilding(car_cinema, 18, 18);
        w.addBuilding(cinema, 18, 21);
        w.addBuilding(office2, 18, 24);
        w.addBuilding(parkbuilding, 18, 27);
        w.addBuilding(office3, 15, 27);
        for(int i=0;i<3;++i)
            w.addBuilding(street, 2+i, 2, CONNECT_X);
        
        w.addBuilding(office, 0, 0);
        
        w.disableOverlap();
        return w;
    }

    public static World World2()
    {
        World w = new World("World2", DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        w.enableOverlap();
        
        w.addBuilding(office, 6, 6);
        w.addBuilding(parkbuilding, 3, 6);
        
        w.addBuilding(office3, 6, 2);
        
        w.addBuilding(car_cinema, 16, 6);
        w.addBuilding(cinema, 16, 2);
        w.addBuilding(office2, 19, 2);
        
        for(int i=0;i<25;++i)
            w.addBuilding(street, 2+i, 5, CONNECT_X);
        
        for(int i=0;i<4;++i)
            w.addBuilding(street, 20, 6+i, CONNECT_Y);
        
        w.disableOverlap();
        return w;
    }
    
    public static World World3()
    {
        World w = new World("World3", DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        //w.enableOverlap();
        
        GetWorld getWorld = GetWorld.ofStatic(w);
        
        placeZone(w, Zones.zone_residential, ZoneType.Variant.DEFAULT, 0, 0, 20, 15);
        placeZone(w, Zones.zone_industrial, ZoneType.Variant.DEFAULT, 0, 15, 20, 15);
        placeZone(w, Zones.zone_business, ZoneType.Variant.DEFAULT, 20, 0, 10, 30);
        
        int zoneSize = 6;
        
        for(int y = zoneSize/2;y<DEFAULT_GRID_SIZE-2;y+=zoneSize)
            placeStreet(w, 0, y, DEFAULT_GRID_SIZE-1, y);
        
        for(int x = zoneSize/2;x<DEFAULT_GRID_SIZE-2;x+=zoneSize*3/2)
            placeStreet(w, x, 0, x, DEFAULT_GRID_SIZE-1);
        
        return w;
    }
    
    private static void placeZone(World w, ZoneType type, ZoneType.Variant variant, int x, int y, int width, int height)
    {
        ZonePlacement zonePlacement = new ZonePlacement(GetWorld.ofStatic(w), type, variant);
        place(zonePlacement, x, y, width, height);
    }
    
    private static void placeStreet(World w, int x, int y, int width, int height)
    {
        StreetBuilder sb = new StreetBuilder(GetWorld.ofStatic(w), street);
        place(sb, x, y, width, height);
    }
    
    private static void place(Action a, int x, int y, int width, int height)
    {
        Point start = new Point(x, y);
        Point end = new Point(x+width, y+height);
        
        a.mouseDown(start);
        a.moveMouse(end);
        a.releaseMouse(end);
    }

    public static List<World> all()
    {
        return List.of(World1(), World2(), World3());
    }
}
