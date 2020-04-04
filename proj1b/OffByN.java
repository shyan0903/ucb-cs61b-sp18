/* A general class that checks if two characters are off by N. */
public class OffByN implements CharacterComparator {
    private int N;

    /* A constructor. */
    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return java.lang.Math.abs(x - y) == N;
    }
}
