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
import java.util.function.Consumer;
import org.exolin.citysim.bt.BuildingTypes;
import org.exolin.citysim.bt.SelfConnections;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.OutOfGridException;
import org.exolin.citysim.utils.Utils;

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
    private final List<Structure> buildings = new ArrayList<>();
    
    private boolean checkOverlap;
    private static final int MONEY_PERIOD = 10_000;//10 seconds
    private long lastMoneyUpdate = System.currentTimeMillis()/MONEY_PERIOD;
    private BigDecimal money;
    
    private SimulationSpeed tickFactor = SimulationSpeed.SPEED1;

    public SimulationSpeed getTickFactor()
    {
        return tickFactor;
    }

    public void setTickFactor(SimulationSpeed tickFactor)
    {
        this.tickFactor = tickFactor;
    }
    
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
    
    public Structure getBuildingAt(int x, int y)
    {
        for(Structure b: buildings)
            if(b.isOccupying(x, y))
                return b;
        
        return null;
    }
    
    public boolean containsBuilding(int x, int y)
    {
        return getBuildingAt(x, y) != null;
    }
    
    private boolean LOG = false;
    
    public void removeBuildingAt(Structure s)
    {
        if(!buildings.remove(s))
            throw new IllegalArgumentException("not part of world");
    }

    public boolean removeBuildingAt(int x, int y, boolean removeZoning, boolean replaceWithZoning)
    {
        if(LOG)System.out.println(" TRYREM @ "+x+"/"+y);
        for (Iterator<Structure> it = buildings.iterator(); it.hasNext();)
        {
            Structure b = it.next();
            if(b.isOccupying(x, y))
            {
                //removeZoning mode keeps streets
                if(b instanceof SelfConnection && removeZoning)
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
                if(LOG)System.out.println(" REM "+b.toString());
                return true;
            }
        }
        if(LOG)System.out.println(" FAILREM");
        return false;
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
    
    public <B extends Structure, E extends StructureVariant> B addBuilding(StructureType<B, E> type, int x, int y)
    {
        return addBuilding(type, x, y, type.getDefaultVariant());
    }
    
    public <B extends Structure, E extends StructureVariant> B addBuilding(StructureType<B, E> type, int x, int y, E variant)
    {
        if(x < 0 || y < 0 || x+type.getSize()>gridSize || y+type.getSize()>gridSize)
            throw new OutOfGridException(
                    "out of grid: "+new Rectangle(x, y, type.getSize(), type.getSize())+
                            " outside of "+new Rectangle(0, 0, gridSize, gridSize));
        
        LOG = type.getSize() > 1 && false;
        if(LOG)
            System.out.println("ADD @ "+x+"/"+y+" "+type.toString());
        
        int size = type.getSize();
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                //remove any, let anything outside be replaced with zone
                //removeBuildingAt(x, y, false, true);
                removeBuildingAt(x, y, false, false);
            }
        }
        
        B b;
        try{
            b = type.createBuilding(x, y, variant);
        }catch(ClassCastException e){
            throw e;
        }
        buildings.add(b);
        buildings.sort(sorter(Rotation.ORIGINAL));
        updateBuilding(b);
        updateBuildCount(b, true);
        onChange();
        return b;
    }
    
    private Comparator<Structure> sorter(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return Comparator.comparing(Structure::getLevel);
        
        return Comparator.comparing((Structure b) -> b.getLevel(gridSize, rotation));
    }
    
    private Structure getBuildingAtForUpdate(int x, int y)
    {
        return getBuildingAt(x, y);
    }
    
    private void updateBuilding(Structure b)
    {
        b.updateAfterChange(this);
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
                Structure buildingAt = getBuildingAtForUpdate(x, by-1);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
            {
                //   ***
                //   ***
                //   ***
                //  xxxxx
                Structure buildingAt = getBuildingAtForUpdate(x, by+bsize);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
        }
        
        for(int y=by;y<by+bsize;++y)
        {
            //only cover sides, corner was already covered in x loop
            
            //  x***
            //  x***
            //  x***
            {
                Structure buildingAt = getBuildingAtForUpdate(bx-1, y);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
            //   ***x
            //   ***x
            //   ***x
            {
                Structure buildingAt = getBuildingAtForUpdate(bx+bsize, y);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
        }
    }

    public List<Structure> getBuildings()
    {
        return buildings;
    }

    public List<Structure> getBuildings(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return buildings;
        
        List<Structure> buildingsRotated = new ArrayList<>(buildings);
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
    
    private static final double TICK_PROBABILTY_FOR_BUILDING = 0.01;

    private <B> void replaceBuilding(ZoneType type, int x, int y, int ticks)
    {
        Structure b = getBuildingAt(x, y);
        if(b == null)
            return;
        
        if(Math.random() < Utils.getProbabilityForTicks(TICK_PROBABILTY_FOR_BUILDING, ticks))
            ;
        else
            return;
        
        int size = getMaxZone(type, x, y);
        BuildingType bt = type.getRandomBuilding(1/*size*/);
        if(bt == null)
            return;
        
        if(bt.getSize() > 1)
        {
            bt = bt;
        }
        
        /*
        for(int yi=0;yi<type.getSize();yi++)
        {
            for(int xi=0;xi<type.getSize();xi++)
            {
                removeBuildingAt(x, y, true, false);
            }
        }
*/
        updateBuildCount(b, false);
        addBuilding(bt, x, y);
    }
    
    private void updateBuildCount(Structure building, boolean up)
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
        for(Structure b : buildings)
            money = money.subtract(b.getMaintenance().multiply(bigTicks));
    }
    
    private void iterateBuildings(Consumer<Structure> consumer)
    {
        
        //TODO: maybe no copy
        List<Structure> originalBuildings = new ArrayList<>(this.buildings);
        
        for(Structure b: originalBuildings)
        {
            //if it was already removed, skip it
            if(!this.buildings.contains(b))
                continue;
            
            consumer.accept(b);
        }
    }

    public void updateAfterTick(int ticks)
    {
        if(ticks == 0)
            return;
        else if(ticks < 0)
            throw new IllegalArgumentException();
        
        long moneyTime = System.currentTimeMillis()/MONEY_PERIOD;
        if(moneyTime != lastMoneyUpdate)
        {
            updateMoney((int)(moneyTime - lastMoneyUpdate) * ticks);
            lastMoneyUpdate = moneyTime;
        }
        
        iterateBuildings(b -> {
            if(b.getType() instanceof ZoneType z)
            {
                if(z.getSize() != 1)
                    return;
                
                if(hasAnyInRadius(SelfConnections.street, b.getX(), b.getY(), Zones.BUILDING_DISTANCE))
                    replaceBuilding(z, b.getX(), b.getY(), ticks);
            }
            else
                b.updateAfterTick(this, ticks);
        });
    }
    
    private int getMaxZone(ZoneType z, int x, int y)
    {
        for(int size=1;size<3;++size)
        {
            if(!isFullOf(z, x, y, size))
                return size-1;
        }
        return 3;
    }
    
    private boolean isFullOf(ZoneType z, int x, int y, int size)
    {
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                Structure b = getBuildingAt(x+xi, y+yi);
                if(b == null || b.getType() != z)
                    return false;
            }
        }
        
        return true;
    }

    private boolean hasAnyInRadius(SelfConnectionType street, int cx, int cy, int distance)
    {
        for(int y=cy-distance;y<=cy+distance;++y)
        {
            for(int x=cx-distance;x<=cx+distance;++x)
            {
                Structure b = getBuildingAt(x, y);
                if(b == null)
                    continue;
                if(b.getType() == street)
                    return true;
            }
        }
        
        return false;
    }
}
