package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public interface GamePanelListener
{
    public void created(GamePanel panel);

    public void zoomChanged(int zoom, double zoomFactor);

    public void offsetChanged(int xoffset, int yoffset);
    
}
