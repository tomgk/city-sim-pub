package org.exolin.citysim.model.fire;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.utils.RandomUtils;

/**
 *
 * @author Thomas
 */
public class FireVariant implements StructureVariant
{
    private final int version;

    public FireVariant(int version)
    {
        this.version = version;
    }

    private static final List<FireVariant> VALUES = IntStream.range(1, 17)
            .mapToObj(i -> new FireVariant(i))
            .toList();

    public static final FireVariant V1 = VALUES.getFirst();

    public static int getVariantCount()
    {
        return VALUES.size();
    }

    public static FireVariant random()
    {
        return RandomUtils.random(VALUES);
    }

    @Override
    public int index()
    {
        return version-1;
    }

    @Override
    public String name()
    {
        return "V"+version;
    }
    
    public static Set<FireVariant> values()
    {
        //TODO: no copy?
        return new LinkedHashSet<>(VALUES);
    }

    public static FireVariant valueOf(String name)
    {
        for(FireVariant v : VALUES)
        {
            if(v.name().equals(name))
                return v;
        }

        throw new NoSuchElementException();
    }
}