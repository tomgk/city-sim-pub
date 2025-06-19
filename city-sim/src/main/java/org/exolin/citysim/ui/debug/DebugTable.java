package org.exolin.citysim.ui.debug;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.exolin.citysim.model.debug.Value;
import static org.exolin.citysim.ui.debug.DebugTableModel.NAME;
import static org.exolin.citysim.ui.debug.DebugTableModel.TYPE;
import static org.exolin.citysim.ui.debug.DebugTableModel.VALUE;
import static org.exolin.citysim.ui.debug.DebugTableModel.WRITABLE;

/**
 *
 * @author Thomas
 */
public class DebugTable extends JTable
{
    private final Supplier<List<Map.Entry<String, Value<?>>>> getValues;
    
    public DebugTable(DebugTableModel model, Supplier<List<Map.Entry<String, Value<?>>>> getValues, boolean allColumns)
    {
        super(model);
        this.getValues = Objects.requireNonNull(getValues);
        
        getColumnModel().getColumn(WRITABLE).setPreferredWidth(40);
        getColumnModel().getColumn(NAME).setPreferredWidth(140);
        
        if(!allColumns)
        {
            TableColumnModel columnModel = getColumnModel();
            TableColumn writableColumn = columnModel.getColumn(WRITABLE);
            TableColumn typeColumn = columnModel.getColumn(TYPE);
            
            columnModel.removeColumn(writableColumn);
            columnModel.removeColumn(typeColumn);
        }
        
    }
    
    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass)
    {
        TableCellRenderer r = super.getDefaultRenderer(columnClass);
        if(r == null)
        {
            System.out.println("No default renderer for "+columnClass.getName());
            return null;
        }

        return (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) ->
        {
            //show non-editable entries in gray

            boolean editable = !getValues.get().get(row).getValue().isReadonly();

            Component c = r.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int V = 220;
            c.setBackground(editable ? Color.white : new Color(V, V, V));
            return c;
        };
    }

    private static final long serialVersionUID = 1L;
    private Class editingClass;

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        editingClass = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == VALUE) {
            Class rowClass = getModel().getValueAt(row, modelColumn).getClass();
            return getDefaultRenderer(rowClass);
        } else {
            return super.getCellRenderer(row, column);
        }
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        editingClass = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == VALUE) {
            editingClass = getModel().getValueAt(row, modelColumn).getClass();

            if(editingClass.isEnum())
                return new DefaultCellEditor(new JComboBox<>(editingClass.getEnumConstants()));

            return getDefaultEditor(editingClass);
        } else {
            return super.getCellEditor(row, column);
        }
    }
    //  This method is also invoked by the editor when the value in the editor
    //  component is saved in the TableModel. The class was saved when the
    //  editor was invoked so the proper class can be created.

    @Override
    public Class getColumnClass(int column) {
        return editingClass != null ? editingClass : super.getColumnClass(column);
    }
}
