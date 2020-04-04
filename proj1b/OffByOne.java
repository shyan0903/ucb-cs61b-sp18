/* A class that checks if two characters are off by 1. */
public class OffByOne implements CharacterComparator {

    @Override
    /* Two chars are considered equal if they are off by exactly 1. */
    public boolean equalChars(char x, char y) {
        return java.lang.Math.abs(x - y) == 1;
    }
}
