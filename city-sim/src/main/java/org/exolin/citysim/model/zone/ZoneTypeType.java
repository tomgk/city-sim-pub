package org.exolin.citysim.model.zone;

/**
 *
 * @author Thomas
 */
public class ZoneTypeType
{
    private final String name;
    private final boolean userPlaceableZone;
    private final int cost;

    public ZoneTypeType(String name, boolean userPlaceableZone, int cost)
    {
        this.name = name;
        this.userPlaceableZone = userPlaceableZone;
        this.cost = cost;
    }

    public String getName()
    {
        return name;
    }

    public boolean isUserPlaceableZone()
    {
        return userPlaceableZone;
    }

    public int getCost()
    {
        return cost;
    }
}
