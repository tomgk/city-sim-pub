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
    public void onChanged(String name, Object value);

    default public void onAllChanged(List<Map.Entry<String, Value<?>>> values)
    {
        values.forEach(e -> onChanged(e.getKey(), e.getValue().get()));
    }
}
