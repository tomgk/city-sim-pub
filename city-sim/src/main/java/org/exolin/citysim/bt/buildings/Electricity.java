package org.exolin.citysim.bt.buildings;

/**
 *
 * @author Thomas
 */
public enum Electricity
{
    /**
     * blocks electricity
     */
    INSULATOR,
    /**
     * transfers electricity but doesn't need it and only does it one tile
     */
    CONDUCTS,
    /**
     * needs electricity; also transfers it
     */
    NEEDS,
    /**
     * transfers electricity but doesn't need it
     */
    TRANSFER;

    public boolean needs()
    {
        return this == NEEDS;
    }
    
    public boolean transfers()
    {
        return this == NEEDS || this == TRANSFER;
    }

    public boolean conducts()
    {
        return this != INSULATOR;
    }

    static Electricity greater(Electricity ex, Electricity ey)
    {
        if(ex.ordinal() > ey.ordinal())
            return ex;
        else
            return ey;
    }
}
