package org.exolin.citysim.model;

import java.math.BigDecimal;
import static org.exolin.citysim.bt.BusinessBuildings.car_cinema;
import static org.exolin.citysim.bt.BusinessBuildings.cinema;
import static org.exolin.citysim.bt.BusinessBuildings.office;
import static org.exolin.citysim.bt.BusinessBuildings.office2;
import static org.exolin.citysim.bt.BusinessBuildings.office3;
import static org.exolin.citysim.bt.BusinessBuildings.parkbuilding;
import static org.exolin.citysim.bt.Streets.street;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_X;
import static org.exolin.citysim.model.street.ConnectVariant.CONNECT_Y;

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
        World w = new World(DEFAULT_GRID_SIZE, DEFAULT_MONEY);
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
        World w = new World(DEFAULT_GRID_SIZE, DEFAULT_MONEY);
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
}
