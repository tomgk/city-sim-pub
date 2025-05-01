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
    private int addCount = 0;
    
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
    //potential fix: give each entry an own version,
    //basically having each entry the index in the collection if nothing ever
    //got removed. Then a check can be added, if the currently pointed to element
    //is the expected one, and if not, search into the direction where the correct element is
    //(up, if the currently pointed element is a lower version, down if it is too high)
    private class Iter<T> implements Iterator<T>
    {
        private final Iterator<Entry<T>> iterator;
        private final int logicalIndex;
        private Object next;
        private static final Object TO_BE_READ = new Object();
        private static final Object END = new Object();

        public Iter(Iterator<Entry<T>> iterator, int logicalIndex)
        {
            this.iterator = iterator;
            this.logicalIndex = logicalIndex;
            this.next = TO_BE_READ;
        }
        
        private void readNext()
        {
            while(iterator.hasNext())
            {
                Entry<T> cur = iterator.next();
                if(cur.version <= logicalIndex)
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
            //no change in addCount, since nothing new got added
        }
    }
    
    @Override
    public boolean add(T e)
    {
        ++addCount;
        entries.add(new Entry<>(e, addCount));
        return true;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iter<>(entries.iterator(), addCount);
    }

    @Override
    public int size()
    {
        return entries.size();
    }
}
