package org.exolin.citysim;

import java.util.function.Consumer;

/**
 *
 * @author Thomas
 */
public class StringPrinter implements Printer
{
    private final StringBuilder out = new StringBuilder();

    @Override
    public void println(String str)
    {
        out.append(str).append("\n");
    }
        
    public static String print(Consumer<StringPrinter> out)
    {
        StringPrinter p = new StringPrinter();
        out.accept(p);
        return p.out.toString();
    }
}
