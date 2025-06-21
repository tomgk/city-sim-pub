package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.exolin.citysim.model.debug.Value;

/**
 *
 * @author Thomas
 */
public abstract class AbstractWorldListener implements GenericWorldListener
{
    @Override
    public void onChanged(String name, Object value)
    {
        if(name.equals(World.PROPERTY_CITY_NAME))
            onCityNameChanged(name, (String)value);
        else if(name.equals(World.PROPERTY_NEED_ELECTRICITY))
            onNeedElectricity(name, (Boolean)value);
        else if(name.equals(World.PROPERTY_MONEY))
            onMoneyChanged(name, (BigDecimal)value);
        else if(name.equals(World.PROPERTY_SIM_SPEED))
            onSimSpeedChanged(name, (SimulationSpeed)value);
        else if(name.equals(World.PROPERTY_STRUCTURE_COUNT))
            onStructureCount(name, (Integer)value);
        else if(name.equals(World.PROPERTY_LAST_MONEY_UPDATE))
            onLastMoneyUpdateChange(name, (Long)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_DATE))
            onLastChangeDate(name, (LocalDate)value);
        else if(name.equals(World.PROPERTY_LAST_CHANGE_TIME))
            onLastChangeTime(name, (LocalTime)value);
        else if(name.equals(World.PROPERTY_ELECTRICITY_COVERAGE))
            onElectricityCoverageChanged(name, (String)value);
        else
            throw new UnsupportedOperationException();
    }

    //only implemented to make it final
    @Override
    public final void onAllChanged(List<Map.Entry<String, Value<?>>> values)
    {
        GenericWorldListener.super.onAllChanged(values);
    }

    public void onCityNameChanged(String name, String cityName){}
    public void onNeedElectricity(String name, boolean needElectricity){}
    public void onMoneyChanged(String name, BigDecimal money){}
    public void onSimSpeedChanged(String name, SimulationSpeed simulationSpeed){}
    public void onStructureCount(String name, int structureCount){}
    public void onLastMoneyUpdateChange(String name, long lastMoneyUpdateChanged){}
    public void onLastChangeDate(String name, LocalDate localDate){}
    public void onLastChangeTime(String name, LocalTime localTime){}
    public void onElectricityCoverageChanged(String name, String string){}
}
