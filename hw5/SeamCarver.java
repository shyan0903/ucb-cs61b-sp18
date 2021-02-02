import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/**
 * This class presents a content-aware resizing technique. It uses
 * dynamic programming to find horizontal or vertical seams with
 * the minimum total energy and remove them from a picture to resize it.
 * */
public class SeamCarver {
    private Picture picture;
    private double[][] energyDB;
    private int width, height;
    private double[][] energyM;

    /** Constructor. */
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();

        energyDB = new double[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                energyDB[col][row] = calcEnergy(col, row);
            }
        }

    }

    /** Return a copy of the input picture to avoid changes. */
    public Picture picture() {
        return new Picture(picture);
    }

    /** Get the width of the picture. */
    public int width() {
        return width;
    }

    /** Get the width of the picture. */
    public int height() {
        return height;
    }

    /** Retrieve energy from array. */
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Your input value is invalid.");
        }
        return energyDB[x][y];
    }

    /** Calculates the energy of a pixel at column x and row y
     * and stores the values in a 2D array for faster retrievals. */
    private double calcEnergy(int x, int y) {
        double rx, gx, bx, ry, gy, by;
        Color left = picture.get(updateCol(x, '-'), y), right = picture.get(updateCol(x, '+'), y);
        Color up = picture.get(x, updateRow(y, '-')), down = picture.get(x, updateRow(y, '+'));
        rx = Math.pow(left.getRed() - right.getRed(), 2);
        gx = Math.pow((left.getGreen() - right.getGreen()), 2);
        bx = Math.pow((left.getBlue() - right.getBlue()), 2);
        ry = Math.pow((up.getRed() - down.getRed()), 2);
        gy = Math.pow((up.getGreen() - down.getGreen()), 2);
        by = Math.pow((up.getBlue() - down.getBlue()), 2);
        return rx + gx + bx + ry + gy + by;
    }

    /** Destructively transpose the energyDB width to adapt the horizontal and
     * vertical carvings. NOTE: width and height are changed after transposing.
     * */
    private double[][] transpose() {
        double[][] transposed = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                transposed[row][col] = energyDB[col][row];
            }
        }
        width = transposed.length;
        height = transposed[0].length;
        return transposed;
    }

    /**
     * Helper functions to compute updated rows and columns, include corner cases.
     * @param cur the current row/column number
     * @param dir a char that indicates the direction of change
     * @return an int
     * */
    private int updateRow(int cur, char dir) {
        return dir == '+' ? Math.floorMod((cur + 1), height)
                : Math.floorMod((cur - 1), height);
    }
    private int updateCol(int cur, char dir) {
        return dir == '+' ? Math.floorMod((cur + 1), width)
                : Math.floorMod((cur - 1), width);
    }

    /** Find the sequence of indices for horizontal seam to be removed. */
    public int[] findHorizontalSeam() {
        calcM();
        return findSeam();
    }

    /** Find the sequence of indices for vertical seam to be removed. */
    public int[] findVerticalSeam() {
        energyDB = transpose();
        int[] seam = findHorizontalSeam();
        energyDB = transpose();
        return seam;
    }

    /**
     * Suppose we have the following definitions:
     * M(col, row) = cost of minimum cost path ending at (col, row)
     * e(col, row) = energy cost of pixel at location (col, row)
     * Then M(i,j) = e(i,j) + min(M(i−1,j−1),M(i,j−1),M(i+1,j−1))
     * */
    private void calcM() {
        energyM = new double[width][height];
        /* Initialize the values for the first column/row depending on the orientation. */
        for (int i = 0; i < height; i++) {
            energyM[0][i] = energyDB[0][i];
        }
        /* For the other columns/rows, use the expression above. */
        for (int i = 1; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double[][] neighbors = touchingPixelsEnergy(i, j);
                int minIndex = minEnergy(neighbors[0]);
                energyM[i][j] = energyDB[i][j] + neighbors[0][minIndex];
            }
        }
    }

    private double[][] touchingPixelsEnergy(int c, int r) {
        int topRow = r - 1, midRow = r, btmRow = r + 1;
        double mid = energyM[c - 1][midRow];
        if (height == 1) {
            return new double[][]{{mid}, {midRow}};
        }
        if (height == 2) {
            if (r == 0) {
                return new double[][]{{mid, energyM[c - 1][btmRow]}, {midRow, btmRow}};
            } else {
                return new double[][]{{energyM[c - 1][topRow], mid}, {topRow, midRow}};
            }
        }
        if (r != 0 && r != height - 1) {
            return new double[][]{
                    {energyM[c - 1][topRow], mid, energyM[c - 1][btmRow]},
                    {topRow, midRow, btmRow}};
        } else if (r == 0) {
            return new double[][]{{mid, energyM[c - 1][btmRow]}, {midRow, btmRow}};
        } else {
            return new double[][]{{energyM[c - 1][topRow], mid}, {topRow, midRow}};
        }
    }

    /** Return the index of the minimum energy among values. */
    private int minEnergy(double[] values) {
        double minEnergy = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < minEnergy) {
                minEnergy = values[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int[] findSeam() {
        int[] seam = new int[width];
        int last = minEnergy(energyM[width - 1]);
        for (int i = width - 1; i > 0; i--) {
            seam[i] = last;
            int temp = minEnergy(touchingPixelsEnergy(i, last)[0]);
            last = (int) touchingPixelsEnergy(i, last)[1][temp];
        }
        seam[0] = last;
        return seam;
    }

    /** Remove horizontal seam from picture. */
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
//        need to change the height after removing
        height = picture.height();
    }
    /** Remove vertical seam from picture. */
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        width = picture.width();
    }

}
