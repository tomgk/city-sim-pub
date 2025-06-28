package org.exolin.citysim.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Thomas
 */
public class CustomKey
{
    private static final Map<String, CustomKey> CUSTOM_KEYS = new LinkedHashMap<>();
    
    private final String name;
    private final Function<Object, String> formatter;

    public CustomKey(String name, Function<Object, String> formatter)
    {
        Objects.requireNonNull(name);
        Objects.requireNonNull(formatter);
        this.name = name;
        this.formatter = formatter;
    }

    public String getName()
    {
        return name;
    }
    
    public String formatValue(Object value)
    {
        return formatter.apply(value);
    }

    public static CustomKey createKey(String name, Function<Object, String> formatter)
    {
        return CUSTOM_KEYS.compute(name, (n, oldVal) -> {
            if(oldVal != null)
                throw new IllegalArgumentException("duplicate "+n);
            
            return new CustomKey(name, formatter);
        });
    }
}
