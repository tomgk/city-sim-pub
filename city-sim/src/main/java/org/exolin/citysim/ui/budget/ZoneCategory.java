package org.exolin.citysim.ui.budget;

import org.exolin.citysim.model.ZoneType;

/**
 *
 * @author Thomas
 */
public class ZoneCategory implements BudgetCategory
{
    private final ZoneType zone;

    public ZoneCategory(ZoneType zone)
    {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ZoneCategory c)
            return zone == c.zone;
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        return zone.hashCode();
    }

    @Override
    public String getTitle()
    {
        return zone.getName();
    }

    @Override
    public boolean isIncome()
    {
        return zone.isUserPlaceableZone();
    }
}
