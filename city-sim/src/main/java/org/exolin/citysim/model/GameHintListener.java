package org.exolin.citysim.model;

import java.util.List;

/**
 *
 * @author Thomas
 */
public interface GameHintListener extends WorldListener
{
    @Override
    public void onHintsChanged(List<String> list);
    
    @Override
    public void onHintAdded(String hint);
    
    @Override
    public void onHintRemoved(String hint);
}
