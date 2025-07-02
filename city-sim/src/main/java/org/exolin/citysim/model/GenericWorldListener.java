package org.exolin.citysim.model;

import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.debug.Value;

/**
 * A listener for world properties that has the same method for all properties.
 *
 * @author Thomas
 * @see WorldListener
 */
public interface GenericWorldListener
{
    /**
     * A property changes.
     * 
     * @param name name of the property
     * @param value new value of the property
     */
    public void onChanged(String name, Object value);
    
    /**
     * An item has been added to a list property.
     * 
     * @param name the name of the property
     * @param item the new item
     */
    public void onAdded(String name, Object item);
    
    /**
     * An item has been removed from a list property.
     * 
     * @param name the name of the property
     * @param item the removed item
     */
    public void onRemoved(String name, Object item);

    default public void onAllChanged(List<Map.Entry<String, Value<?>>> values)
    {
        values.forEach(e -> onChanged(e.getKey(), e.getValue().get()));
    }
}
