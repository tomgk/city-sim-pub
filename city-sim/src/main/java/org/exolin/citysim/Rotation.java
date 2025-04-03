package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public enum Rotation
{
    ORIGINAL,
    PLUS_90,
    PLUS_180,
    PLUS_270;
    
    private static final Rotation[] values = values();
    
    public Rotation getNext()
    {
        int num = ordinal()+1;
        if(num == values.length)
            num = 0;
        
        return values[num];
    }
}
