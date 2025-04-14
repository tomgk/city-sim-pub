package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import org.exolin.citysim.model.ActualBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;

/**
 *
 * @author Thomas
 */
public class Parks
{
    public static final ActualBuildingType zoo = createBuildingType(createUnanimated("parks/zoo"), 4, Zones.parks, 100);
}
