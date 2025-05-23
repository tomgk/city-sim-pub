package org.exolin.citysim.ui;

import org.exolin.citysim.model.RCI;
import org.exolin.citysim.ui.actions.Action;

/**
 *
 * @author Thomas
 */
public interface GamePanelListener
{
    public void created(GamePanel panel);
    public void onActionChanged(Action newAction);
    public void onRCIChanged(RCI rci);
}
