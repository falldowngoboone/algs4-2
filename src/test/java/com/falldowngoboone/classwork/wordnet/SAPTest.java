package com.falldowngoboone.classwork.wordnet;

import static org.hamcrest.core.Is.*;
import org.junit.*;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.*;
import java.util.*;

public class SAPTest {
    private final String TEST_FILE_DIR = TestHelper.TEST_FILE_DIR.concat("/");
    SAP sap;

    @Before
    public void set_up() {
        sap = null;
    }

    @Test
    public void test_bird_and_worm_ancestor_is_correct() {
        sap = buildSap("digraph-wordnet.txt");
        int animal = 20743;
        List<Integer> bird = Arrays.asList(24306, 24307, 25293, 33764, 70067);
        List<Integer> worm = Arrays.asList(81679, 81680, 81681, 81682);
        assertThat(sap.ancestor(bird, worm), is(animal));
    }

    @Test
    public void test_bird_and_worm_length_is_correct() {
        sap = buildSap("digraph-wordnet.txt");
        int length = 5;
        List<Integer> bird = Arrays.asList(24306, 24307, 25293, 33764, 70067);
        List<Integer> worm = Arrays.asList(81679, 81680, 81681, 81682);
        assertThat(sap.length(bird, worm), is(length));
    }

    @Test
    public void test_length_for_3_and_3() {
        sap = buildSap("digraph1.txt");
        assertThat(sap.length(3, 3), is(0));
    }

    @Test
    public void test_length_for_4_and_1() {
        sap = buildSap("digraph4.txt");
        assertThat(sap.length(4, 1), is(3));
    }

    @Test
    public void test_ancestor_for_4_and_1() {
        sap = buildSap("digraph4.txt");
        assertThat(sap.ancestor(4, 1), is(4));
    }

    @Test
    public void test_length_for_14_and_21() {
        sap = buildSap("digraph5.txt");
        assertThat(sap.length(14, 21), is(8));
    }

    @Test
    public void test_length_for_4_and_0() {
        sap = buildSap("digraph9.txt");
        assertThat(sap.length(4, 0), is(3));
    }

    private SAP buildSap(String gFileName) {
        In digraph = new In(TEST_FILE_DIR + gFileName);
        Digraph G = new Digraph(digraph);
        return new SAP(G);
    }
}