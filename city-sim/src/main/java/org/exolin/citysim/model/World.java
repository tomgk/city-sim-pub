package org.exolin.citysim.model;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.Plants;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.building.Building;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.regular.SelfConnection;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.debug.ReadonlyValue;
import org.exolin.citysim.model.debug.Value;
import org.exolin.citysim.model.debug.ValueImpl;
import org.exolin.citysim.model.sim.RemoveMode;
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
    
    private String name;
    
    //hint: lastChange should not be saved in save file,
    //so that the time gaps where the game doesnt run are just ignored
    private long lastChange = System.currentTimeMillis();
    private final int gridSize;
    private final List<Structure<?, ?, ?, ?>> structures = new ArrayList<>();
    
    private boolean checkOverlap;
    private static final int MONEY_PERIOD = 10_000;//10 seconds
    private long lastMoneyUpdate = 0;
    private BigDecimal money;
    
    private SimulationSpeed tickFactor = SimulationSpeed.SPEED1;
    
    private final RCI rci = new RCI();

    public RCI getRCI()
    {
        return rci;
    }
    
    public SimulationSpeed getTickFactor()
    {
        return tickFactor;
    }

    public void setTickFactor(SimulationSpeed tickFactor)
    {
        this.tickFactor = tickFactor;
        changed(PROPERTY_SIM_SPEED, tickFactor);
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
        changed(PROPERTY_MONEY, money);
    }

    public void reduceMoney(long money)
    {
        this.money = this.money.subtract(BigDecimal.valueOf(money));
        changed(PROPERTY_MONEY, money);
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

    public void setName(String name)
    {
        this.name = name;
        changed(PROPERTY_CITY_NAME, name);
    }
    
    public Structure<?, ?, ?, ?> getBuildingAt(int x, int y)
    {
        for(Structure<?, ?, ?, ?> b: structures)
            if(b.isOccupying(x, y))
                return b;
        
        return null;
    }
    
    public boolean containsBuilding(int x, int y)
    {
        return getBuildingAt(x, y) != null;
    }
    
    private boolean LOG = false;
    
    public void removeBuildingAt(Structure<?, ?, ?, ?> s)
    {
        if(!structures.remove(s))
            throw new IllegalArgumentException("not part of world");
    }

    public long getNextMoney(long passedTicks)
    {
        return MONEY_PERIOD - passedTicks*GamePanel.TICK_LENGTH%MONEY_PERIOD;
    }
    
    public boolean removeBuildingAt(int x, int y, RemoveMode mode)
    {
        if(LOG)System.out.println(" TRYREM @ "+x+"/"+y);
        for (Iterator<Structure<?, ?, ?, ?>> it = structures.iterator(); it.hasNext();)
        {
            Structure<?, ?, ?, ?> b = it.next();
            if(b.isOccupying(x, y))
            {
                //removeZoning mode keeps streets
                if(b instanceof SelfConnection && mode == RemoveMode.REMOVE_ZONING)
                    continue;
                
                Optional<ZoneType> zoneType = b.getTheZoneType();
                
                //for REMOVE_ZONE, skip anything with a zone
                if(mode == RemoveMode.REMOVE_ZONING && zoneType.isEmpty())
                    continue;
                
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
        
        return Comparator.comparing(b -> b.getLevel(gridSize, rotation));
    }
    
    private Structure<?, ?, ?, ?> getBuildingAtForUpdate(int x, int y)
    {
        return getBuildingAt(x, y);
    }
    
    private void updateBuilding(Structure<?, ?, ?, ?> b)
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
                Structure<?, ?, ?, ?> buildingAt = getBuildingAtForUpdate(x, by-1);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
            {
                //   ***
                //   ***
                //   ***
                //  xxxxx
                Structure<?, ?, ?, ?> buildingAt = getBuildingAtForUpdate(x, by+bsize);
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
                Structure<?, ?, ?, ?> buildingAt = getBuildingAtForUpdate(bx-1, y);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
            //   ***x
            //   ***x
            //   ***x
            {
                Structure<?, ?, ?, ?> buildingAt = getBuildingAtForUpdate(bx+bsize, y);
                if(buildingAt != null)
                    buildingAt.updateAfterChange(this);
            }
        }
    }

    public List<Structure<?, ?, ?, ?>> getStructures()
    {
        return structures;
    }

    public List<Structure<?, ?, ?, ?>> getStructures(Rotation rotation)
    {
        if(rotation == Rotation.ORIGINAL)
            return structures;
        
        List<Structure<?, ?, ?, ?>> structuresRotated = new ArrayList<>(structures);
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
    
    /** returns the biggest dimension possible to build on that zone */
    private int getMaxZone(ZoneType z, int x, int y)
    {
        for(int size=1;size<StructureSize.MAX;++size)
        {
            if(!isFullOf(z, x, y, size))
                return size-1;
        }
        return 3;
    }
    
    /** checks if size x size tiles are of zone z (empty zone) */
    private boolean isFullOf(ZoneType z, int x, int y, int size)
    {
        for(int yi=0;yi<size;++yi)
        {
            for(int xi=0;xi<size;++xi)
            {
                Structure<?, ?, ?, ?> b = getBuildingAt(x+xi, y+yi);
                if(b == null || b.getType() != z)
                    return false;
            }
        }
        
        return true;
    }
    
    private <B> void replaceBuilding(ZoneType type, int x, int y, int ticks)
    {
        Structure<?, ?, ?, ?> b = getBuildingAt(x, y);
        if(b == null)
            return;
        
        if(RandomUtils.random() < RandomUtils.getProbabilityForTicks(TICK_PROBABILTY_FOR_BUILDING, ticks))
            ;
        else
            return;
        
        int size = getMaxZone(type, x, y);
        StructureType bt = type.getRandomBuilding(size);
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
    
    private void updateBuildCount(Structure<?, ?, ?, ?> building, boolean up)
    {
        Optional<ZoneType> type = building.getUnderlyingZoneType();
        if(type.isEmpty())
            return;
        
        int factor = up ? +1 : -1;
        buildSupply.computeIfPresent(type.get(), (k, v) -> v+factor*building.getSupply());
    }
    
    /**
     * updates the amount of money, based on taxes and costs
     * 
     * @param passedTicks passed ticks
     */
    private void updateMoney(long passedTicks)
    {
        long moneyTime = passedTicks*GamePanel.TICK_LENGTH/MONEY_PERIOD;
        if(moneyTime == lastMoneyUpdate)
            return;
        
        int ticks = (int)(moneyTime - lastMoneyUpdate);
        lastMoneyUpdate = moneyTime;
        
        BigDecimal bigTicks = BigDecimal.valueOf(ticks);
        for(Structure<?, ?, ?, ?> b : structures)
        {
            money = money.subtract(b.getMaintenance().multiply(bigTicks));
            money = money.add(b.getTaxRevenue().multiply(bigTicks));
        }
        
        changed(PROPERTY_MONEY, money);
    }
    
    /**
     * Iterates over all current structures,
     * ignoring those added by the {@link consumer}
     * as well as those removed previously by the {@code consumer}.
     * 
     * @param consumer gets called for every entry that existed at the time
     *                 of the call exluding the removed ones
     */
    private void iterateStructures(Consumer<Structure<?, ?, ?, ?>> consumer)
    {
        //TODO: maybe no copy
        List<Structure> originalStructures = new ArrayList<>(this.structures);
        
        for(Structure<?, ?, ?, ?> b: originalStructures)
        {
            //if it was already removed, skip it
            if(!this.structures.contains(b))
                continue;
            
            consumer.accept(b);
        }
    }

    public void updateAfterTick(int ticks, long passedTicks)
    {
        updateStats();
        updateAfterTick0(ticks, passedTicks);
        updateStats();
    }
    
    private void updateAfterTick0(int ticks, long passedTicks)
    {
        if(ticks == 0)
            return;
        else if(ticks < 0)
            throw new IllegalArgumentException();
        
        updateMoney(passedTicks);
        rci.update(structures);
        
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
    
    private void handleZone(Structure<?, ?, ?, ?> s, int ticks, ZoneType z)
    {
        if(z.getSize() != 1)
            return;
        
        //no building without electric
        if(!canBuildBuilding(s))
            return;

        if(hasAnyInRadius(SelfConnections.street, s.getX(), s.getY(), Zones.BUILDING_DISTANCE))
            replaceBuilding(z, s.getX(), s.getY(), ticks);
    }

    private boolean hasAnyInRadius(SelfConnectionType street, int cx, int cy, int distance)
    {
        for(int y=cy-distance;y<=cy+distance;++y)
        {
            for(int x=cx-distance;x<=cx+distance;++x)
            {
                Structure<?, ?, ?, ?> b = getBuildingAt(x, y);
                if(b == null)
                    continue;
                if(b.getType() == street)
                    return true;
            }
        }
        
        return false;
    }
    
    private final Map<Structure<?, ?, ?, ?>, ElectricityGridArea> structuresWithElectricity = new IdentityHashMap<>();

    public Map<Structure<?, ?, ?, ?>, ElectricityGridArea> getStructuresWithElectricity()
    {
        return Collections.unmodifiableMap(structuresWithElectricity);
    }
    
    public Map<ElectricityGrid, List<Structure<?, ?, ?, ?>>> getElectricityGrids()
    {
        return structuresWithElectricity.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        (Entry<Structure<?, ?, ?, ?>, ElectricityGridArea> e) -> e.getValue().getElectricityGrid(),
                        Collectors.mapping((Entry<Structure<?, ?, ?, ?>, ElectricityGridArea> e) -> e.getKey(), Collectors.toList())));
    }
    
    private void updateStats()
    {
        updateElectricityGrid();
        updateElectricityCoverageStats();
    }
    
    private void updateElectricityGrid()
    {
        structuresWithElectricity.clear();
        
        structures.stream()
                .filter(Plants::isPlant)
                .forEach(plant -> {
                    //each plant starts as an own grid
                    ElectricityGridArea grid = new ElectricityGridArea((Building)plant);
                    onFindStructureWithElectricity(grid, plant);
                });
        
        //System.out.println(structures);
    }
    
    private void onFindStructureWithElectricity(ElectricityGridArea grid, Structure<?, ?, ?, ?> s)
    {
        Objects.requireNonNull(s);
        
        //remember, and if already captured, skip
        ElectricityGridArea existingGrid = structuresWithElectricity.get(s);
        if(existingGrid != null)
        {
            grid.connectTo(existingGrid);
            
            return;
        }
        
        structuresWithElectricity.put(s, grid);
        grid.getElectricityGrid().addStructure(s);
        
        int x = s.getX();
        int y = s.getY();
        int size = s.getSize();
        for(int yi=-1;yi<size+1;++yi)
        {
            for(int xi=-1;xi<size+1;++xi)
            {
                //exclude diagonal
                if(xi != 0 && yi != 0)
                    continue;
                
                ConnectionType.Direction d = xi != 0 ? ConnectionType.Direction.X : ConnectionType.Direction.Y;
                
                Structure<?, ?, ?, ?> neighbor = getBuildingAt(x+xi, y+yi);
                
                //check if it is actually the current building
                if(neighbor == s)
                    continue;
                
                //TODO: conduction over street should only work with one tile
                //TODO: circuit over street doesn't work
                if(neighbor != null && Plants.getElectricity(neighbor, d).transfers())
                    onFindStructureWithElectricity(grid, neighbor);
            }
        }
    }
    
    record Coverage(int covered, int total)
    {
        Coverage merge(Coverage c)
        {
            return new Coverage(covered+c.covered, total+c.total);
        }
        
        String getPercentage()
        {
            if(total == 0)
                return "--/--";
            
            return covered+"/"+total;
        }
    }
    
    private static final Coverage EMPTY_COVERAGE = new Coverage(0, 0);
    private Coverage currentCoverage = EMPTY_COVERAGE;
    
    private void updateElectricityCoverageStats()
    {
        Coverage previous = currentCoverage;
        
        currentCoverage = structures.stream()
                .filter(f -> f instanceof Building || f instanceof Zone || f.getZoneType(true).isPresent())
                .map(s -> {
                    boolean covered = hasElectricity(s);
                    int size = s.getSize();
                    return new Coverage(covered ? size : 0, size);
                })
                .reduce(Coverage::merge)
                .orElse(EMPTY_COVERAGE);
        
        if(!previous.equals(currentCoverage))
            changed(PROPERTY_ELECTRICITY_COVERAGE, getElectricityCoverage());
    }
    
    private String getElectricityCoverage()
    {
        return currentCoverage.getPercentage();
    }
    
    public boolean hasElectricity(Structure<?, ?, ?, ?> s)
    {
        return structuresWithElectricity.containsKey(s);
    }
    
    private boolean needElectricity = true;
    
    public static final String PROPERTY_CITY_NAME = "cityName";
    public static final String PROPERTY_NEED_ELECTRICITY = "needElectricity";
    public static final String PROPERTY_MONEY = "money";
    public static final String PROPERTY_SIM_SPEED = "simSpeed";
    public static final String PROPERTY_STRUCTURE_COUNT = "stuctureCount";
    public static final String PROPERTY_LAST_MONEY_UPDATE = "lastMoneyUpdate";
    public static final String PROPERTY_LAST_CHANGE_DATE = "lastChange.date";
    public static final String PROPERTY_LAST_CHANGE_TIME = "lastChange.time";
    public static final String PROPERTY_ELECTRICITY_COVERAGE = "electricityCoverage";
    
    private final List<Entry<String, Value<?>>> values = new ArrayList<>();
    
    private <T> void addValue(String name, Supplier<T> getter, Consumer<T> setter)
    {
        values.add(new AbstractMap.SimpleImmutableEntry<>(name, new ValueImpl<>(getter, setter)));
    }
    
    private <T> void addDebugValue(String name, Supplier<T> getter, Consumer<T> setter)
    {
        values.add(new AbstractMap.SimpleImmutableEntry<>(name, new ValueImpl<>(getter, v -> {
            setter.accept(v);
            changed(name, v);
        })));
    }
    
    private <T> void addReadonlyValue(String name, Supplier<T> getter)
    {
        values.add(new AbstractMap.SimpleImmutableEntry<>(name, (ReadonlyValue<T>)getter::get));
    }
    
    {
        addValue(PROPERTY_CITY_NAME, this::getName, this::setName);
        addDebugValue(PROPERTY_NEED_ELECTRICITY, () -> needElectricity, v -> this.needElectricity = v);
        addValue(PROPERTY_MONEY, this::getMoney, this::setMoney);
        addValue(PROPERTY_SIM_SPEED, this::getTickFactor, this::setTickFactor);
        addReadonlyValue(PROPERTY_STRUCTURE_COUNT, structures::size);
        addReadonlyValue(PROPERTY_LAST_MONEY_UPDATE, () -> lastMoneyUpdate);
        addReadonlyValue(PROPERTY_LAST_CHANGE_DATE, () -> LocalDate.ofInstant(Instant.ofEpochMilli(lastChange), ZoneId.systemDefault()));
        addReadonlyValue(PROPERTY_LAST_CHANGE_TIME, () -> LocalTime.ofInstant(Instant.ofEpochMilli(lastChange), ZoneId.systemDefault()));
        addReadonlyValue(PROPERTY_ELECTRICITY_COVERAGE, () -> getElectricityCoverage());
    }

    public List<Entry<String, Value<?>>> getValues()
    {
        return Collections.unmodifiableList(values);
    }
    
    public boolean canBuildBuilding(Structure<?, ?, ?, ?> s)
    {
        return needElectricity ? hasElectricity(s) : true;
    }
    
    private static final List<WorldListener> listeners = new ArrayList<>();
    
    public void addListener(WorldListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(WorldListener listener)
    {
        listeners.remove(listener);
    }
    
    private void changed(String name, Object newValue)
    {
        //System.out.println("["+listeners.size()+"] Changed "+name+"."+name+"="+newValue);
        listeners.stream().forEach(l -> l.onChanged(name, newValue));
    }

    /**
     * Calls {@link WorldListener#onChanged(java.lang.String, java.lang.Object)}
     * for every value.
     * <p>
     * Useful when the {@link World} instance gets replaced.
     * 
     * @param listener the listener
     */
    public void triggerAllChanges(WorldListener listener)
    {
        values.forEach(e -> listener.onChanged(e.getKey(), e.getValue().get()));
    }
}
