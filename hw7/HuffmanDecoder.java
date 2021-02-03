import java.util.ArrayList;
import java.util.List;

/**
 * Decode a compressed file specified at args[0
 * and write the result to args[1].
 */
public class HuffmanDecoder {
    public static void main(String[] args) {

        /**
         * 1: Read the Huffman coding trie.
         * 2: Read the massive bit sequence corresponding to the original txt.
         * 3: Repeat until there are no more symbols:
         *     4a: Perform a longest prefix match on the massive sequence.
         *     4b: Record the symbol in some data structure.
         *     4c: Create a new bit sequence containing the remaining unmatched bits.
         * 4: Write the symbols in some data structure to the specified file.
         * */
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie encodingTrie = (BinaryTrie) or.readObject();
        BitSequence bits = (BitSequence) or.readObject();

        List<Character> decodingList = new ArrayList<>();
        while (bits.length() > 0) {
            Match matchResult = encodingTrie.longestPrefixMatch(bits);
            decodingList.add(matchResult.getSymbol());
            bits = bits.allButFirstNBits(matchResult.getSequence().length());
        }
        char[] decodingCharArray = new char[decodingList.size()];
        for (int i = 0; i < decodingCharArray.length; i++) {
            decodingCharArray[i] = decodingList.get(i);
        }
        FileUtils.writeCharArray(args[1], decodingCharArray);
    }
}
