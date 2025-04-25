package org.exolin.citysim.ui.actions;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Supplier;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.fire.FireParameters;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.storage.BuildingData;
import org.exolin.citysim.storage.StructureData;

/**
 *
 * @author Thomas
 */
public class PlaceBuilding implements BuildingAction
{
    private final GetWorld world;
    private final StructureType type;
    private final Rectangle marking = new Rectangle();
    private final int cost;
    private final Supplier<StructureVariant> variant;
    private final Supplier<StructureParameters> parameters;

    public PlaceBuilding(GetWorld world, BuildingType type)
    {
        this.world = world;
        this.type = type;
        this.cost = type.getCost();
        this.variant = () -> BuildingType.Variant.DEFAULT;
        this.parameters = () -> new PlainStructureParameters();
    }

    public PlaceBuilding(GetWorld world, FireType type)
    {
        this.world = world;
        this.type = type;
        this.cost = 0;
        this.variant = FireType.Variant::random;
        this.parameters = () -> new FireParameters(10000);//TODO: constant
    }

    @Override
    public StructureType getBuilding()
    {
        return type;
    }

    @Override
    public String getName()
    {
        return "build "+type.getName();
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public void mouseDown(Point gridPoint)
    {
        World w = world.get();
        w.addBuilding(type, marking.x, marking.y, variant.get(), parameters.get());
        w.reduceMoney(cost);
    }

    @Override
    public Rectangle getSelection()
    {
        return marking;
    }

    @Override
    public void moveMouse(Point gridPoint)
    {
        marking.x = gridPoint.x;
        marking.y = gridPoint.y;
        marking.width = type.getSize();
        marking.height = type.getSize();
    }


    @Override
    public void mouseReleased(Point gridPoint)
    {

    }

    @Override
    public Image getMarker()
    {
        return type.getBrightImage();
    }

    @Override
    public boolean scaleMarker()
    {
        return true;
    }

    @Override
    public int getCost()
    {
        return cost;
    }
}
