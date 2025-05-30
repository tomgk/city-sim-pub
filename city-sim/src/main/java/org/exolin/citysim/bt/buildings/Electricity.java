package org.exolin.citysim.bt.buildings;

/**
 *
 * @author Thomas
 */
public enum Electricity
{
    INSULATOR,
    NEEDS,
    TRANSFER,
    CONDUCTS;

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
