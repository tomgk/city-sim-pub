package org.exolin.citysim.model.sim;

/**
 *
 * @author Thomas
 */
public enum RemoveMode
{
    /**
     * removes zones and any building with a zone, keeps everything else;
     * no replacement for removals
     */
    REMOVE_ZONING,

    /**
     * removes buildings, leaves back zones if zoned
     */
    TEAR_DOWN,

    /**
     * remove, leave nothing behind, expect outside of (x,y)
     */
    CLEAR
}
