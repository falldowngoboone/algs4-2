package com.falldowngoboone.classwork.wordnet;

import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.*;
import java.util.*;
import java.util.stream.Collectors;

public class WordNetTest {
    private WordNet wordnet;
    private String synsetsFile = System.getProperty("user.dir") + "/src/test/wordnet/synsets.txt";
    private String hypernymsFile = System.getProperty("user.dir") + "/src/test/wordnet/hypernyms.txt";

    @Before
    public void set_up() {
        wordnet = new WordNet(synsetsFile, hypernymsFile);
    }

    @Test
    public void checkCorrectnessOfSap() {
        // 20743 is the int val for "animal animate_being beast brute creature fauna"
        assertThat(wordnet.sap("worm", "bird"), is(equalTo("animal animate_being beast brute creature fauna")));
    }
}