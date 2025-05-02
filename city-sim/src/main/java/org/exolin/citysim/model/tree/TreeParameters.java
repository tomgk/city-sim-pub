package org.exolin.citysim.model.tree;

import org.exolin.citysim.model.StructureParameters;

/**
 *
 * @author Thomas
 */
public class TreeParameters implements StructureParameters<TreeParameters>
{
    @Override
    public TreeParameters copy()
    {
        return new TreeParameters();
    }
}
