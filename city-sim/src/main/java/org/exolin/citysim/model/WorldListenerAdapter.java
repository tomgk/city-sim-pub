package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.exolin.citysim.model.debug.Value;

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
            listener.onCityNameChanged(name, (String)value);
        else if(name.equals(World.PROPERTY_NEED_ELECTRICITY))
            listener.onNeedElectricity(name, (Boolean)value);
        else if(name.equals(World.PROPERTY_MONEY))
            listener.onMoneyChanged(name, (BigDecimal)value);
        else if(name.equals(World.PROPERTY_SIM_SPEED))
            listener.onSimSpeedChanged(name, (SimulationSpeed)value);
        else if(name.equals(World.PROPERTY_STRUCTURE_COUNT))
            listener.onStructureCount(name, (Integer)value);
        else if(name.equals(World.PROPERTY_LAST_MONEY_UPDATE))
            listener.onLastMoneyUpdateChange(name, (Long)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_DATE))
            listener.onLastChangeDate(name, (LocalDate)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_TIME))
            listener.onLastChangeTime(name, (LocalTime)value);
        else if(name.equals(World.PROPERTY_ELECTRICITY_COVERAGE))
            listener.onElectricityCoverageChanged(name, (String)value);
        else
            throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode()
    {
        return this.listener.hashCode();
    }

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
