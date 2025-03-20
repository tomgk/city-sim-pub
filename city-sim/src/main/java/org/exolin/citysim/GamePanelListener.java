package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public interface GamePanelListener
{
    public void created(GamePanel panel);

    public void zoomChanged(int zoom);

    public void offsetChanged(int xoffset, int yoffset);
    
}
