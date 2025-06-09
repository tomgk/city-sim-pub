package org.exolin.citysim.ui;

import java.awt.Component;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import org.exolin.citysim.model.Value;

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
        return 3;
    }
    
    private static final int TYPE = 0;
    private static final int NAME = 1;
    private static final int VALUE = 2;

    @Override
    public String getColumnName(int columnIndex)
    {
        return switch(columnIndex)
        {
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
            case TYPE -> String.class;
            case NAME -> String.class;
            case VALUE -> Object.class;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex == VALUE;
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
        };
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        
    }
    
    public static JTable createJTable(List<Entry<String, Value<?>>> values)
    {
        return new JTable(new DebugTableModel(values)){
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
    }
    
    public static void main(String[] args)
    {
        List<Entry<String, Value<?>>> values = new ArrayList<>();
        
        class BoolVal implements Value<Boolean>
        {
            private final String name;
            private boolean val;

            public BoolVal(String name)
            {
                this.name = name;
            }
            
            @Override
            public Boolean get()
            {
                return val;
            }

            @Override
            public void set(Boolean value)
            {
                this.val = value;
                System.out.println(name+"="+value);
            }
        }
        
        class StrVal implements Value<String>
        {
            private final String name;
            private String val;

            public StrVal(String name, String value)
            {
                this.name = name;
                this.val = value;
            }
            
            @Override
            public String get()
            {
                return val;
            }

            @Override
            public void set(String value)
            {
                this.val = value;
                System.out.println(name+"="+value);
            }
        }
        
        class IntVal implements Value<Integer>
        {
            private final String name;
            private int val;

            public IntVal(String name, int value)
            {
                this.name = name;
                this.val = value;
            }
            
            @Override
            public Integer get()
            {
                return val;
            }

            @Override
            public void set(Integer value)
            {
                this.val = value;
                System.out.println(name+"="+value);
            }
        }
        
        enum YesNo{YES, NO, MAYBE}
        
        class EnumVal<E extends Enum<E>> extends Value.EnumValue<E>
        {
            private final String name;
            private E val;

            public EnumVal(Class<E> type, String name, E value)
            {
                super(type);
                this.name = name;
                this.val = value;
            }
            
            @Override
            public E get()
            {
                return val;
            }

            @Override
            public void set(E value)
            {
                this.val = value;
                System.out.println(name+"="+value);
            }
        }
        
        values.add(new AbstractMap.SimpleImmutableEntry<>("cityName", new StrVal("cityName", "TestVille")));
        values.add(new AbstractMap.SimpleImmutableEntry<>("needElectricity", new BoolVal("needElectricity")));
        values.add(new AbstractMap.SimpleImmutableEntry<>("speed", new IntVal("speed", 3)));
        values.add(new AbstractMap.SimpleImmutableEntry<>("catastrophes", new EnumVal(YesNo.class, "catastrophes", YesNo.MAYBE)));
        
        JTable t = createJTable(values);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(t);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
