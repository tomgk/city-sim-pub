package org.exolin.citysim.bt.buildings;

/**
 *
 * @author Thomas
 */
public enum Electricity
{
    NEEDS,
    TRANSFER,
    CONDUCTS,
    INSULATOR;

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
}
