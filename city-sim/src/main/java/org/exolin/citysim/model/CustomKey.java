package org.exolin.citysim.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Thomas
 */
public class CustomKey
{
    private static final Map<String, CustomKey> CUSTOM_KEYS = new LinkedHashMap<>();
    
    private final String name;

    public CustomKey(String name)
    {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static CustomKey createKey(String name)
    {
        return CUSTOM_KEYS.compute(name, (n, oldVal) -> {
            if(oldVal != null)
                throw new IllegalArgumentException("duplicate "+n);
            
            return new CustomKey(name);
        });
    }
}
