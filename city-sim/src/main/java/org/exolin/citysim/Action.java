package org.exolin.citysim;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Thomas
 */
public interface Action
{
    void mouseDown(Point gridPoint);
    void moveMouse(Point gridPoint);
    void releaseMouse(Point gridPoint);
    Rectangle getSelection();
}
