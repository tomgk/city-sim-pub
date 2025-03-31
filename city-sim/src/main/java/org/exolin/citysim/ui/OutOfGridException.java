package org.exolin.citysim.ui;

/**
 *
 * @author Thomas
 */
public class OutOfGridException extends IllegalArgumentException
{
    public OutOfGridException(String message)
    {
        super(message);
    }
}
