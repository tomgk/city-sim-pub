package org.exolin.citysim.model;

import java.util.List;
import java.util.Optional;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.model.zone.ZoneTypeType;

/**
 * Demand for resistendal, commercial and industrial
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
        int totalCount = 0;
        int rCount = 0;
        int cCount = 0;
        int iCount = 0;
        
        for(Structure<?, ?, ?, ?> s: structures)
        {
            Optional<ZoneType> zoneType = s.getTheZoneType();
            if(zoneType.isEmpty())
                continue;
            
            ZoneType z = zoneType.get();
            
            ZoneTypeType category = z.getCategory();
            int sizeq = s.getSize() * s.getSize();
            if(category == Zones.residential_category)
                rCount += sizeq;
            else if(category == Zones.business_category)
                cCount += sizeq;
            else if(category == Zones.industrial_category)
                iCount += sizeq;
            else
                continue;
            
            totalCount += sizeq;
        }
        
        if(totalCount == 0)
            set(100, 100, 100);
        else
            set(getRCI(rCount, totalCount), getRCI(cCount, totalCount), getRCI(iCount, totalCount));
    }

    private static int getRCI(int supply, int total)
    {
        int current = supply * 100 / total;
        return 100 - current;
    }
}
