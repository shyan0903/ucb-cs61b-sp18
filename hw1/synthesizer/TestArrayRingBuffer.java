package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testAll() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        arb.enqueue("a");
        arb.enqueue("b");
        arb.enqueue("c");
        arb.enqueue("d");
        arb.enqueue("e");

        assertEquals(5, arb.fillCount());
        assertEquals("a", arb.dequeue());
        assertEquals(4, arb.fillCount());
        assertEquals("b", arb.dequeue());

        arb.enqueue("aAgain");
        assertEquals("c", arb.peek());
        assertNotEquals("d", arb.dequeue());
        assertEquals("d", arb.dequeue());
        assertEquals("e", arb.dequeue());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
