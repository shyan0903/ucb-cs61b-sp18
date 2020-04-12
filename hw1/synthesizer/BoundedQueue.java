package synthesizer;
import java.util.Iterator;
/** This interface defines the behaviors of a queue with fixed capacity.
 */

public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();     // Returns size of the buffer
    int fillCount();    // Returns number of items currently in the buffer
    void enqueue(T x);  // Adds item x to the end
    T dequeue();        // Deletes and returns item from the front
    T peek();           // Returns (but do not delete) item from the front
    Iterator<T> iterator();

    // Checks if the buffer is empty
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    // Checks if the buffer is full
    default boolean isFull() {
        return fillCount() == capacity();
    }


}
