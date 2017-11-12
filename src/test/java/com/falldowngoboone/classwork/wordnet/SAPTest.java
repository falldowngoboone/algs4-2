package com.falldowngoboone.classwork.wordnet;

import static org.hamcrest.core.Is.*;
import org.junit.*;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.*;
import java.util.*;
import java.util.stream.Collectors;

public class SAPTest {
    private SAP sap;
    private Digraph G;
    private String testFileDir = System.getProperty("user.dir") + "/src/test/wordnet";

    @Before
    public void set_up() {
        In digraph = new In(testFileDir + "/digraph-wordnet.txt");
        G = new Digraph(digraph);
        sap = new SAP(G);
    }

    @Test
    public void test_bird_and_worm_ancestor_is_correct() {
        int animal = 20743;
        List<Integer> bird = Arrays.asList(24306, 24307, 25293, 33764, 70067);
        List<Integer> worm = Arrays.asList(81679, 81680, 81681, 81682);
        assertThat(sap.ancestor(bird, worm), is(animal));
    }

    @Test
    public void test_bird_and_worm_length_is_correct() {
        int length = 5;
        List<Integer> bird = Arrays.asList(24306, 24307, 25293, 33764, 70067);
        List<Integer> worm = Arrays.asList(81679, 81680, 81681, 81682);
        assertThat(sap.length(bird, worm), is(length));
    }
}