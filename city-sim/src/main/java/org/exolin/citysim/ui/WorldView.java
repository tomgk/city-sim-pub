package org.exolin.citysim.ui;

/**
 *
 * @author Thomas
 */
public enum WorldView
{
    OVERGROUND,
    ZONES;
    
    private static final WorldView[] values = values();
    
    public WorldView getNext()
    {
        int num = ordinal()+1;
        if(num == values.length)
            num = 0;
        
        return values[num];
    }
}
