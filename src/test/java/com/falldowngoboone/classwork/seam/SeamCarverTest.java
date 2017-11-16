package com.falldowngoboone.classwork.seam;

import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import com.falldowngoboone.classwork.TestFolder;
import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {
    private SeamCarver sc1;
    private TestFolder testFolder = new TestFolder("seam");
    private Picture testPic1 = new Picture(testFolder.getFilePath("3x4.png"));

    @Before
    public void set_up() {
        sc1 = new SeamCarver(testPic1);
    }

    @Test
    public void test_energy_correctness() {
        // refer to [picName].printseams.txt for confirmation on [picName].png
        assertThat(sc1.energy(0,0), is(1000.0));
        assertThat(sc1.energy(1,1), is(228.52789764052878));
    }

    @Test
    public void test_energy_invalid_pixel() {
        // should throw IllegalArgumentException
    }
}