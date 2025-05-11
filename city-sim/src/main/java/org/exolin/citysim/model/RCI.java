package org.exolin.citysim.model;

import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.model.zone.ZoneTypeType;

/**
 *
 * @author Thomas
 */
public class RCI
{
    private int r;
    private int c;
    private int i;

    public int getR()
    {
        return r;
    }

    public int getC()
    {
        return c;
    }

    public int getI()
    {
        return i;
    }

    void set(int r, int c, int i)
    {
        this.r = r;
        this.c = c;
        this.i = i;
    }
    
    void update(List<Structure<?, ?, ?, ?>> structures)
    {
        int total = 0;
        int r = 0;
        int c = 0;
        int i = 0;
        
        for(Structure<?, ?, ?, ?> s: structures)
        {
            Optional<ZoneType> zoneType = ZoneType.getZoneType(s);
            if(zoneType.isEmpty())
                continue;
            
            ZoneType z = zoneType.get();
            
            ZoneTypeType category = z.getCategory();
            int sizeq = s.getSize() * s.getSize();
            if(category == Zones.residential_category)
                r += sizeq;
            else if(category == Zones.business_category)
                c += sizeq;
            else if(category == Zones.industrial_category)
                i += sizeq;
            else
                continue;
            
            total += sizeq;
        }
        
        if(total == 0)
            set(100, 100, 100);
        else
            set(getRCI(r, total), getRCI(c, total), getRCI(i, total));
    }

    private static int getRCI(int supply, int total)
    {
        int current = supply * 100 / total;
        return 100 - current;
    }
}
