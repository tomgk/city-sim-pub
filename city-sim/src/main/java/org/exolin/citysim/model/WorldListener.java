package org.exolin.citysim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * A listener for world properties that
 * has a different method for each property.
 *
 * @author Thomas
 * @see GenericWorldListener
 */
public interface WorldListener
{
    default public void onCityNameChanged(String cityName){}
    default public void onNeedElectricity(boolean needElectricity){}
    default public void onMoneyChanged(BigDecimal money){}
    default public void onSimSpeedChanged(SimulationSpeed simulationSpeed){}
    default public void onStructureCount(int structureCount){}
    default public void onLastMoneyUpdateChange(long lastMoneyUpdateChanged){}
    default public void onLastChangeDate(LocalDate localDate){}
    default public void onLastChangeTime(LocalTime localTime){}
    default public void onElectricityCoverageChanged(String string){}

    /**
     * gets called when the hints change, even if just one item got added/removed
     * 
     * @param list new list
     */
    default public void onHintsChanged(List<String> list){}
    
    /**
     * Gets called when a hint gets added, even if the whole list changes.
     * 
     * @param hint the new hint
     */
    default public void onHintAdded(String hint){}
    
    /**
     * Gets called when a hint gets deleted, even if the whole list changes.
     * 
     * @param hint 
     */
    default public void onHintRemoved(String hint){}
}
