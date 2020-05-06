package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF percolationUnion;
    private int myN;
    private boolean[][] openSites;
    private int numOpen;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        myN = N;
        percolationUnion = new WeightedQuickUnionUF(N * N);
        openSites = new boolean[N][N];
        numOpen = 0;
    }

    /* A helper function that checks whether the input row and col
     * are valid numbers. */
    private boolean checkValidity(int row, int col) {
        return 0 <= row && row < myN && 0 <= col && col < myN;
    }

    /* A helper function that checks if any touching site is open. If so,
    * connect them. */
    private int[] touchingSites(int row, int col) {
        int[] result = new int[]{-1, -1, -1, -1};
        int index = 0;
        for (int i = row - 1; i <= row + 1; i += 2) {
            if (i >= 0 && i < myN && isOpen(i, col)) {
                    result[index] = rowColtoN(i, col);
                    index += 1;
            }
        }
        for (int j = col - 1; j <= col + 1; j += 2) {
            if (j >= 0 && j < myN && isOpen(row, j)) {
                result[index] = rowColtoN(row, j);
                index += 1;
            }
        }
        return result;
    }

    /* Transforms row and col to a corresponding integer. */
    private int rowColtoN(int row, int col) {
        return row * myN + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkValidity(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            openSites[row][col] = true;
            numOpen += 1;
            for (int i : touchingSites(row, col)) {
                if (i >= 0) {
                    percolationUnion.union(rowColtoN(row, col), i);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkValidity(row, col)) {
            return openSites[row][col];
        }
        throw new IndexOutOfBoundsException();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkValidity(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        for (int c = 0; c < myN; c++) {
            if (isOpen(row, col) &&
                    percolationUnion.connected(rowColtoN(row, col), rowColtoN(0, c))) {
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < myN; i ++) {
            if (isFull(myN - 1, i)) {
                return true;
            }
        }
        return false;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation trial = new Percolation(5);
        trial.open(0,0);
        System.out.println(trial.isFull(0,0));
        trial.open(1,1);
        trial.open(2,2);
        trial.open(3,1);
        System.out.println(trial.isOpen(2,1));
        System.out.println(trial.isOpen(2,2));
        System.out.println(trial.isFull(3,1));
        System.out.println(trial.isFull(1,1));
        trial.open(2,1);
        trial.open(0,1);
        trial.open(4,1);

        System.out.println(trial.numberOfOpenSites());
        System.out.print(trial.percolates());

    }
}
