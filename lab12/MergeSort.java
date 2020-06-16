import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> qOfQs = new Queue<>();
        if (!items.isEmpty()) {
            for (Item i : items) {
                Queue<Item> temp = new Queue<>();
                temp.enqueue(i);
                qOfQs.enqueue(temp);
            }
            return qOfQs;
        }
        return null;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> mergedQ = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            mergedQ.enqueue(MergeSort.getMin(q1, q2));
        }
        return mergedQ;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> qOfQs = makeSingleItemQueues(items);
        Queue<Queue<Item>> temp = new Queue<>();
        int index = 0;
        Queue<Item> q1 = new Queue<>(), q2 = new Queue<>();
        while (qOfQs.size() > 1) {
            for (Queue<Item> q : qOfQs) {
                if (index % 2 == 0) {
                    q1 = qOfQs.dequeue();
                } else {
                    q2 = qOfQs.dequeue();
                    temp.enqueue(mergeSortedQueues(q1, q2));
                }
                index++;
            }
            if (index % 2 == 1) {
                temp.enqueue(mergeSortedQueues(q1, new Queue<>()));
            }
            qOfQs = temp;
        }

        return qOfQs.dequeue();
    }

    public static void main(String[] args) {
        Queue<Integer> unsortedInts = new Queue<>();
        Queue<Character> unsortedInts2 = new Queue<>();
        for (int i = 0; i < 20; i++) {
            int temp = (int) (Math.random() * 50);
            unsortedInts.enqueue(temp);
            unsortedInts2.enqueue((char) temp);
        }
        System.out.println(unsortedInts.toString());
        System.out.println(unsortedInts2.toString());

        System.out.println(MergeSort.mergeSort(unsortedInts));
        System.out.println(MergeSort.mergeSort(unsortedInts2));
    }
}
