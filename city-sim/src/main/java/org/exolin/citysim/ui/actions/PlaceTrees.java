package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeParameters;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.tree.TreeVariant;
import org.exolin.citysim.model.zone.Zone;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class PlaceTrees implements BuildingAction
{
    private final GetWorld world;
    private final Rectangle marking = new Rectangle();
    private boolean mouseDown;
    private long lastPlaced;
    private static final long PLANT_COOLDOWN = 250;//ms
    private final boolean isGrass;

    public PlaceTrees(GetWorld world, boolean isGrass)
    {
        this.world = world;
        this.isGrass = isGrass;
    }
    
    private List<TreeType> get()
    {
        return (isGrass ? Trees.GRASS : Trees.XTREES);
    }

    @Override
    public StructureType getBuilding()
    {
        return get().getFirst();
    }

    @Override
    public String getName()
    {
        return "plant trees";
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
        //if trees already planted there, add one more
        if(buildingAt instanceof Tree t)
        {
            alreadyPlaced = t.getType().getCount();
            zoneType = t.getTheZoneType();
        }
        //trees keep zone if empty zone
        else if(buildingAt instanceof Zone z)
            zoneType = Optional.of(z.getType());
        //trees cant displace anything else
        else if(buildingAt != null)
            return;
        
        if(alreadyPlaced+1 >= get().size())
            return;
        
        w.addBuilding(get().get(alreadyPlaced), marking.x, marking.y, TreeVariant.random(), new TreeParameters(zoneType));
        w.reduceMoney(TreeType.COST);
    }

    @Override
    public int getCost()
    {
        return TreeType.COST;
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
