public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int nextFirst; // a pointer to the nextFirst position
    private int nextLast;  // a pointer to the nextLast position

    /* Creates a starting array of 8 memory boxes. */
    public ArrayDeque(){
        array = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /* Checks if the array is full of capacity. */
    private boolean isFull(){
        return size == array.length;
    }

    /* Update the index non-destructively by 1 circularly. */
    private int update(int index, String dir){
        if (dir == "add") {
            return (index + 1) % array.length;
        }
        return (index - 1 + array.length) % array.length;
    }

    /* Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (isFull()){
            resize();
        }
        array[nextFirst] = item;
        nextFirst = update(nextFirst, "minus");
        size++;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        if (isFull()){
            resize();
        }
        array[nextLast] = item;
        nextLast = update(nextLast, "add");
        size++;
    }

    /* Calculates the usage factor for arrays of length 16 or more. */
    private double usage(){
        if (array.length >= 16) {
            return size / array.length;
        }
        return 0.25;
    }

    /* Resizes the deque when necessary. */
    private void resize(){
        int refactor = 2;
        T[] newArray;
        // Expand the deque
        if (isFull()) {
            newArray = (T[]) new Object[size * refactor];
        }else{
            newArray = (T[]) new Object[size / refactor];
        }
        int old = update(nextFirst, "add");
        for(int i = 0; i < size; i++){
            newArray[i] = array[old];
            old = update(old, "add");
        }
        array = newArray;
        nextFirst = size * refactor - 1;
        nextLast = size;
    }

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /* Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        int i = update(nextFirst,"add");
        for (int j = 0; j < size; j++) {
            System.out.print(array[i] + " ");
            i = update(i, "add");
        }
        System.out.println();
    }

    /* Removes and returns the item at the front of the deque.
        If no such item exists, returns null. */
    public T removeFirst(){
        if (usage() < 0.25){
            resize();
        }
        nextFirst = update(nextFirst, "add");
        T removed = array[nextFirst];
        array[nextFirst] = null;
        if (!isEmpty()) {
            size--;
        }
        return removed;
    }

    /* Removes and returns the item at the back of the deque.
        If no such item exists, returns null. */
    public T removeLast(){
        if (usage() < 0.25){
            resize();
        }
        nextLast = update(nextLast, "minus");
        T removed = array[nextLast];
        array[nextLast] = null;
        if (!isEmpty()) {
            size--;
        }
        return removed;
    }

    /* Gets the item at the given index, where 0 is the front,
         1 is the next item, and so forth. If no such item exists,
         returns null. */
    public T get(int index){
        if (index < 0){
            return null;
        }
        return array[update(nextFirst,"add") + index];
    }
}
