package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Plants;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.plant.Plant;
import org.exolin.citysim.model.plant.PlantParameters;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantVariant;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class PlacePlants implements BuildingAction
{
    private final GetWorld world;
    private final Rectangle marking = new Rectangle();
    private boolean mouseDown;
    private long lastPlaced;
    private static final long PLANT_COOLDOWN = 250;//ms
    private final boolean isGrass;

    public PlacePlants(GetWorld world, boolean isGrass)
    {
        this.world = world;
        this.isGrass = isGrass;
    }
    
    private List<PlantType> get()
    {
        return (isGrass ? Plants.GRASS : Plants.XTREES);
    }

    @Override
    public StructureType getBuilding()
    {
        return get().getFirst();
    }

    @Override
    public String getName()
    {
        return "plant plants";
    }

    @Override
    public String toString()
    {
        return getName();
    }
    
    private void plant()
    {
        //always count as, even if nothing gets planted
        lastPlaced = System.currentTimeMillis();
        
        World w = world.get();
        int alreadyPlaced = 0;
        Structure<?, ?, ?, ?> buildingAt = w.getBuildingAt(marking.x, marking.y);
        Optional<ZoneType> zoneType = Optional.empty();
        //if plants already planted there, add one more
        if(buildingAt instanceof Plant t)
        {
            alreadyPlaced = t.getType().getCount();
            zoneType = t.getTheZoneType();
        }
        //plants keep zone if empty zone
        else if(buildingAt instanceof Zone z)
            zoneType = Optional.of(z.getType());
        //plants cant displace anything else
        else if(buildingAt != null)
            return;
        
        if(alreadyPlaced+1 >= get().size())
            return;
        
        w.addBuilding(get().get(alreadyPlaced), marking.x, marking.y, PlantVariant.random(), new PlantParameters(zoneType));
        w.reduceMoney(PlantType.COST);
    }

    @Override
    public int getCost()
    {
        return PlantType.COST;
    }
    
    @Override
    public void mouseDown(Point gridPoint)
    {
        plant();
        mouseDown = true;
    }

    @Override
    public Rectangle getSelection()
    {
        return marking;
    }

    @Override
    public void moveMouse(Point gridPoint)
    {
        //lastPlaced only counts if not switched to different tile
        if(marking.x != gridPoint.x || marking.y != gridPoint.y)
            lastPlaced = 0;
        
        marking.x = gridPoint.x;
        marking.y = gridPoint.y;
        marking.width = 1;
        marking.height = 1;
        
        if(mouseDown && System.currentTimeMillis() - lastPlaced > PLANT_COOLDOWN)
            plant();
    }


    @Override
    public void mouseReleased(Point gridPoint)
    {
        mouseDown = false;
    }

    @Override
    public Image getMarker()
    {
        return get().getFirst().getBrightImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return true;
    }
}
