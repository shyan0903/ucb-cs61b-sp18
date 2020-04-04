import org.junit.Test;

import java.util.Deque;

import static org.junit.Assert.*;

/* A class for JUnit tests for Palindrome. */
public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("happy"));
        assertFalse(palindrome.isPalindrome("Racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("f", new OffByOne()));
        assertTrue(palindrome.isPalindrome("", new OffByOne()));
        assertFalse(palindrome.isPalindrome("noon", new OffByOne()));
    }

    @Test
    public void testIsPalindromeRecursion() {
        assertTrue(palindrome.isPalindromeRecursion("racecar"));
        assertTrue(palindrome.isPalindromeRecursion("a"));
        assertTrue(palindrome.isPalindromeRecursion(""));
        assertFalse(palindrome.isPalindromeRecursion("happy"));
        assertFalse(palindrome.isPalindromeRecursion("Racecar"));
        assertTrue(palindrome.isPalindromeRecursion("noon"));
    }


}
