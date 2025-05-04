package org.exolin.citysim.ui;

import org.exolin.citysim.utils.Utils;

/**
 *
 * @author Thomas
 */
public enum WorldView
{
    OVERGROUND,
    ZONES;
    
    private static final WorldView[] values = values();
    
    public WorldView getPrev()
    {
        return Utils.getPrev(values, ordinal());
    }
    
    public WorldView getNext()
    {
        return Utils.getNext(values, ordinal());
    }
}
