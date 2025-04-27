package org.exolin.citysim.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Thomas
 */
public interface StructureVariant
{
    default int index()
    {
        return ((Enum)this).ordinal();
    }
    String name();
    
      
    /**
     * Returns how many variants there are of the same type.
     * <p>
     * If the variant is implemented by a simple enum,
     * then it is {@code EnumType.values().length}
     * 
     * @param clazz
     * @return number of variants of same type
     */
    static int getVariantCount(Class<? extends StructureVariant> clazz)
    {
        if(clazz.isEnum())
            return clazz.getEnumConstants().length;
        
        if(!StructureVariant.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException(clazz.getName()+" is not a "+StructureVariant.class.getName());
        
        try{
            Method m = clazz.getMethod("getVariantCount");
            return (Integer)m.invoke(null);
        }catch(NoSuchMethodException|IllegalAccessException e){
            throw new IllegalArgumentException("Couldn't get count from "+clazz.getName(), e);
        }catch(InvocationTargetException e){
            Throwable cause = e.getCause();
            if(cause instanceof RuntimeException re)
                throw re;
            
            throw new IllegalArgumentException("Error while getting count", e.getCause());
        }
    }
}
