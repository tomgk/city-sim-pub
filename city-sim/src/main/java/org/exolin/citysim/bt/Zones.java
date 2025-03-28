package org.exolin.citysim.bt;

import org.exolin.citysim.ZoneType;
import static org.exolin.citysim.ui.Utils.loadImage;

/**
 *
 * @author Thomas
 */
public class Zones
{
    public static final ZoneType zone_residential = createZone("zone_residential");
    public static final ZoneType zone_business = createZone("zone_business");
    public static final ZoneType zone_industrial = createZone("zone_industrial");
    
    private static ZoneType createZone(String name)
    {
        return new ZoneType(name, loadImage(name), 1);
    }

    static void init()
    {
        BuildingTypes.init();
    }
}
