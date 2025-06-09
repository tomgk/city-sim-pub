package org.exolin.citysim.model.debug;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author Thomas
 * @param <T>
 */
public interface Value<T>
{
    T get();
    void set(T value);
    default boolean isReadonly()
    {
        return false;
    }
    
    default Class<T> getType()
    {
        Type[] xs = getClass().getGenericInterfaces();
        
        for(Type x : xs)
        {
            if(!(x instanceof ParameterizedType))
                continue;

            ParameterizedType superClass = (ParameterizedType) x;
            
            Class rt = (Class)superClass.getRawType();
            if(rt != Value.class && rt != ReadonlyValue.class)
                continue;
            
            for(Type t : superClass.getActualTypeArguments())
            {
                return (Class)t;
                //if(t instanceof Class c)
                //    if(StructureVariant.class.isAssignableFrom(c))
                //        return c;
            }
        }
        
        throw new UnsupportedOperationException();
    }
}
