package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import org.exolin.citysim.bt.Trees;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.tree.Tree;
import org.exolin.citysim.model.tree.TreeType;

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

    public PlaceTrees(GetWorld world)
    {
        this.world = world;
    }

    @Override
    public StructureType getBuilding()
    {
        return Trees.TREES.getFirst();
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
    
    private static final int COST = 3;
    
    private void plant()
    {
        //always count as, even if nothing gets planted
        lastPlaced = System.currentTimeMillis();
        
        World w = world.get();
        int alreadyPlaced = 0;
        Structure buildingAt = w.getBuildingAt(marking.x, marking.y);
        //if trees already planted there, add one more
        if(buildingAt instanceof Tree t)
            alreadyPlaced = t.getType().getCount();
        //trees cant displace anything else
        else if(buildingAt != null)
            return;
        
        if(alreadyPlaced+1 >= Trees.TREES.size())
            return;
        
        w.addBuilding(Trees.TREES.get(alreadyPlaced), marking.x, marking.y, TreeType.Variant.random());
        w.reduceMoney(COST);
    }

    @Override
    public int getCost()
    {
        return COST;
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
        return Trees.TREES.getFirst().getBrightImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return true;
    }
}
