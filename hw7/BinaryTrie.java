import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    private Node start;
    /**
     * Build a Huffman decoding trie given a frequency table
     * which maps symbols of type V to their relative frequencies.
     * @param frequencyTable
     * */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (Character c : frequencyTable.keySet()) {
            pq.add(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.add(new Node('\0',
                    left.frequency + right.frequency, left, right));
        }
        start = pq.poll();
    }

    /**
     * A private inner class defining Node in the Trie.
     * */
    private class Node implements Comparable<Node>, Serializable {
        char symbol;
        int frequency;
        Node left, right;
        String sequence;

        Node(char sym, int freq, Node l, Node r) {
            symbol = sym;
            frequency = freq;
            left = l;
            right = r;
            sequence = "";
        }

        @Override
        public int compareTo(Node o) {
            return frequency - o.frequency;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    /**
     *  Find the longest prefix that matches the given querySequence
     *  and returns a Match object for that Match.
     *  @param querySequence
     *  @return a Match object
     *  */
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node current = start;
        int i = 0;
        String sequence = "";
        while (!current.isLeaf()) {
            sequence += querySequence.bitAt(i);
            current = (querySequence.bitAt(i) == 0) ? current.left : current.right;
            i++;
        }
        return new Match(new BitSequence(sequence), current.symbol);
    }


    /**
     * Return the inverse of the coding trie.
     * @return a map
     * */
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> encodingMap = new HashMap<>();
        buildHelper(encodingMap, start, "");
        return encodingMap;
    }

     /**
      * A private helper method to build lookup table
      * from Trie using recursion.
      * */
    private void buildHelper(Map<Character, BitSequence> map, Node n, String seq) {
        if (!n.isLeaf()) {
            buildHelper(map, n.left, seq + "0");
            buildHelper(map, n.right, seq + "1");
        } else {
            map.put(n.symbol, new BitSequence(seq));
        }

    }
}
