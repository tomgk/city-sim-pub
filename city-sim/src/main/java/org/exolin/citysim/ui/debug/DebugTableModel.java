package org.exolin.citysim.ui.debug;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.exolin.citysim.model.debug.Value;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.ui.WorldHolder;
import org.exolin.citysim.model.GenericWorldListener;

/**
 *
 * @author Thomas
 */
public final class DebugTableModel implements TableModel, GenericWorldListener
{
    private List<Map.Entry<String, Value<?>>> entries;
    private final Map<String, Integer> indexes = new LinkedHashMap<>();

    @SuppressWarnings("LeakingThisInConstructor")
    public DebugTableModel(WorldHolder w, Supplier<List<Entry<String, Value<?>>>> getValues)
    {
        setEntries(getValues.get());
        if(w != null)
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
    
    static final int WRITABLE = 0;
    static final int TYPE = 1;
    static final int NAME = 2;
    static final int VALUE = 3;
    static final int COUNT = 4;

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
        return new DebugTable(new DebugTableModel(wh, getValues), getValues, allColumns);
    }
    
    public static void main(String[] args)
    {
        DebugTableModelRun.main1(args, false);
        DebugTableModelRun.main1(args, true);
    }
}
