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
import java.util.Optional;
import java.util.function.Consumer;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.OutOfGridException;
import org.exolin.citysim.utils.RandomUtils;

/**
 * Logical representation of the world.
 *
 * @author Thomas
 */
public final class World
{
    static
    {
        StructureTypes.init();
    }
    
    private final String name;
    
    //hint: lastChange should not be saved in save file,
    //so that the time gaps where the game doesnt run are just ignored
    private long lastChange = System.currentTimeMillis();
    private final int gridSize;
    private final List<Structure> structures = new ArrayList<>();
    
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
    
    public World(String name, int gridSize, BigDecimal money, SimulationSpeed speed)
    {
        this.name = name;
        this.gridSize = gridSize;
        this.money = Objects.requireNonNull(money);
        this.tickFactor = speed;
    }

    public String getName()
    {
        return name;
    }
    
    public Structure getBuildingAt(int x, int y)
    {
        for(Structure b: structures)
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
        if(!structures.remove(s))
            throw new IllegalArgumentException("not part of world");
    }

    public long getNextMoney(long passedTicks)
    {
        return MONEY_PERIOD - passedTicks*GamePanel.TICK_LENGTH%MONEY_PERIOD;
    }
    
    public enum RemoveMode
    {
        /**
         * removes zones and any building with a zone, keeps everything else;
         * no replacement for removals
         */
        REMOVE_ZONING,
        
        /**
         * removes buildings, leaves back zones if zoned
         */
        TEAR_DOWN,
        
        /**
         * remove, leave nothing behind, expect outside of (x,y)
         */
        CLEAR
    }

    public boolean removeBuildingAt(int x, int y, RemoveMode mode)
    {
        if(LOG)System.out.println(" TRYREM @ "+x+"/"+y);
        for (Iterator<Structure> it = structures.iterator(); it.hasNext();)
        {
            Structure b = it.next();
            if(b.isOccupying(x, y))
            {
                //removeZoning mode keeps streets
                if(b instanceof SelfConnection && mode == RemoveMode.REMOVE_ZONING)
                    continue;
                
                Optional<ZoneType> zoneType = b.getZoneType();
                
                if(mode == RemoveMode.REMOVE_ZONING)
                {
                    //if remove zone and it is a zone, continue
                    if(b instanceof Zone)
                        ;
                    //keep building if not belonging to a zone
                    //but it is about removing zoning
                    else if(zoneType.isEmpty())
                        continue;
                }
                
                //if(checkOverlap)
                //    throw new IllegalArgumentException();
                
                it.remove();
                
                //TODO: CLEAR should place zone outside of (x,y) itself
                if(mode == RemoveMode.TEAR_DOWN)
                {
                    if(zoneType.isPresent())
                        placeZone(zoneType.get(), b.getX(), b.getY(), b.getSize(), x, y);
                    
                    updateStructuresAround(b.getX(), b.getY(), b.getSize());
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
    
    public <B extends Structure, E extends StructureVariant> B addBuilding(StructureType<B, E, EmptyStructureParameters> type, int x, int y)
    {
        return addBuilding(type, x, y, type.getVariantForDefaultImage());
    }
    
    public <B extends Structure, E extends StructureVariant> B addBuilding(StructureType<B, E, EmptyStructureParameters> type, int x, int y, E variant)
    {
        return addBuilding(type, x, y, variant, EmptyStructureParameters.getInstance());
    }
    
    public <B extends Structure, E extends StructureVariant, D extends StructureParameters<D>> B addBuilding(StructureType<B, E, D> type, int x, int y, E variant, D data)
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
                removeBuildingAt(x+xi, y+yi, RemoveMode.CLEAR);
            }
        }
        
        B b;
        try{
            b = type.createBuilding(x, y, variant, data);
        }catch(ClassCastException e){
            throw e;
        }
        structures.add(b);
        structures.sort(sorter(Rotation.ORIGINAL));
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
        updateStructuresAround(b.getX(), b.getY(), b.getSize());
    }
    
    private void updateStructuresAround(int bx, int by, int bsize)
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

    public List<Structure> getStructures()
    {
        return structures;
    }

    public List<Structure> getStructures(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return structures;
        
        List<Structure> structuresRotated = new ArrayList<>(structures);
        structuresRotated.sort(sorter(rotation));
        return structuresRotated;
    }

    void onChange()
    {
        lastChange = System.currentTimeMillis();
    }

    public long getLastChange()
    {
        return lastChange;
    }
    
    private static final double TICK_PROBABILTY_FOR_BUILDING = 0.0001;

    private <B> void replaceBuilding(ZoneType type, int x, int y, int ticks)
    {
        Structure b = getBuildingAt(x, y);
        if(b == null)
            return;
        
        if(RandomUtils.random() < RandomUtils.getProbabilityForTicks(TICK_PROBABILTY_FOR_BUILDING, ticks))
            ;
        else
            return;
        
        int size = getMaxZone(type, x, y);
        StructureType bt = type.getRandomBuilding(1/*size*/);
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
        Optional<ZoneType> type = building.getZoneType();
        if(type.isEmpty())
            return;
        
        int factor = up ? +1 : -1;
        buildSupply.computeIfPresent(type.get(), (k, v) -> v+factor*building.getSupply());
    }
    
    private void updateMoney(int ticks)
    {
        BigDecimal bigTicks = BigDecimal.valueOf(ticks);
        for(Structure b : structures)
        {
            money = money.subtract(b.getMaintenance().multiply(bigTicks));
            money = money.add(b.getTaxRevenue().multiply(bigTicks));
        }
    }
    
    private void iterateStructures(Consumer<Structure> consumer)
    {
        
        //TODO: maybe no copy
        List<Structure> originalStructures = new ArrayList<>(this.structures);
        
        for(Structure b: originalStructures)
        {
            //if it was already removed, skip it
            if(!this.structures.contains(b))
                continue;
            
            consumer.accept(b);
        }
    }

    public void updateAfterTick(int ticks, long passedTicks)
    {
        if(ticks == 0)
            return;
        else if(ticks < 0)
            throw new IllegalArgumentException();
        
        long moneyTime = passedTicks*GamePanel.TICK_LENGTH/MONEY_PERIOD;
        if(moneyTime != lastMoneyUpdate)
        {
            //TODO: only works for ticks=1
            updateMoney((int)(moneyTime - lastMoneyUpdate) * ticks);
            lastMoneyUpdate = moneyTime;
        }
        
        iterateStructures(s -> {
            if(s.getType() instanceof ZoneType z)
            {
                handleZone(s, ticks, z);
            }
            
            s.updateAfterTick(this, ticks);
            
            if(s instanceof Tree t)
            {
                t.getDataCopy().getZone().ifPresent(z -> {
                    handleZone(s, ticks, z);
                });
            }
        });
    }
    
    private void handleZone(Structure s, int ticks, ZoneType z)
    {
        if(z.getSize() != 1)
            return;

        if(hasAnyInRadius(SelfConnections.street, s.getX(), s.getY(), Zones.BUILDING_DISTANCE))
            replaceBuilding(z, s.getX(), s.getY(), ticks);
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
