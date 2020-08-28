import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Use dynamic programming to find horizontal or vertical seams with the minimum total energy
 * and remove them from a picture to resize it.
 * */
public class SeamCarver {
    private Picture picture;
    private double[][] energyDB;
    private int width, height;
    private final int HORIZONTAL = 0, VERTICAL = 1;
    private double[][] energyM;
    private int[][] pathFrom;

    /** Constructor. */
    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
        energyDB = new double[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                energyDB[col][row] = energyPrivate(col, row);
            }
        }
    }

    /** Return the current picture. */
    public Picture picture() {
        return this.picture;
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
        return energyDB[x][y];
    }

    /** Calculates the energy of a pixel at column x and row y
     * and stores the values in a 2D array for faster retrievals. */
    private double energyPrivate(int x, int y) {
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

    public static void main(String[] args) {
        Picture picture = new Picture("images/6x5.png");
        SeamCarver sc = new SeamCarver(picture);
        for (int i :
                sc.findHorizontalSeam()) {
            System.out.println(i);
        }
        for (int i :
                sc.findVerticalSeam()){
            System.out.println(i);
        }
    }

    /**
     * Helper functions to compute updated rows and columns, include corner cases.
     * @param cur the current row/column number
     * @param dir a char that indicates the direction of change
     * @return an int
     * */
    private int updateRow(int cur, char dir) {
        //System.out.println(Math.floorMod((cur - 1), width()));
        return dir == '+' ? Math.floorMod((cur + 1), height)
                : Math.floorMod((cur - 1), height);
    }
    private int updateCol(int cur, char dir) {
        return dir == '+' ? Math.floorMod((cur + 1), width)
                : Math.floorMod((cur - 1), width);
    }

    /** Find the sequence of indices for horizontal seam to be removed. */
    public int[] findHorizontalSeam() {
        calcM(0);
        int[] seam = new int[width];
        double[] temp = new double[height];
        for (int i = 0; i < height; i++) {
            temp[i] = i;
        }
        double[][] para = new double[2][];
        para[0] = energyM[width - 1];
        para[1] = temp;
        int last = (int) minEnergy(para)[1];
        for (int i = width - 1; i >= 0; i--) {
            seam[i] = last;
            last = pathFrom[i][last];
        }
        return seam;
    }
    /** Find the sequence of indices for vertical seam to be removed. */
    public int[] findVerticalSeam() {
        calcM(1);
        int[] seam = new int[height];
        double[] temp = new double[width];
        for (int i = 0; i < width; i++) {
            temp[i] = i;
        }
        double[][] para = new double[2][];
        para[0] = energyM[height - 1];
        para[1] = temp;
        int last = (int) minEnergy(para)[1];
        for (int i = height - 1; i >= 0; i--) {
            seam[i] = last;
            last = pathFrom[i][last];
        }
        return seam;
    }

    /**
     * Suppose we have the following definitions:
     * M(col, row) = cost of minimum cost path ending at (col, row)
     * e(col, row) = energy cost of pixel at location (col, row)
     * Then M(i,j) = e(i,j) + min(M(i−1,j−1),M(i,j−1),M(i+1,j−1))
     * */
    private void calcM(int dir) {
        /* Horizontal orientation. */
        if (dir == 0) {
            energyM = new double[width][height];
            pathFrom = new int[width][height];
            /* Initialize the values for the first column. */
            for (int i = 0; i < height; i++) {
                energyM[0][i] = energyDB[0][i];
                pathFrom[0][i] = -1;
            }
            /* For the other columns, use the expression above. */
            for (int i = 1; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    double[] temp = minEnergy(touchingPixelsEnergy(i, j, dir));
                    energyM[i][j] = energyDB[i][j] + temp[0];
                    pathFrom[i][j] = (int) temp[1];
                }
            }
        } else {
            /* Vertical orientation. */
            energyM = new double[height][width];
            pathFrom = new int[height][width];
            for (int i = 0; i < width; i++) {
                energyM[0][i] = energyDB[i][0];
                pathFrom[0][i] = -1;
            }
            for (int i = 1; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    double[] temp = minEnergy(touchingPixelsEnergy(j, i, dir));
                    energyM[i][j] = energyDB[j][i] + temp[0];
                    pathFrom[i][j] = (int)temp[1];
                }
            }
        }
    }

    private double[][] touchingPixelsEnergy(int c, int r, int dir) {
        if (dir == 0) {
            if (r != 0 && r != height - 1) {
                return new double[][]{{energyM[c - 1][r - 1], energyM[c - 1][r], energyM[c - 1][r + 1]},
                        {r-1,r,r+1}};
            } else if (r == 0) {
                return new double[][]{{energyM[c - 1][r], energyM[c - 1][r + 1]},{r,r+1}};
            } else {
                return new double[][]{{energyM[c - 1][r - 1], energyM[c - 1][r]}, {r-1,r}};
            }
        } else {
            if (c != 0 && c != width - 1) {
                return new double[][]
                        {{energyM[r - 1][c - 1], energyM[r - 1][c], energyM[r - 1][c + 1]},
                                {c - 1, c, c + 1}};
            } else if (c == 0) {
                return new double[][]{{energyM[r - 1][c], energyM[r - 1][c + 1]}, {c, c + 1}};
            } else {
                return new double[][]{{energyM[r - 1][c - 1], energyM[r - 1][c]}, {c - 1, c}};
            }
        }
    }

    /** Return the minimum energy among values. */
    private double[] minEnergy(double[][] values) {
        double minEnergy = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < values[0].length; i++) {
            if (values[0][i] < minEnergy) {
                minEnergy = values[0][i];
                minIndex = i;
            }
        }
        return new double[]{minEnergy, values[1][minIndex]};
    }

    /** Remove horizontal seam from picture. */
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }
    /** Remove vertical seam from picture. */
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }

}
