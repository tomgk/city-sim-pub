package org.exolin.citysim;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.exolin.citysim.ui.Utils;
import static org.exolin.citysim.ui.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public final class World
{
    public static final ZoneType zone_residential = createZone("zone_residential");
    public static final ZoneType zone_business = createZone("zone_business");
    public static final ZoneType zone_industrial = createZone("zone_industrial");
    
    public static final ActualBuildingType office = createBuildingType("office", 4, zone_business);
    public static final ActualBuildingType office2 = createBuildingType("office_2", 3, zone_business);
    public static final ActualBuildingType office3 = createBuildingType("office_3", 3, zone_business);
    public static final ActualBuildingType car_cinema = createBuildingType("car-cinema", 4, zone_business);
    public static final ActualBuildingType cinema = createBuildingType("cinema", 3, zone_business);
    public static final ActualBuildingType parkbuilding = createBuildingType("parkbuilding", 3, zone_business);
    
    public static final ActualBuildingType small_house_1 = createBuildingType("small_house_1", 1, zone_residential);
    public static final ActualBuildingType house_1 = createBuildingType("house_1", 3, zone_residential);
    public static final ActualBuildingType house_2 = createBuildingType("house_2", 3, zone_residential);
    public static final ActualBuildingType house_3 = createBuildingType("house_3", 4, zone_residential);
    public static final ActualBuildingType house_4 = createBuildingType("house_4", 4, zone_residential);
    
    public static final ActualBuildingType industrial_small_1 = createBuildingType("industrial_small_1", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_2 = createBuildingType("industrial_small_2", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_3 = createBuildingType("industrial_small_3", 1, zone_industrial);
    public static final ActualBuildingType industrial_small_4 = createBuildingType("industrial_small_4", 1, zone_industrial);
    
    public static final ActualBuildingType plant_solar = createBuildingType("plant_solar", 4, null);
    
    public static final StreetType street = createStreetType("street", List.of(
            "street_1", "street_2",
            "street_x_intersection",
            "street_curve_1", "street_curve_2", "street_curve_3", "street_curve_4",
            "street_t_1", "street_t_2", "street_t_3", "street_t_4"), 1);
    
    private long lastChange = System.currentTimeMillis();
    
    private static StreetType createStreetType(String name, List<String> variants, int size)
    {
        List<BufferedImage> images = variants.stream()
                .map(Utils::loadImage)
                .toList();
        
        return new StreetType(name, images, size);
    }
    
    private static ActualBuildingType createBuildingType(String name, int size, ZoneType zoneType)
    {
        return new ActualBuildingType(name, loadImage(name), size, zoneType);
    }
    
    private static ZoneType createZone(String name)
    {
        return new ZoneType(name, loadImage(name), 1);
    }
    
    private final int gridSize = 30;
    
    public int getGridSize()
    {
        return gridSize;
    }
    
    private final List<Building> buildings = new ArrayList<>();
    
    private World()
    {
        
    }

    public static World Empty()
    {
        return new World();
    }

    public static World World1()
    {
        World w = new World();
        w.addBuilding(office, 6, 3);
        w.addBuilding(office, 9, 12);
        w.addBuilding(car_cinema, 18, 18);
        w.addBuilding(cinema, 18, 21);
        w.addBuilding(office2, 18, 24);
        w.addBuilding(parkbuilding, 18, 27);
        w.addBuilding(office3, 15, 27);
        for(int i=0;i<3;++i)
            w.addBuilding(street, 2+i, 2, StreetType.Variant.CONNECT_X);
        
        w.addBuilding(office, 0, 0);
        
        return w;
    }

    public static World World2()
    {
        World w = new World();
        
        w.addBuilding(office, 6, 6);
        w.addBuilding(parkbuilding, 3, 6);
        
        w.addBuilding(office3, 6, 2);
        
        w.addBuilding(car_cinema, 16, 6);
        w.addBuilding(cinema, 16, 2);
        w.addBuilding(office2, 19, 2);
        
        for(int i=0;i<25;++i)
            w.addBuilding(street, 2+i, 5, StreetType.Variant.CONNECT_X);
        
        for(int i=0;i<4;++i)
            w.addBuilding(street, 20, 6+i, StreetType.Variant.CONNECT_Y);
        
        return w;
    }
    
    public Building getBuildingAt(int x, int y)
    {
        for(Building b: buildings)
            if(b.isOccupying(x, y))
                return b;
        
        return null;
    }
    
    public boolean containsBuilding(int x, int y)
    {
        return getBuildingAt(x, y) != null;
    }

    public void removeBuildingAt(int x, int y)
    {
        //System.out.println("Remove "+x+"/"+y);
        for (Iterator<Building> it = buildings.iterator(); it.hasNext();)
        {
            Building b = it.next();
            if(b.isOccupying(x, y))
            {
                it.remove();
                
                ZoneType zoneType = b.getZoneType();
                if(zoneType != null)
                {
                    //TODO: replace for full size
                    buildings.add(zoneType.createBuilding(x, y, ZoneType.Variant.DEFAULT));
                }
                
                updateBuildingsAround(b.getX(), b.getY(), b.getSize());
                return;
            }
        }
    }
    
    public <B extends Building, E extends Enum<E>> B addBuilding(BuildingType<B, E> type, int x, int y)
    {
        return addBuilding(type, x, y, type.getVariantClass().getEnumConstants()[0]);
    }
    
    public <B extends Building, E extends Enum<E>> B addBuilding(BuildingType<B, E> type, int x, int y, E variant)
    {
        if(x < 0 || y < 0 || x+type.getSize()>=gridSize || y+type.getSize()>=gridSize)
            throw new IllegalArgumentException("out of grid");
        
        B b = type.createBuilding(x, y, variant);
        buildings.add(b);
        buildings.sort(Comparator.comparing(Building::getLevel));
        updateBuilding(b);
        onChange();
        return b;
    }
    
    private Building getBuildingAtForUpdate(int x, int y)
    {
        //System.out.println(x+"/"+y);
        return getBuildingAt(x, y);
    }
    
    private void updateBuilding(Building b)
    {
        b.update(this);
        updateBuildingsAround(b.getX(), b.getY(), b.getSize());
    }
    
    private void updateBuildingsAround(int bx, int by, int bsize)
    {
        //System.out.println("-------");
        for(int x=bx-1;x<bx+bsize+1;++x)
        {
            {
                //  xxxxx
                //   ***
                //   ***
                //   ***
                Building buildingAt = getBuildingAtForUpdate(x, by-1);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
            {
                //   ***
                //   ***
                //   ***
                //  xxxxx
                Building buildingAt = getBuildingAtForUpdate(x, by+bsize);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
        }
        
        for(int y=by;y<by+bsize;++y)
        {
            //only cover sides, corner was already covered in x loop
            
            //  x***
            //  x***
            //  x***
            {
                Building buildingAt = getBuildingAtForUpdate(bx-1, y);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
            //   ***x
            //   ***x
            //   ***x
            {
                Building buildingAt = getBuildingAtForUpdate(bx+bsize, y);
                if(buildingAt != null)
                    buildingAt.update(this);
            }
        }
    }

    public List<Building> getBuildings()
    {
        return buildings;
    }

    void onChange()
    {
        lastChange = System.currentTimeMillis();
    }

    public long getLastChange()
    {
        return lastChange;
    }
    
    private <B> void replaceBuilding(ZoneType type, int x, int y)
    {
        Building b = getBuildingAt(x, y);
        if(b == null)
            return;
        
        ActualBuildingType bt = type.getRandomBuilding();
        if(bt != null && Math.random() < 0.01)
        {
            buildings.remove(b);
            addBuilding(bt, x, y);
        }
    }

    public void update(World w)
    {
        //TODO: maybe no copy
        List<Building> originalBuildings = new ArrayList<>(this.buildings);
        
        for(Building b: originalBuildings)
        {
            if(b.getType() instanceof ZoneType z)
            {
                if(z.getSize() == 1)
                {
                    replaceBuilding(z, b.getX(), b.getY());
                }
            }
        }
    }
}
