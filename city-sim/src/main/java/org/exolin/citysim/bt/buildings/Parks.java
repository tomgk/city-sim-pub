package org.exolin.citysim.bt.buildings;

import java.math.BigDecimal;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.buildings.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import static org.exolin.citysim.model.StructureSize._4;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class Parks
{
    public static final BuildingType zoo = createBuildingType(createUnanimated("parks/zoo"), _4, Zones.parks, 100, BigDecimal.ONE);
}
