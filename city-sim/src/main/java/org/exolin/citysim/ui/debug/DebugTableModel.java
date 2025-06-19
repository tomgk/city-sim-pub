package org.exolin.citysim.ui.debug;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.exolin.citysim.model.WorldListener;
import org.exolin.citysim.model.debug.Value;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.ui.WorldHolder;

/**
 *
 * @author Thomas
 */
public final class DebugTableModel implements TableModel, WorldListener
{
    private List<Map.Entry<String, Value<?>>> entries;
    private final Map<String, Integer> indexes = new LinkedHashMap<>();

    @SuppressWarnings("LeakingThisInConstructor")
    public DebugTableModel(WorldHolder w)
    {
        setEntries(w.get().getValues());
        w.addWorldListener(this);
    }

    public DebugTableModel(List<Map.Entry<String, Value<?>>> entries)
    {
        setEntries(entries);
    }

    private void setEntries(List<Entry<String, Value<?>>> entries)
    {
        this.entries = Objects.requireNonNull(entries);
        this.indexes.clear();
        for(int i=0;i<entries.size();++i)
            indexes.put(entries.get(i).getKey(), i);
    }
    
    private void fire(TableModelEvent e)
    {
        listeners.forEach(l -> l.tableChanged(e));
    }

    /**
     * Updates one row in the table
     * 
     * @param name the name of the property
     * @param value 
     */
    @Override
    public void onChanged(String name, Object value)
    {
        int index = indexes.get(name);
        if(index < 0)
            ErrorDisplay.show(null, new IllegalStateException("unknown "+name));
        else
            fire(new TableModelEvent(this, index, index, VALUE));
    }
    
    @Override
    public void onAllChanged(List<Map.Entry<String, Value<?>>> values)
    {
        setEntries(values);
        fire(new TableModelEvent(this));
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

    /**
     * Sets the value of the variable in row {@code rowIndex}.
     * Since only the value can be changed,
     * {@code columnIndex} can only be {@link #VALUE},
     * otherwise an exception will be thrown
     * 
     * @param newValue new value
     * @param rowIndex row index
     * @param columnIndex column index, only {@link #VALUE} is supported
     * @throws IllegalArgumentException if {@code columnIndex}!={@link #VALUE}
     */
    @Override
    public void setValueAt(Object newValue, int rowIndex, int columnIndex)
    {
        if(columnIndex != VALUE)
            throw new IllegalArgumentException();
        
        Value value = entries.get(rowIndex).getValue();
        value.set(newValue);
    }
    
    private final List<TableModelListener> listeners = new ArrayList<>();

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        listeners.remove(l);
    }
    
    public static JTable createJTable(WorldHolder wh, Supplier<List<Entry<String, Value<?>>>> getValues, boolean allColumns)
    {
        JTable t = new JTable(new DebugTableModel(wh)){
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
        DebugTableModelRun.main1(args, false);
        DebugTableModelRun.main1(args, true);
    }
}
