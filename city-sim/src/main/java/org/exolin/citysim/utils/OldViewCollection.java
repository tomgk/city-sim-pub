package org.exolin.citysim.utils;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A collection that allows modification during iteration,
 * with the iterator only returning items that were in the
 * collection at the time of creation of the iterator and
 * have not been removed yet.
 * <p>
 * 
 * <em>IMPORTANT</em>: The call to {@link Iterator#next()} has to be called
 * after {@link Iterator#hasNext()} without removing elements from the collection
 * because otherwise, the call to {@link Iterator#next()} might change
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
        private final int logicalIndex;

        public Entry(T object, int version)
        {
            this.object = object;
            this.logicalIndex = version;
        }
    }
    
    //TODO: can't deal with modification outside of Iterator
    //potential fix: give each entry an own version,
    //basically having each entry the index in the collection if nothing ever
    //got removed. Then a check can be added, if the currently pointed to element
    //is the expected one, and if not, search into the direction where the correct element is
    //(up, if the currently pointed element is a lower version, down if it is too high)
    private class Iter implements Iterator<T>
    {
        private int pointer;
        private int logicalIndex;
        private final int maxIndex;

        public Iter(int maxLogicalIndex)
        {
            this.pointer = 0;
            this.logicalIndex = entries.getFirst().logicalIndex;
            this.maxIndex = maxLogicalIndex;
        }
        
        private int getLogicalIndexAt(int index)
        {
            return entries.get(index).logicalIndex;
        }
        
        /**
         * Moves the {@link #pointer} to the appropriate entry for
         * {@link #logicalIndex}.
         * 
         * This changes the {@link #logicalIndex}, if that entry has been removed.
         */
        private void adjustPointer()
        {
            int actLogicalIndex = getLogicalIndexAt(pointer);
            
            //current log. index not high enoug -> go further
            if(logicalIndex > actLogicalIndex)
            {
                while(logicalIndex > actLogicalIndex)
                {
                    ++pointer;
                    actLogicalIndex = getLogicalIndexAt(pointer);
                }
                
                //previously pointed to element might no longer be here
                //then point to whatever came after
                logicalIndex = actLogicalIndex;
            }
            //
            else if(logicalIndex < actLogicalIndex)
            {
                //TODO: when going back, sometimes it's needed to go forward one again
                //example:
                //  logicalIndex = 4
                //  entries = [1, 2, 5, 6]
                //in this case logicalIndex should point to 5, since 4 was deleted
                while(logicalIndex < actLogicalIndex)
                {
                    --pointer;
                    actLogicalIndex = getLogicalIndexAt(pointer);
                }
                
                //if target was deleted -> move to next,
                //since that is what should be pointed to after a deletion
                if(logicalIndex != actLogicalIndex)
                {
                    ++pointer;
                    actLogicalIndex = getLogicalIndexAt(pointer);
                }
                
                //previously pointed to element might no longer be here
                //then point to whatever came after
                logicalIndex = actLogicalIndex;
            }
        }
        
        @Override
        public boolean hasNext()
        {
            adjustPointer();
            return logicalIndex <= maxIndex;
        }

        @Override
        public T next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            
            adjustPointer();
            
            T val = entries.get(pointer).object;
            
            //move to next
            ++pointer;
            logicalIndex = entries.get(pointer).logicalIndex;
            
            return val;
        }

        @Override
        public void remove()
        {
            //TODO: check if next has been called before
            
            adjustPointer();
            
            entries.remove(pointer);
            //the element that was after the removed one is now in place
            //this move could move it beyond maxIndex, but that gets checked for
            //in readNext()
            logicalIndex = getLogicalIndexAt(pointer);
            
            //pointer stays the same, since it already moved to the next element
            
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
        return new Iter(addCount);
    }

    @Override
    public int size()
    {
        return entries.size();
    }

    @Override
    public boolean contains(Object o)
    {
        return entries.stream().anyMatch(e -> Objects.equals(e.object, o));
    }

    @Override
    public void clear()
    {
        entries.clear();
    }

    @Override
    public Object[] toArray()
    {
        //this optimization only works with the Object[] version of toArray
        //where wrapped and unwrapped fit into it
        Object[] arr = entries.toArray();
        for(int i=0;i<arr.length;++i)
            arr[i] = ((Entry<T>)arr[i]).object;
        return arr;
    }
}
