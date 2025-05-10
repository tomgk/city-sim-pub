package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class RCI
{
    private int r;
    private int c;
    private int i;

    public int getR()
    {
        return r;
    }

    public int getC()
    {
        return c;
    }

    public int getI()
    {
        return i;
    }

    void set(int r, int c, int i)
    {
        this.r = r;
        this.c = c;
        this.i = i;
    }
}
