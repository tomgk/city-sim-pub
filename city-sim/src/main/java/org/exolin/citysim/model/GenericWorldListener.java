package org.exolin.citysim.model;

import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.debug.Value;

/**
 * A listener for world properties that has the same methods for all properties.
 *
 * @author Thomas
 * @see WorldListener
 */
public interface GenericWorldListener
{
    /**
     * A property changes.
     * 
     * For list properties this method will always be called,
     * when an item gets added/removed.
     * Only implementing {@link #onAdded(java.lang.String, java.lang.Object)}
     * and {@link #onRemoved(java.lang.String, java.lang.Object)} is enough
     * if it keeps track of the changes, no need to implement
     * {@link #onChanged(java.lang.String, java.lang.Object)}, especially if
     * replacing everything is expensive.
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
    
    /**
     * Gets called if all properties have been changed at once.
     * 
     * @implSpec the default implementation just delegates to
     * {@link #onChanged(java.lang.String, java.lang.Object)}, calling it once per entry
     * 
     * @param values new list of properties, with name and value
     */
    default public void onAllChanged(List<Map.Entry<String, Value<?>>> values)
    {
        values.forEach(e -> onChanged(e.getKey(), e.getValue().get()));
    }
}
