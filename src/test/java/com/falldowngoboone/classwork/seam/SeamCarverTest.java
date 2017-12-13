package com.falldowngoboone.classwork.seam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import com.falldowngoboone.classwork.TestFolder;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarverTest {
    SeamCarver sc3x4;
    SeamCarver sc1x1;
    SeamCarver sc4x6;
    SeamCarver sc7x10;
    SeamCarver sc10x12;
    SeamCarver sc3x7;
    TestFolder testFolder = new TestFolder("seam");
    Picture testPic3x4 = new Picture(testFolder.getFilePath("3x4.png"));
    Picture testPic1x1 = new Picture(testFolder.getFilePath("1x1.png"));
    Picture testPic4x6 = new Picture(testFolder.getFilePath("4x6.png"));
    Picture testPic7x10 = new Picture(testFolder.getFilePath("7x10.png"));
    Picture testPic10x12 = new Picture(testFolder.getFilePath("10x12.png"));
    Picture testPic3x7 = new Picture(testFolder.getFilePath("3x7.png"));

    @BeforeEach
    public void set_up() {
        sc3x4 = new SeamCarver(testPic3x4);
        sc1x1 = new SeamCarver(testPic1x1);
        sc4x6 = new SeamCarver(testPic4x6);
        sc7x10 = new SeamCarver(testPic7x10);
        sc10x12 = new SeamCarver(testPic10x12);
        sc3x7 = new SeamCarver(testPic3x7);
    }

    @Test
    public void picture_withoutProcessing_ReturnsOriginalPicture() {
        assertEquals(sc3x4.picture(), testPic3x4);
    }

    @Test
    public void test_energy_correctness() {
        // refer to [picName].printseams.txt for confirmation on [picName].png
        assertEquals(1000.0, sc3x4.energy(0, 0));
        assertEquals(228.52789764052878, sc3x4.energy(1, 1));
    }

    @Test
    public void test_energy_invalid_pixel() {
        assertThrows(IllegalArgumentException.class, () -> sc3x4.energy(4, 5));
    }

    @Test
    public void picture_afterRemoveHorizontalSeam_DoesNotThrowException() {
        sc3x4.removeHorizontalSeam(new int[] { 1, 1, 1 });
        sc3x4.picture();
    }

    @Test
    public void picture_AfterRemoveVerticalSeam_ShouldNotThrowError() {
        sc3x4.removeVerticalSeam(new int[] { 1, 1, 1, 1 });
        sc3x4.picture();
    }

    @Test
    public void removeHorizontalSeam_ValidSeam_PictureIsOnePixelShorter() {
        sc3x4.removeHorizontalSeam(new int[] { 1, 1, 1 });
        Picture pic = sc3x4.picture();
        assertEquals(3, sc3x4.height());
        assertEquals(3, pic.height());
    }

    @Test
    public void removeVerticalSeam_ValidSeam_PictureIsOnePixelNarrower() {
        sc3x4.removeVerticalSeam(new int[] { 1, 1, 1, 1 });
        Picture pic = sc3x4.picture();
        assertEquals(2, sc3x4.width());
        assertEquals(2, pic.width());
    }

    @Test
    public void removeHorizontalSeam_ValidSeam_DoesNotThrowException() {
        int[] valid = new int[] { 1, 1, 1 };
        sc3x4.removeHorizontalSeam(valid);
    }

    @Test
    public void removeVerticalSeam_ValidSeame_DoesNotThrowException() {
        int[] valid = new int[] { 1, 1, 1, 1 };
        sc3x4.removeVerticalSeam(valid);
    }

    @Test
    public void removeVerticalSeam_SeamWrongLength_ThrowsIllegalArgumentException() {
        int[] tooLong = new int[sc3x4.height() + 1];
        assertThrows(IllegalArgumentException.class, () -> sc3x4.removeVerticalSeam(tooLong));
    }

    @Test
    public void removeVerticalSeam_UnconnectedSeam_ThrowsIllegalArgumentException() {
        int[] unconnected = new int[] { 1, 1, 3, 2 };
        assertThrows(IllegalArgumentException.class, () -> sc3x4.removeVerticalSeam(unconnected));
    }

    @Test
    public void removeVerticalSeam_OutOfBoundsSeam_ThrowsIllegalArgumentException() {
        int[] outOfBounds = new int[] { 5, 6, 6, 7 };
        assertThrows(IllegalArgumentException.class, () -> sc3x4.removeVerticalSeam(outOfBounds));
    }

    @Test
    public void removeVerticalSeam_PictureWithWidth1px_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sc1x1.removeVerticalSeam(new int[] { 0 }));
    }

    @Test
    public void removeHorizontalSeam_PictureWithHeight1px_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sc1x1.removeHorizontalSeam(new int[] { 0 }));
    }

    @Test
    public void findHorizontalSeam_sc3x4_Returns121() {
        assertArrayEquals(new int[] { 1, 2, 1 }, sc3x4.findHorizontalSeam());
    }

    @Test
    public void findHorizontalSeam_sc4x6_Returns1210() {
        assertArrayEquals(new int[] { 1, 2, 1, 0 }, sc4x6.findHorizontalSeam());
    }

    @Test
    public void findVerticalSeam_sc3x4_Returns0110() {
        assertArrayEquals(new int[] { 0, 1, 1, 0 }, sc3x4.findVerticalSeam());
    }

    @Test
    public void findVerticalSeam_sc4x6_Returns121121() {
        assertArrayEquals(new int[] { 1, 2, 1, 1, 2, 1 }, sc4x6.findVerticalSeam());
    }

    @Test
    public void findVerticalSeam_sc7x10_Returns2343433221() {
        assertArrayEquals(new int[] { 2, 3, 4, 3, 4, 3, 3, 2, 2, 1 }, sc7x10.findVerticalSeam());
    }

    @Test
    public void removeHorizontalSeam_WithOptimalSeam_RemovesSeam() {
        int[] expected = new int[] { 8, 9, 10, 10, 10, 9, 10, 10, 9, 8 };
        int[] optimalSeam = sc10x12.findHorizontalSeam();
        assertArrayEquals(expected, optimalSeam);
        sc10x12.removeHorizontalSeam(optimalSeam);

        int[] expected3x7 = new int[] { 1, 2, 1 };
        int[] optimalSeam3x7 = sc3x7.findHorizontalSeam();
        assertArrayEquals(expected3x7, optimalSeam3x7);
        sc3x7.removeHorizontalSeam(optimalSeam3x7);

        int[] expected7x10 = new int[] { 6, 7, 7, 7, 8, 8, 7 };
        int[] optimalSeam7x10 = sc7x10.findHorizontalSeam();
        assertArrayEquals(expected7x10, optimalSeam7x10);
        sc7x10.removeHorizontalSeam(optimalSeam7x10);
    }
}