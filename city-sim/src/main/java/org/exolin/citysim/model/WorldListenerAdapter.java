package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Adapter to turn a {@link WorldListener} into a {@link GenericWorldListener}.
 *
 * @author Thomas
 */
final class WorldListenerAdapter implements GenericWorldListener
{
    private final WorldListener listener;

    public WorldListenerAdapter(WorldListener listener)
    {
        this.listener = Objects.requireNonNull(listener);
    }
    
    @Override
    public void onChanged(String name, Object value)
    {
        if(name.equals(World.PROPERTY_CITY_NAME))
            listener.onCityNameChanged((String)value);
        else if(name.equals(World.PROPERTY_NEED_ELECTRICITY))
            listener.onNeedElectricity((Boolean)value);
        else if(name.equals(World.PROPERTY_MONEY))
            listener.onMoneyChanged((BigDecimal)value);
        else if(name.equals(World.PROPERTY_SIM_SPEED))
            listener.onSimSpeedChanged((SimulationSpeed)value);
        else if(name.equals(World.PROPERTY_STRUCTURE_COUNT))
            listener.onStructureCount((Integer)value);
        else if(name.equals(World.PROPERTY_LAST_MONEY_UPDATE))
            listener.onLastMoneyUpdateChange((Long)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_DATE))
            listener.onLastChangeDate((LocalDate)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_TIME))
            listener.onLastChangeTime((LocalTime)value);
        else if(name.equals(World.PROPERTY_ELECTRICITY_COVERAGE))
            listener.onElectricityCoverageChanged((String)value);
        else if(name.equals(World.PROPERTY_HINTS))
            listener.onHintsChanged((List<String>)value);
        else
            throw new UnsupportedOperationException();
    }

    @Override
    public void onAdded(String name, Object item)
    {
        if(name.equals(World.PROPERTY_HINTS))
            listener.onHintAdded((String)item);
        else
            throw new UnsupportedOperationException();
    }

    @Override
    public void onRemoved(String name, Object item)
    {
        if(name.equals(World.PROPERTY_HINTS))
            listener.onHintRemoved((String)item);
        else
            throw new UnsupportedOperationException();
    }

    //required to make remove(new WorldListenerAdapter(x)) work
    @Override
    public int hashCode()
    {
        return this.listener.hashCode();
    }

    //required to make remove(new WorldListenerAdapter(x)) work
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WorldListenerAdapter other = (WorldListenerAdapter) obj;
        return Objects.equals(this.listener, other.listener);
    }
}
