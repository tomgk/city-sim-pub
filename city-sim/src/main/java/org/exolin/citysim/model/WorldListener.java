package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A listener for world properties that has a different method for each property.
 *
 * @author Thomas
 * @see GenericWorldListener
 */
public interface WorldListener
{
    default public void onCityNameChanged(String name, String cityName){}
    default public void onNeedElectricity(String name, boolean needElectricity){}
    default public void onMoneyChanged(String name, BigDecimal money){}
    default public void onSimSpeedChanged(String name, SimulationSpeed simulationSpeed){}
    default public void onStructureCount(String name, int structureCount){}
    default public void onLastMoneyUpdateChange(String name, long lastMoneyUpdateChanged){}
    default public void onLastChangeDate(String name, LocalDate localDate){}
    default public void onLastChangeTime(String name, LocalTime localTime){}
    default public void onElectricityCoverageChanged(String name, String string){}
}
