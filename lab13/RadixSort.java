import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


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
        int maxLength = 0;
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
        /* Count the frequency of each ascii. */
        int[] counts = new int[256];
        for (String s : asciis) {
            if (index >= s.length()) {
                counts[(int) '_'] ++;
            } else {
                counts[(int) s.charAt(index)] ++;
            }
        }
        /* Fill the start array. */
        int[] start = new int[256];
        int position = 0;
        for (int i = 0; i < counts.length; i++) {
            start[i] = position;
            position += counts[i];
        }
        /* Copy the Strings to their sorted location. */
        String[] toReturn = new String[asciis.length];
        for (int i = 0; i < toReturn.length; i++) {
            String temp = asciis[i];
            if (index >= temp.length()) {
                toReturn[start[(int) '_']] = temp;
                start[(int) '_']++;
            } else {
                toReturn[start[(int) temp.charAt(index)]] = temp;
                start[(int) temp.charAt(index)]++;
            }
        }
        for (int i = 0; i < toReturn.length; i++) {
            asciis[i] = toReturn[i];
        }
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

    public static void main(String[] args) {
        String[] acsii1 = {"abc", "hot", "row", "apple", "amex", "zedd", "2r3f"};
        for (String i : RadixSort.sort(acsii1)) {
            System.out.println(i);
        }
        //System.out.println((int) 'A');
    }
}
