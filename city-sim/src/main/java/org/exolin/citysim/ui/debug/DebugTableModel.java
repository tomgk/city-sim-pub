package org.exolin.citysim.ui.debug;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.exolin.citysim.model.debug.Value;

/**
 *
 * @author Thomas
 */
public class DebugTableModel implements TableModel
{
    private final List<Map.Entry<String, Value<?>>> entries;

    public DebugTableModel(List<Map.Entry<String, Value<?>>> entries)
    {
        this.entries = Objects.requireNonNull(entries);
    }
    
    @Override
    public int getRowCount()
    {
        return entries.size();
    }

    @Override
    public int getColumnCount()
    {
        return COUNT;
    }
    
    private static final int WRITABLE = 0;
    private static final int TYPE = 1;
    private static final int NAME = 2;
    private static final int VALUE = 3;
    private static final int COUNT = 4;

    @Override
    public String getColumnName(int columnIndex)
    {
        return switch(columnIndex)
        {
            case WRITABLE -> "Writable";
            case TYPE -> "Type";
            case NAME -> "Name";
            case VALUE -> "Value";
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return switch(columnIndex)
        {
            case WRITABLE -> boolean.class;
            case TYPE -> String.class;
            case NAME -> String.class;
            case VALUE -> Object.class;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if(columnIndex != VALUE)
            return false;
        
        var entry = entries.get(rowIndex);
        
        var v = entry.getValue();
        return !v.isReadonly();
    }
    
    private static String getTypeName(Class<?> clazz)
    {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        var entry = entries.get(rowIndex);
        
        return switch(columnIndex)
        {
            case WRITABLE -> !entry.getValue().isReadonly();
            case TYPE -> getTypeName(entry.getValue().getType());
            case NAME -> entry.getKey();
            case VALUE -> entry.getValue().get();
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        var entry = entries.get(rowIndex);
        
        switch(columnIndex)
        {
            case VALUE -> ((Value)entry.getValue()).set(aValue);
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        
    }
    
    public static JTable createJTable(List<Entry<String, Value<?>>> values, boolean allColumns)
    {
        JTable t = new JTable(new DebugTableModel(values)){
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
                    
                    boolean editable = !values.get(row).getValue().isReadonly();
                    
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
        };
        
        t.getColumnModel().getColumn(WRITABLE).setPreferredWidth(40);
        t.getColumnModel().getColumn(NAME).setPreferredWidth(140);
        
        if(!allColumns)
        {
            TableColumnModel columnModel = t.getColumnModel();
            TableColumn writableColumn = columnModel.getColumn(WRITABLE);
            TableColumn typeColumn = columnModel.getColumn(TYPE);
            
            columnModel.removeColumn(writableColumn);
            columnModel.removeColumn(typeColumn);
        }
        
        return t;
    }
    
    public static void main(String[] args)
    {
        DebugTableModelRun.main1(args);
    }
}
