package com.falldowngoboone.classwork.seam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.falldowngoboone.classwork.TestFolder;
import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {
    SeamCarver sc1;
    TestFolder testFolder = new TestFolder("seam");
    Picture testPic1 = new Picture(testFolder.getFilePath("3x4.png"));

    @BeforeEach
    public void set_up() {
        sc1 = new SeamCarver(testPic1);
    }

    @Test
    public void test_energy_correctness() {
        // refer to [picName].printseams.txt for confirmation on [picName].png
        assertEquals(sc1.energy(0,0), 1000.0);
        assertEquals(sc1.energy(1,1), 228.52789764052878);
    }

    @Test
    public void test_energy_invalid_pixel() {
        // should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> sc1.energy(4,5));
    }
}