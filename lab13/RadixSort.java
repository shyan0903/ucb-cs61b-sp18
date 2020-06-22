import edu.princeton.cs.algs4.Queue;
import org.junit.Test;



/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxLength = Integer.MIN_VALUE;
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            maxLength = Math.max(asciis[i].length(), maxLength);
            sorted[i] = asciis[i];
        }

        for (int i = maxLength - 1; i >= 0; i--) {
            sortHelperLSD(sorted, i);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Create R = 256 queues to store keys based on the ascii value at index
        Queue<String>[] queues = new Queue[256];
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new Queue<>();
        }
        // Enqueue keys to the corresponding queue
        for (String s : asciis) {
            int asciiVal;
            if (index >= s.length()) {
                 asciiVal = 0;
            } else {
                asciiVal = s.charAt(index);
            }
            queues[asciiVal].enqueue(s);
        }
        // Dequeue keys in order of the queues and store in asciis destructively
        int k = 0;
        for (Queue<String> q : queues) {
            while (!q.isEmpty()) {
                asciis[k] = q.dequeue();
                k++;
            }
        }
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

//    public static void main(String[] args) {
//        String[] acsii1 = {"10", "3", "23", "99", "10002", "592", "1"};
//        for (String i : RadixSort.sort(acsii1)) {
//            System.out.println(i);
//        }
//    }
}
