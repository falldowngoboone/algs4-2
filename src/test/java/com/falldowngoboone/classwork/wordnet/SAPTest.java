package com.falldowngoboone.classwork.wordnet;

import org.hamcrest.core.Is;
import org.junit.*;
import edu.princeton.cs.algs4.*;
import java.util.*;
import java.util.stream.Collectors;

public class SAPTest {
    private SAP sap;
    private Digraph G;

    @Before
    public void set_up() {
        In digraph1 = new In(System.getProperty("user.dir") + "/src/test/wordnet/digraph1.txt");
        G = new Digraph(digraph1);
        sap = new SAP(G);
    }

    @Test
    public void testSapIsNotNull() {
        Assert.assertNotNull("SAP is not null", sap);
    }

    @Test
    public void testSapAncestorWithSingleVertices() {
        int ancestor = sap.ancestor(7, 10);
        Assert.assertThat("SAP#ancestor with single vertices", ancestor, Is.is(1));

        ancestor = sap.ancestor(12, 1);
        Assert.assertThat("SAP#ancestor with single vertices", ancestor, Is.is(1));

        ancestor = sap.ancestor(12, 11);
        Assert.assertThat("SAP#ancestor with single vertices", ancestor, Is.is(10));

        ancestor = sap.ancestor(12, 6);
        Assert.assertThat("SAP#ancestor with single vertices", ancestor, Is.is(-1));
    }

    @Test
    public void testSapAncestorWithIterableVertices() {
        Bag<Integer> verticesA = new Bag<>();
        verticesA.add(12);
        verticesA.add(9);
        verticesA.add(8);
        Bag<Integer> verticesB = new Bag<>();
        verticesB.add(11);
        verticesB.add(7);

        int ancestor = sap.ancestor(verticesA, verticesB);
        Assert.assertThat("SAP#ancestor with iterable vertices", ancestor, Is.is(3));
    }

    @Test
    public void testSapLengthWithSingleVertices() {
        int length = sap.length(7, 2);
        Assert.assertThat("SAP#length with single vertices", length, Is.is(4));

        length = sap.length(12, 9);
        Assert.assertThat("SAP#length with single vertices", length, Is.is(3));
    }

    @Test
    public void testSapLengthWithIterableVertices() {
        Bag<Integer> verticesA = new Bag<>();
        verticesA.add(12);
        verticesA.add(9);
        verticesA.add(8);
        Bag<Integer> verticesB = new Bag<>();
        verticesB.add(11);
        verticesB.add(7);

        int ancestor = sap.length(verticesA, verticesB);
        Assert.assertThat("SAP#length with iterable vertices", ancestor, Is.is(2));
    }
}