package org.exolin.citysim.utils;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class OldViewCollection<T> extends AbstractCollection<T>
{
    private final List<Entry<T>> entries = new ArrayList<>();
    private int currentVersion = 0;
    
    private static class Entry<T>
    {
        private final T object;
        private final int version;

        public Entry(T object, int version)
        {
            this.object = object;
            this.version = version;
        }
    }
    
    //TODO: can't deal with modification outside of Iterator
    private class Iter<T> implements Iterator<T>
    {
        private final Iterator<Entry<T>> iterator;
        private final int version;
        private Object next;
        private static final Object TO_BE_READ = new Object();
        private static final Object END = new Object();

        public Iter(Iterator<Entry<T>> iterator, int version)
        {
            this.iterator = iterator;
            this.version = version;
            this.next = TO_BE_READ;
        }
        
        private void readNext()
        {
            while(iterator.hasNext())
            {
                Entry<T> cur = iterator.next();
                if(cur.version <= version)
                {
                    next = cur.object;
                    return;
                }
            }
            
            next = END;
        }
        
        @Override
        public boolean hasNext()
        {
            if(next == TO_BE_READ)
                readNext();
            
            return next != END;
        }

        @Override
        public T next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            
            T val = (T)next;
            readNext();
            return val;
        }

        @Override
        public void remove()
        {
            iterator.remove();
            ++currentVersion;
        }
    }
    
    @Override
    public boolean add(T e)
    {
        ++currentVersion;
        entries.add(new Entry<>(e, currentVersion));
        return true;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iter<>(entries.iterator(), currentVersion);
    }

    @Override
    public int size()
    {
        return entries.size();
    }
}
