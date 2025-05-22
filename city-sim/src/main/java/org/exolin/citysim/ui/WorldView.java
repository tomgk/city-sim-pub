package org.exolin.citysim.ui;

import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public enum WorldView
{
    OVERGROUND,
    ZONES,
    ELECTRICITY;
    
    private static final WorldView[] values = values();
    
    public WorldView getPrev()
    {
        return Utils.getPrev(values, ordinal());
    }
    
    public WorldView getNext()
    {
        return Utils.getNext(values, ordinal());
    }
    
    public String getTitle()
    {
        return name().charAt(0)+name().substring(1).toLowerCase();
    }
}
