package org.exolin.citysim.bt.buildings;

import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class Parks
{
    public static final BuildingType zoo = createBuildingType(createUnanimated("parks/zoo"), 4, Zones.parks, 100);
}
