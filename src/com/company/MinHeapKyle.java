package com.company;

import java.util.ArrayList;

public class MinHeapKyle <T extends Comparable<T>> {
    private int size;
    private ArrayList<T> heap;

    public MinHeapKyle() {
       size = 0;
       heap = new ArrayList<>();
    }

    public void print() {
        System.out.print("View:");
        for ( T i : heap ) System.out.print(i + ", ");
        System.out.println( size );
    }

    public void add( T element ) {
        /*
         * Bug code: 54: "minheap.add(item)" -> causes it to append directly to the end of memory instead of the end
         * of the min heap... this causes issues because old elements aren't deleted so the memory is generally larger
         * than the heap, so we get "old" values that should be removed in the position of the new element
         *
         * Potential fix: 54: "minheap.add( size, item )" -> causes it to append to the end of the heap not memory ( but
         * this moves every element after the one we inserted, so it is O(n) )
         * Unfortunately this leads to the min heap taking a lot of space in memory and it runs in O(n) (Not amortised, every time)
         * But it does fix the issue of getting "random"/old values that shouldn't be in the heap causing unexpected behaviour
         *
         * To this memory "leak" we need to change "minheap.add(item)" to lines 47-49
         * What this does is if the index already exists in memory we can change its value otherwise we add memory (to the end) and include the new value there
         */
        if ( size < heap.size() )
            heap.set(size, element);            // According to java15 doc "The size, isEmpty, get, set, iterator, and listIterator operations run in constant O(1) time."
        else heap.add( element );               // According to java15 doc "The add operation runs in amortized constant time, that is, adding n elements requires O(n) time." Amortized means this will generally be O(1) unless the internal array grows in which case it is O(n)


        bubbleUp( size );

        size++;
    }

    public void bubbleUp( int currentPos ) {
        if ( currentPos > 0 && heap.get( currentPos ).compareTo( heap.get(Parent(currentPos)) ) < 0 ) {
            swap(currentPos, Parent(currentPos));
            bubbleUp( Parent(currentPos) );
        }
    }

    public T poll() {
        T top = heap.get(0);

        heap.set( 0, heap.get( size - 1 ) );

        size--;

        bubbleDown(0);

        return top;
    }

    public void bubbleDown( int currentPos ) {
        int smallest = currentPos;

        if ( leftChild( currentPos ) < size && heap.get( leftChild( currentPos ) ).compareTo( heap.get( smallest  ) ) < 0 )
            smallest = leftChild( currentPos );

        if ( rightChild( currentPos ) < size && heap.get( rightChild( currentPos ) ).compareTo( heap.get( smallest ) ) < 0 )
            smallest = rightChild( currentPos );

        if ( heap.get(smallest).compareTo( heap.get(currentPos) ) < 0 ) {
            swap( smallest, currentPos );
            bubbleDown( smallest );
        }
    }

    public boolean isEmpty () {
        return size == 0;
    }

    private void swap( int pos1, int pos2 ) {
        T saved = heap.get(pos1);
        heap.set(pos1, heap.get(pos2));
        heap.set(pos2, saved);
    }
    private int Parent( int pos ) { return (pos-1)/2; }
    private int leftChild( int pos ) { return (pos*2)+1; }
    private int rightChild( int pos ) { return (pos*2)+2; }
}
