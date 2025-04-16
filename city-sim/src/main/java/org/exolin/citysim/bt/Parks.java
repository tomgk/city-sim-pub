package org.exolin.citysim.bt;

import static org.exolin.citysim.bt.Buildings.createBuildingType;
import static org.exolin.citysim.model.Animation.createUnanimated;
import org.exolin.citysim.model.ab.ActualBuildingType;

/**
 *
 * @author Thomas
 */
public class Parks
{
    public static final ActualBuildingType zoo = createBuildingType(createUnanimated("parks/zoo"), 4, Zones.parks, 100);
}
