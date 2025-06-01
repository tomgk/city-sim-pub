package org.exolin.citysim.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;

/**
 * For a given {@link StructureType} the variant of it.
 * For example for a street variants can be straigth connection,
 * curves or intersections.
 * 
 * @author Thomas
 */
public interface StructureVariant
{
    /**
     * The index in the set of variants.
     * A {@link StructureVariant} acts like an enum (but doesn't need to be an
     * {@link Enum}.
     * 
     * @implSpec the default implementation assumes that an {@link Enum} is used
     * and just calls {@link Enum#ordinal()}. If the implementation doesn't use
     * {@link Enum} then this method has to be implemented
     * 
     * @return the index
     */
    default int index()
    {
        return ((Enum)this).ordinal();
    }
    
    /**
     * Returns the name, which has to be unique for the set of variants.
     * 
     * @return the name
     */
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
        if(!StructureVariant.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException(clazz.getName()+" is not a "+StructureVariant.class.getName());
        
        if(clazz.isEnum())
            return clazz.getEnumConstants().length;
        
        try{
            Method m = clazz.getMethod("getVariantCount");
            return (Integer)m.invoke(null);
        }catch(NoSuchMethodException|IllegalAccessException e){
            throw new IllegalArgumentException("Couldn't get count from "+clazz.getName(), e);
        }catch(InvocationTargetException e){
            throw handleInvocationTargetException(e);
        }
    }
    
    static Set<? extends StructureVariant> getValues(Class<? extends StructureVariant> clazz)
    {
        if(clazz.isEnum())
            return EnumSet.allOf((Class)clazz);
        
        try{
            Method values = clazz.getMethod("values");
            return (Set)values.invoke(null);
        }catch(NoSuchMethodException|IllegalAccessException e){
            throw new IllegalArgumentException("Couldn't get count from "+clazz.getName()+": "+e.getMessage(), e);
        }catch(InvocationTargetException e){
            throw handleInvocationTargetException(e);
        }
    }
    
    private static RuntimeException handleInvocationTargetException(InvocationTargetException e)
    {
        Throwable cause = e.getCause();
        if(cause instanceof RuntimeException re)
            throw re;

        throw new IllegalArgumentException("Error while getting count", e.getCause());
    }
}
