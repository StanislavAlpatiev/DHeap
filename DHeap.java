

public class DHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;      // Number of elements in heap
    private T [ ] array; // The heap array
    private final int numberOfChildren;

    /**
     * Construct the binary heap.
     */
    public DHeap()
    {
        this.numberOfChildren = 2;
        array = (T[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
    }

    /**
     * Construct the binary heap.
     * @param d the number of children for node in binary heap.
     */
    public DHeap(int d)
    {
        if (d <= 1) {
            throw new IllegalArgumentException();
        }
        currentSize = 0;
        this.numberOfChildren = d;
        array = (T[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
    }

    public int size() {
        return currentSize;
    }

    T get(int index) {
        return array[index];
    }

    public int parentIndex(int index) {
        if (index == 1) {
            throw new IllegalArgumentException();
        }
        return index <= 1 ? -1 : (int) Math.ceil((index - 1.0) / numberOfChildren);
    }

    public int firstChildIndex(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException();
        }

        return numberOfChildren * index - numberOfChildren + 2;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( T x )
    {
        if( currentSize == array.length - 1 ) {
            enlargeArray(array.length * numberOfChildren + 1);
        }
        // Percolate up
        int hole = ++currentSize;
        array[0] = x;
        if (hole > 1) {
            while (parentIndex(hole) > 1 && x.compareTo(array[parentIndex(hole)]) < 0) {
                array[hole] = array[parentIndex(hole)];
                hole = parentIndex(hole);
            }
            if (parentIndex(hole) == 1 && x.compareTo(array[parentIndex(hole)]) < 0) {
                array[hole] = array[parentIndex(hole)];
                hole = parentIndex(hole);
            }
        }
        array[hole] = x;
    }


    private void enlargeArray( int newSize )
    {
        T [] old = array;
        array = (T []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public T findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public T deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        T minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }


    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
        int child;
        T tmp = array[hole];

        for(; firstChildIndex(hole) <= currentSize; hole = child)
        {
            child = firstChildIndex(hole);
            int indexOfLowestChild = firstChildIndex(hole);
            for (int i = 1; child + i <= currentSize && i < numberOfChildren; i++) {
                if(array[child + 1] != null) {
                    if (array[indexOfLowestChild].compareTo(array[child + i]) > 0) {
                        indexOfLowestChild = child + i;
                    }
                }
            }
            child = indexOfLowestChild;
            if(array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    public String printHeap () {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(T element : array) {
            sb.append(element);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(T element : array) {
            sb.append(element);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Test program
    public static void main( String [ ] args )
    {
        //        int numItems = 10000;
//        DHeap<Integer> h = new DHeap<Integer>( );
//        int i = 37;
//
//        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
//            h.insert( i );
//        System.out.print(h.printHeap());
//        for( i = 1; i < numItems; i++ )
//            if( h.deleteMin( ) != i )
//                System.out.println( "Oops! " + i );
//        int parent = (int) Math.ceil(5.0/3);
//        System.out.println(parent);
    }
}
