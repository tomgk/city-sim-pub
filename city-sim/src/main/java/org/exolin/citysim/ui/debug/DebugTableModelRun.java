package org.exolin.citysim.ui.debug;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTable;
import org.exolin.citysim.model.debug.EnumValue;
import org.exolin.citysim.model.debug.ReadonlyValue;
import org.exolin.citysim.model.debug.Value;
import static org.exolin.citysim.ui.debug.DebugTableModel.createJTable;

/**
 *
 * @author Thomas
 */
public class DebugTableModelRun
{
    private static void output(String name, Object value)
    {
        System.out.println(name+"="+value+" ["+value.getClass().getSimpleName()+"]");
    }
    
    public static void main1(String[] args)
    {
        List<Map.Entry<String, Value<?>>> values = new ArrayList<>();
        
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
                output(name, value);
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
                output(name, value);
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
                output(name, value);
            }
        }
        
        class ReadonlyIntVal implements ReadonlyValue<Integer>
        {
            private final int val;

            public ReadonlyIntVal(int value)
            {
                this.val = value;
            }
            
            @Override
            public Integer get()
            {
                return val;
            }
        }
        
        class BigDecVal implements Value<BigDecimal>
        {
            private final String name;
            private BigDecimal val;

            public BigDecVal(String name, BigDecimal value)
            {
                this.name = name;
                this.val = value;
            }
            
            @Override
            public BigDecimal get()
            {
                return val;
            }

            @Override
            public void set(BigDecimal value)
            {
                this.val = value;
                output(name, value);
            }
        }
        
        enum YesNo{YES, NO, MAYBE}
        
        class EnumVal<E extends Enum<E>> extends EnumValue<E>
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
                output(name, value);
            }
        }
        
        values.add(new AbstractMap.SimpleImmutableEntry<>("cityName", new StrVal("cityName", "TestVille")));
        values.add(new AbstractMap.SimpleImmutableEntry<>("needElectricity", new BoolVal("needElectricity")));
        values.add(new AbstractMap.SimpleImmutableEntry<>("speed", new IntVal("speed", 3)));
        values.add(new AbstractMap.SimpleImmutableEntry<>("catastrophes", new EnumVal(YesNo.class, "catastrophes", YesNo.MAYBE)));
        values.add(new AbstractMap.SimpleImmutableEntry<>("money", new BigDecVal("money", BigDecimal.valueOf(10000))));
        values.add(new AbstractMap.SimpleImmutableEntry<>("stats.buildingCount", new ReadonlyIntVal(103)));
        
        JTable t = createJTable(values);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(t);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
