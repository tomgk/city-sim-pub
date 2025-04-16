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
import static org.exolin.citysim.bt.Streets.water;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.connection.regular.ConnectVariant.CONNECT_Y;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.OutOfGridException;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.ZonePlacement;

/**
 *
 * @author Thomas
 */
public class Worlds
{
    public static final int DEFAULT_GRID_SIZE = 30;
    public static final BigDecimal DEFAULT_MONEY = BigDecimal.valueOf(100000);
    
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
        World w = new World("Zone World", DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        //w.enableOverlap();
        
        GetWorld getWorld = GetWorld.ofStatic(w);
        
        placeZone(w, Zones.residential, ZoneType.Variant.DEFAULT, 0, 0, 20, 15);
        placeZone(w, Zones.industrial, ZoneType.Variant.DEFAULT, 0, 15, 20, 15);
        placeZone(w, Zones.business, ZoneType.Variant.DEFAULT, 20, 0, 10, 30);
        
        int zoneSize = 6;
        
        for(int y = zoneSize/2;y<DEFAULT_GRID_SIZE-2;y+=zoneSize)
            placeStreet(w, 0, y, DEFAULT_GRID_SIZE-1, y);
        
        for(int x = zoneSize/2;x<DEFAULT_GRID_SIZE-2;x+=zoneSize*3/2)
            placeStreet(w, x, 0, x, DEFAULT_GRID_SIZE-1);
        
        return w;
    }
    
    public static World WaterWorld()
    {
        World w = new World("Water World", DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        
        GetWorld getWorld = GetWorld.ofStatic(w);
        
        int x = DEFAULT_GRID_SIZE/2;
        int y = DEFAULT_GRID_SIZE/2;
        
        int dx = 1;
        int dy = 1;
        
        for(int i=0;i<200;++i)
        {
            if(Math.random() < 0.5)
            {
                switch((int)(Math.random()*4))
                {
                    case 0:
                        dx = 1;
                        dy = 0;
                        break;
                    case 1:
                        dx = -1;
                        dy = 0;
                        break;
                    case 2:
                        dx = 0;
                        dy = 1;
                        break;
                    case 3:
                        dx = 0;
                        dy = -1;
                        break;
                }
            }
            
            //if(x >= 0 && x < DEFAULT_GRID_SIZE && y >= 0 && y < DEFAULT_GRID_SIZE)
            try{
                placeStreet(w, x, y, 1, 1, water);
            }catch(OutOfGridException e){
                //ignore
            }
            
            x += dx;
            y += dy;
            
            x = limit(x);
            y = limit(y);
        }
        
        return w;
    }
    
    private static int limit(int num)
    {
        if(num < 0)
            return num + DEFAULT_GRID_SIZE;
        else if(num >= DEFAULT_GRID_SIZE)
            return num - DEFAULT_GRID_SIZE;
        else
            return num;
    }
    
    public static void placeZone(World w, ZoneType type, ZoneType.Variant variant, int x, int y, int width, int height)
    {
        ZonePlacement zonePlacement = new ZonePlacement(GetWorld.ofStatic(w), type, variant);
        place(zonePlacement, x, y, width, height);
    }
    
    public static void placeStreet(World w, int x, int y, int width, int height)
    {
        placeStreet(w, x, y, width, height, street);
    }
    public static void placeStreet(World w, int x, int y, int width, int height, SelfConnectionType type)
    {
        StreetBuilder sb = new StreetBuilder(GetWorld.ofStatic(w), type, true);
        place(sb, x, y, width, height);
    }
    
    public static void place(Action a, int x, int y, int width, int height)
    {
        Point start = new Point(x, y);
        Point end = new Point(x+width, y+height);
        
        a.mouseDown(start);
        a.moveMouse(end);
        a.mouseReleased(end);
    }

    public static List<World> all()
    {
        return List.of(World1(), World2(), World3(), WaterWorld());
    }
}
