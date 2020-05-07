package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {
    private WeightedQuickUnionUF percolationUnion;
    private int myN;
    private boolean[][] openSites;
    private int numOpen;
    private int top; // virtual top node
    private int bottom; // virtual bottom node
    private WeightedQuickUnionUF percolationUnionNoBottom;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        myN = N;
        percolationUnion = new WeightedQuickUnionUF(N * N + 2);
        percolationUnionNoBottom = new WeightedQuickUnionUF(N * N + 1);
        openSites = new boolean[N][N];
        numOpen = 0;
        top = N * N;
        bottom = N * N + 1;
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

    /*  open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (!checkValidity(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            openSites[row][col] = true;
            numOpen += 1;
            int siteValue = rowColtoN(row, col);
            if (row == 0) {
                percolationUnion.union(siteValue, top);
                percolationUnionNoBottom.union(siteValue, top);
            } else if (row == myN - 1) {
                percolationUnion.union(siteValue, bottom);
            }

            for (int i : touchingSites(row, col)) {
                if (i >= 0) {
                    percolationUnion.union(siteValue, i);
                    percolationUnionNoBottom.union(siteValue, i);
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
        return percolationUnionNoBottom.connected(rowColtoN(row, col), top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationUnion.connected(top, bottom);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
    }
}
