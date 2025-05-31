package org.exolin.citysim.ui.budget;

import org.exolin.citysim.model.connection.regular.SelfConnectionType;

/**
 *
 * @author Thomas
 */
public class SelfConnectionCategory implements BudgetCategory
{
    private final SelfConnectionType type;

    public SelfConnectionCategory(SelfConnectionType type)
    {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof SelfConnectionCategory c)
            return type == c.type;
        else
            return false;
    }

    @Override
    public int hashCode()
    {
        return type.hashCode();
    }

    @Override
    public String getTitle()
    {
        return type.getName();
    }

    @Override
    public boolean isIncome()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return SelfConnectionType.class.getName()+"["+type.getName()+"]";
    }
}
