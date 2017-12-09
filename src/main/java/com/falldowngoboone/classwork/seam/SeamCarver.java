/******************************************************************************
*  Compilation:  javac SeamCarver.java
* 
*  Content aware image resizing
******************************************************************************/
package com.falldowngoboone.classwork.seam;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import static java.lang.System.arraycopy;

public class SeamCarver {
    private int W;
    private int H;
    Pixel[][] pixels;
    private boolean isTransposed = false;

    private class Pixel {
        private Color color;
        private double energy;

        public Pixel(Color color) {
            this.color = color;
        }
    }

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (null == picture)
            throw new IllegalArgumentException("SeamCarver must be called with a valid Picture instance.");
        W = picture.width();
        H = picture.height();
        pixels = new Pixel[H][W];
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++) {
                pixels[y][x] = new Pixel(picture.get(x, y));
                pixels[y][x].energy = energy(x, y, picture);
            }
    }

    private Pixel getPixel(int x, int y) {
        return isValidPixel(x, y) ? pixels[y][x] : null;
    }

    // current picture
    public Picture picture() {
        if (isTransposed)
            transpose();
        Picture picture = new Picture(W, H);
        for (int x = 0; x < W; x++)
            for (int y = 0; y < H; y++)
                picture.set(x, y, getPixel(x, y).color);
        return picture;
    }

    // width of current picture
    public int width() {
        return isTransposed ? H : W;
    }

    // height of current picture
    public int height() {
        return isTransposed ? W : H;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (isTransposed) {
            transpose();
        }
        if (!isValidPixel(x, y))
            throw new IllegalArgumentException();

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;

        Color up = getPixel(x, y - 1).color, down = getPixel(x, y + 1).color, left = getPixel(x - 1, y).color,
                right = getPixel(x + 1, y).color;

        return Math.sqrt(squareDelta(up, down) + squareDelta(left, right));
    }

    private double energy(int x, int y, Picture picture) {
        if (!isValidPixel(x, y))
            throw new IllegalArgumentException();

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;

        Color up = picture.get(x, y - 1), down = picture.get(x, y + 1), left = picture.get(x - 1, y),
                right = picture.get(x + 1, y);

        return Math.sqrt(squareDelta(up, down) + squareDelta(left, right));
    }

    private double squareDelta(Color a, Color b) {
        double deltaR = a.getRed() - b.getRed(), deltaG = a.getGreen() - b.getGreen(),
                deltaB = a.getBlue() - b.getBlue();

        return Math.pow(deltaR, 2) + Math.pow(deltaG, 2) + Math.pow(deltaB, 2);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!isTransposed)
            transpose();
        return findSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (isTransposed)
            transpose();
        return findSeam();
    }

    private int[] findSeam() {
        int[] verticalSeam = new int[H];
        double[][] distTo = new double[H][W]; // push accumulated energy to this
        int[][] pixelTo = new int[H][W]; // push x to this
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++) {
                if (y == 0)
                    distTo[y][x] = 0;
                else
                    distTo[y][x] = Double.POSITIVE_INFINITY;
            }
        for (int y = 0; y < H - 1; y++)
            for (int x = 0; x < W; x++) {
                Pixel p1 = getPixel(x, y);
                int y2 = y + 1;
                for (int x2 = x - 1; x2 <= x + 1; x2++) {
                    Pixel p2 = getPixel(x2, y2);
                    if (p2 == null)
                        continue;
                    if (distTo[y][x] + p2.energy < distTo[y2][x2]) {
                        distTo[y2][x2] = distTo[y][x] + p2.energy;
                        pixelTo[y2][x2] = x;
                    }
                }
            }
        int x = 0;
        for (int i = 0; i < W; i++) {
            if (distTo[H - 1][i] < distTo[H - 1][x])
                x = i;
        }
        for (int y = H - 1; y >= 0; y--) {
            verticalSeam[y] = x;
            x = pixelTo[y][x];
        }
        return verticalSeam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (1 == H)
            throw new IllegalArgumentException("Cannot remove seam; width is 1px.");
        // transpose the picture and apply removeVerticalSeam()
        // so there needs to be a boolean for storing picture state
        // as well as a transpose method that transposes the picture based on that boolean
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (1 == W)
            throw new IllegalArgumentException("Cannot remove seam; width is 1px.");
        validateSeamAndMaybeThrowError(seam, H);
        for (int i = 0; i < H; i++) {
            if (!isValidPixel(seam[i], i))
                throw new IllegalArgumentException();
            // remove pixel and shift via arraycopy
        }
    }

    private boolean isValidPixel(int x, int y) {
        return x >= 0 && y >= 0 && x < width() && y < height();
    }

    private void validateSeamAndMaybeThrowError(int[] seam, int expectedLength) {
        if (null == seam)
            throw new IllegalArgumentException("Method must be supplied a valid int[] seam instance.");
        if (seam.length != expectedLength)
            throw new IllegalArgumentException(
                    "Expected seam length of " + expectedLength + ", recevied: " + seam.length);
        int prev = -1;
        for (int current : seam) {
            if (prev != -1 && prev - current < -1 || prev - current > 1)
                throw new IllegalArgumentException("Seam is unconnected.");
            prev = current;
        }
    }

    private void transpose() {
        Pixel[][] newPixels = new Pixel[W][H];
        if (!isTransposed) {
            for (int y = 0; y < H; y++)
                for (int x = 0; x < W; x++)
                    newPixels[W - 1 - x][y] = getPixel(x, y);
        } else {
            // TODO: fix me!!!
            for (int x = 0; x < W; x++)
                for (int y = 0; y < H; y++)
                    newPixels[x][H - 1 - y] = getPixel(x, y); // pixels[yH][xW]
        }
        pixels = newPixels;
        int T = W;
        W = H;
        H = T;
        isTransposed = !isTransposed;
    }
}