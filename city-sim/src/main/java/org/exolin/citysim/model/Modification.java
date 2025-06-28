package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public interface Modification
{
    /**
     * Before an world update gets executed
     * 
     * @param w world
     * @param ticks newly passed ticks
     * @param passedTicks total passed ticks
     */
    public void beforeUpdate(World w, int ticks, long passedTicks);
    
    /**
     * After an world update gets executed
     * 
     * @param w world
     * @param ticks newly passed ticks
     * @param passedTicks total passed ticks
     */
    public void afterUpdate(World w, int ticks, long passedTicks);
}
