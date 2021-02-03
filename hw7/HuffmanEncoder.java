import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    /**
     * Map characters to their counts.
     * @param inputSymbols
     * @return a map
     * */
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> table = new HashMap<>();
        for (char c : inputSymbols) {
            if (table.get(c) == null) {
                table.put(c, 1);
            } else {
                table.put(c, table.get(c) + 1);
            }
        }
        return table;
    }

    public static void main(String[] args) {
        /**
         * 1. Read the file as 8 bit symbols.
         * 2. Build frequency table and use it to construct a binary decoding trie.
         * 3. Write the binary decoding trie to the .huf file and use it
         *    to create lookup table for encoding.
         * */
        char[] fileContent = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(fileContent);
        BinaryTrie decodingTrie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(decodingTrie);
        Map<Character, BitSequence> lookupTable = decodingTrie.buildLookupTable();
        /**
         * 4. Create a list of bitsequences for each symbol and assemble
         *    all bit sequences into one huge bit sequence.
         * 5. Write the huge bit sequence to the .huf file.
         * */
        List<BitSequence> lst = new ArrayList<>();
        for (char c : fileContent) {
            lst.add(lookupTable.get(c));
        }
        BitSequence allBits = BitSequence.assemble(lst);
        ow.writeObject(allBits);
    }

    private void checkSize() {
        /**
         *  Check how much space is saved after compressing
         *  */
        List<List<File>> list = new ArrayList<>();
        List<File> temp = new ArrayList<>();
        temp.add(new File("watermelonsugar.txt"));
        temp.add(new File("watermelonsugar.txt.huf"));
        list.add(temp);

        List<File> temp2 = new ArrayList<>();
        temp2.add(new File("signalalarm.txt"));
        temp2.add(new File("signalalarm.txt.huf"));
        list.add(temp2);

        for (List<File> l : list) {
            System.out.format("%s"
                            + " has been compressed by "
                            + "%.2f" + "%% %n", l.get(0),
                    (1 - (double) l.get(1).length() / l.get(0).length()) * 100);
        }
    }

}
