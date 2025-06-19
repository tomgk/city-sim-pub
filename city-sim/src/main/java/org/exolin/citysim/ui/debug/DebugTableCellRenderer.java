package org.exolin.citysim.ui.debug;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.exolin.citysim.model.debug.Value;

/**
 * Makes readonly variables gray and editable ones white
 * 
 * @author Thomas
 */
public class DebugTableCellRenderer implements TableCellRenderer
{
    private final TableCellRenderer r;
    private final Supplier<List<Map.Entry<String, Value<?>>>> getValues;

    public DebugTableCellRenderer(TableCellRenderer r, Supplier<List<Map.Entry<String, Value<?>>>> getValues)
    {
        this.r = r;
        this.getValues = getValues;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        //show non-editable entries in gray

        boolean editable = !getValues.get().get(row).getValue().isReadonly();

        Component c = r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int V = 220;
        c.setBackground(editable ? Color.white : new Color(V, V, V));
        return c;
    }
}
