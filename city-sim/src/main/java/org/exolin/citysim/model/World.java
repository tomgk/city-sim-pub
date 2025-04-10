package org.exolin.citysim.model;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.exolin.citysim.bt.BuildingTypes;
import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.street.Street;
import org.exolin.citysim.model.street.StreetType;
import org.exolin.citysim.ui.OutOfGridException;

/**
 *
 * @author Thomas
 */
public final class World
{
    static
    {
        BuildingTypes.init();
    }
    
    private final String name;
    
    //hint: lastChange should not be saved in save file,
    //so that the time gaps where the game doesnt run are just ignored
    private long lastChange = System.currentTimeMillis();
    private final int gridSize;
    private final List<Building> buildings = new ArrayList<>();
    
    private boolean checkOverlap;
    private static final int MONEY_PERIOD = 10_000;//10 seconds
    private long lastMoneyUpdate = System.currentTimeMillis()/MONEY_PERIOD;
    private BigDecimal money;
    
    public void enableOverlap()
    {
        checkOverlap = true;
    }
    
    public void disableOverlap()
    {
        checkOverlap = false;
    }
    
    private final Map<ZoneType, Integer> buildSupply = new HashMap<>();
    {
        for(ZoneType t: Zones.BASIC_ZONES)
            buildSupply.put(t, 0);
    }
    
    public int getGridSize()
    {
        return gridSize;
    }

    public BigDecimal getMoney()
    {
        return money;
    }

    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public void reduceMoney(long money)
    {
        this.money = this.money.subtract(BigDecimal.valueOf(money));
    }
    
    public World(String name, int gridSize, BigDecimal money)
    {
        this.name = name;
        this.gridSize = gridSize;
        this.money = Objects.requireNonNull(money);
    }

    public String getName()
    {
        return name;
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

    public void removeBuildingAt(int x, int y, boolean removeZoning, boolean replaceWithZoning)
    {
        for (Iterator<Building> it = buildings.iterator(); it.hasNext();)
        {
            Building b = it.next();
            if(b.isOccupying(x, y))
            {
                //removeZoning mode keeps streets
                if(b instanceof Street && removeZoning)
                    continue;
                
                ZoneType zoneType = b.getZoneType();
                
                //if remove zone and it is a zone, continue
                if(removeZoning && b instanceof Zone)
                    ;
                //keep building if not belonging to a zone
                //but it is about removing zoning
                else if(zoneType == null && removeZoning)
                    continue;
                
                if(checkOverlap)
                    throw new IllegalArgumentException();
                
                it.remove();
                
                if(replaceWithZoning)
                {
                    if(zoneType != null)
                        placeZone(zoneType, b.getX(), b.getY(), b.getSize(), x, y);
                    
                    updateBuildingsAround(b.getX(), b.getY(), b.getSize());
                }
                return;
            }
        }
    }

    private void placeZone(ZoneType zoneType, int x, int y, int size, int exceptX, int exceptY)
    {
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                if(y+yi == exceptY && x+xi == exceptX)
                    continue;
                
                addBuilding(zoneType, x+xi, y+yi, ZoneType.Variant.DEFAULT);
            }
        }
    }
    
    public <B extends Building, E extends BuildingVariant> B addBuilding(BuildingType<B, E> type, int x, int y)
    {
        return addBuilding(type, x, y, type.getDefaultVariant());
    }
    
    public <B extends Building, E extends BuildingVariant> B addBuilding(BuildingType<B, E> type, int x, int y, E variant)
    {
        if(x < 0 || y < 0 || x+type.getSize()>gridSize || y+type.getSize()>gridSize)
            throw new OutOfGridException(
                    "out of grid: "+new Rectangle(x, y, type.getSize(), type.getSize())+
                            " outside of "+new Rectangle(0, 0, gridSize, gridSize));
        
        int size = type.getSize();
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                //remove any, let anything outside be replaced with zone
                removeBuildingAt(x, y, false, true);
            }
        }
        
        B b = type.createBuilding(x, y, variant);
        buildings.add(b);
        buildings.sort(sorter(Rotation.ORIGINAL));
        updateBuilding(b);
        updateBuildCount(b, true);
        onChange();
        return b;
    }
    
    private Comparator<Building> sorter(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return Comparator.comparing(Building::getLevel);
        
        return Comparator.comparing((Building b) -> b.getLevel(gridSize, rotation));
    }
    
    private Building getBuildingAtForUpdate(int x, int y)
    {
        return getBuildingAt(x, y);
    }
    
    private void updateBuilding(Building b)
    {
        b.update(this);
        updateBuildingsAround(b.getX(), b.getY(), b.getSize());
    }
    
    private void updateBuildingsAround(int bx, int by, int bsize)
    {
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

    public List<Building> getBuildings(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return buildings;
        
        List<Building> buildingsRotated = new ArrayList<>(buildings);
        buildingsRotated.sort(sorter(rotation));
        return buildingsRotated;
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
            updateBuildCount(b, false);
            addBuilding(bt, x, y);
        }
    }
    
    private void updateBuildCount(Building building, boolean up)
    {
        ZoneType type = building.getZoneType();
        if(type == null)
            return;
        
        int factor = up ? +1 : -1;
        
        buildSupply.computeIfPresent(type, (k, v) -> v+factor*building.getSupply());
    }
    
    private void updateMoney(int ticks)
    {
        BigDecimal bigTicks = BigDecimal.valueOf(ticks);
        for(Building b : buildings)
            money = money.subtract(b.getMaintenance().multiply(bigTicks));
    }

    public void update()
    {
        long moneyTime = System.currentTimeMillis()/MONEY_PERIOD;
        if(moneyTime != lastMoneyUpdate)
        {
            updateMoney((int)(moneyTime - lastMoneyUpdate));
            lastMoneyUpdate = moneyTime;
        }
        
        //TODO: maybe no copy
        List<Building> originalBuildings = new ArrayList<>(this.buildings);
        
        for(Building b: originalBuildings)
        {
            if(b.getType() instanceof ZoneType z)
            {
                if(z.getSize() != 1)
                    continue;
                
                if(hasAnyInRadius(Streets.street, b.getX(), b.getY(), Zones.BUILDING_DISTANCE))
                    replaceBuilding(z, b.getX(), b.getY());
            }
        }
    }

    private boolean hasAnyInRadius(StreetType street, int cx, int cy, int distance)
    {
        for(int y=cy-distance;y<=cy+distance;++y)
        {
            for(int x=cx-distance;x<=cx+distance;++x)
            {
                Building b = getBuildingAt(x, y);
                if(b == null)
                    continue;
                if(b.getType() == street)
                    return true;
            }
        }
        
        return false;
    }
}
