/******************************************************************************
 *  Compilation:  javac SeamCarver.java
 * 
 *  Content aware image resizing
 ******************************************************************************/
package com.falldowngoboone.classwork.seam;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private int W;
    private int H;
    private Pixel[][] pixels;
    private boolean isTransposed = false;

    private class Pixel {
        private final Color color;
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

    // current picture
    public Picture picture() {
        if (isTransposed)
            transpose();
        Picture picture = new Picture(W, H);
        for (int y = 0; y < H; y++)
            for (int x = 0; x < W; x++)
                picture.set(x, y, pixels[y][x].color);
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

        if (x == 0 || y == 0 || x == W - 1 || y == H - 1)
            return 1000;

        Color up = pixels[y - 1][x].color, down = pixels[y + 1][x].color, left = pixels[y][x - 1].color,
                right = pixels[y][x + 1].color;

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
                int y2 = y + 1;
                for (int x2 = x - 1; x2 <= x + 1; x2++) {
                    if (!isValidPixel(x2, y2)) continue;
                    Pixel p2 = pixels[y2][x2];
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
        if (!isTransposed) transpose();
        removeSeam(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (isTransposed) transpose();
        removeSeam(seam);
    }
    
    private void removeSeam(int[] seam) {
        if (1 == W)
            throw new IllegalArgumentException("Cannot remove seam; width is 1px.");
        validateSeamAndMaybeThrowError(seam, H);
        Pixel[][] newPixels = new Pixel[H][W - 1];
        for (int y = 0; y < H; y++) {
            if (!isValidPixel(seam[y], y)) throw new IllegalArgumentException();
            System.arraycopy(pixels[y], 0, newPixels[y], 0, seam[y]);
            if (seam[y] + 1 != W)
                System.arraycopy(pixels[y], seam[y] + 1, newPixels[y], seam[y], W - 1 - seam[y]);
        }
        W--;
        pixels = newPixels;
        for (int y = 0; y < H; y++) {
            if (isValidPixel(seam[y] - 1, y)) energy(seam[y] - 1, y);
            if (isValidPixel(seam[y], y)) energy(seam[y], y);
        }
    }

    private boolean isValidPixel(int x, int y) {
        return x >= 0 && y >= 0 && x < W && y < H;
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
                for (int x = 0; x < W; x++){
                    newPixels[x][y] = pixels[y][x];
                }
        } else {
            for (int x = 0; x < W; x++)
                for (int y = 0; y < H; y++){
                    newPixels[x][H - 1 - y] = pixels[y][x]; // pixels[yH][xW]
                }
        }
        pixels = newPixels;
        int T = W;
        W = H;
        H = T;
        isTransposed = !isTransposed;
    }
}