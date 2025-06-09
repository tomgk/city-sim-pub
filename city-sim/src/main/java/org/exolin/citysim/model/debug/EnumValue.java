package org.exolin.citysim.model.debug;

/**
 *
 * @author Thomas
 */
public abstract class EnumValue<E extends Enum<E>> implements Value<E>
{
    private final Class<E> type;

    public EnumValue(Class<E> type)
    {
        this.type = type;
    }

    @Override
    public Class<E> getType()
    {
        return type;
    }
}
