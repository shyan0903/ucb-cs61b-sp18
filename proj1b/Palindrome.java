import java.util.Deque;
import java.util.LinkedList;

/* A class for palindrome operations. */
public class Palindrome {

    /* Returns a Deque where the characters in a String
    * appear in the same order. */
    public Deque<Character> wordToDeque(String word){
        Deque result = new LinkedList<Character>();
        for (int i = 0; i < word.length(); i++){
            result.addLast(word.charAt(i));
        }
        return result;
    }

    /* Checks if a word is a Palindrome, a word that is
    * the same whether it is read forwards or backwards. */
    public boolean isPalindrome(String word){
        // need to specify the type of the Deque for later code
        // to compile
        Deque<Character> forward = wordToDeque(word);
        boolean isPalindrome = true;
        for (int i = 0; i < word.length() / 2; i++){
            // need to cast char to Character to use the Character.equals()
            if (! ((Character) word.charAt(i)).equals(forward.removeLast())) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome;
    }

    /* Checks if a word is an off-by-1 palindrome.
    * For example: “flake” is an off-by-1 palindrome since “f” and “e”
    * are one letter apart, and “k” and “l” are one letter apart.*/
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> wd = wordToDeque(word);
        boolean isPalindrome = true;
        for (int i = 0; i < word.length() / 2; i++){
            // need to cast char to Character to use the Character.equals()
            if (! cc.equalChars(word.charAt(i), wd.removeLast())) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome;
    }

    /* A recursive version of isPalindrome. */
    public boolean isPalindromeRecursion(String word){
        Deque<Character> wordDeque = wordToDeque(word);
        return recursionHelper(wordDeque, true);
    }

    private boolean recursionHelper(Deque<Character> wordDeque, boolean result) {
        if (wordDeque.size() > 1 && result){
            return recursionHelper(wordDeque, wordDeque.removeFirst().equals(wordDeque.removeLast()));
        }
        return result;
    }
}
