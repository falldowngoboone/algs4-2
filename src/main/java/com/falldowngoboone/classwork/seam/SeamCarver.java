/******************************************************************************
 *  Compilation:  javac SeamCarver.java
 * 
 *  Content aware image resizing
 ******************************************************************************/
package com.falldowngoboone.classwork.seam;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

/* 
 * Corner cases.
 * =============
 * 
 * Your code should throw a `java.lang.IllegalArgumentException` when a 
 * constructor or method is called with an invalid argument, as documented 
 * below:
 * 
 * - By convention, the indices x and y are integers between 0 and width − 1 and 
 *   between 0 and height − 1 respectively, where width is the width of the 
 *   current image and height is the height. Throw a java.lang.
 *   IllegalArgumentException if either x or y is outside its prescribed range.
 * - Throw a java.lang.IllegalArgumentException if the constructor, 
 *   removeVerticalSeam(), or removeHorizontalSeam() is called with a null 
 *   argument.
 * - Throw a java.lang.IllegalArgumentException if removeVerticalSeam() or 
 *   removeHorizontalSeam() is called with an array of the wrong length or if the 
 *   array is not a valid seam (i.e., either an entry is outside its prescribed 
 *   range or two adjacent entries differ by more than 1).
 * - Throw a java.lang.IllegalArgumentException if removeVerticalSeam() is called 
 *   when the width of the picture is less than or equal to 1 or if 
 *   removeHorizontalSeam() is called when the height of the picture is less than 
 *   or equal to 1.
 * 
 * Constructor. 
 * ============
 * 
 * The data type may not mutate the Picture argument to the constructor.
 */

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validatePixelAndMaybeThrowError(x, y);

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;

        Color up    = picture.get(x, y-1), 
              down  = picture.get(x, y+1), 
              left  = picture.get(x-1, y), 
              right = picture.get(x+1, y);

        return Math.sqrt(squareDelta(up, down) + squareDelta(left, right));
    }

    private double squareDelta(Color a, Color b) {
        double deltaR = a.getRed() - b.getRed(), 
               deltaG = a.getGreen() - b.getGreen(), 
               deltaB = a.getBlue() - b.getBlue();

        return Math.pow(deltaR, 2) + Math.pow(deltaG, 2) + Math.pow(deltaB, 2);
    }

    // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam() {}

    // sequence of indices for vertical seam
    // public int[] findVerticalSeam() {}

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // create a new picture of height 1px less
        // iterate through the current picture
        // if the current pixel is not in the seam
            // copy the pixel to the new picture
        // set the picture to the new picture
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        // create a new picture of width 1px less
        // iterate through the current picture
        // if the current pixel is not in the seam
            // copy the pixel to the new picture
        // set the picture to the new picture
    }

    private void validatePixelAndMaybeThrowError(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException();            
        }
    }
}