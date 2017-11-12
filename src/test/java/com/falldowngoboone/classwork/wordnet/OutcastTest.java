package com.falldowngoboone.classwork.wordnet;

import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.*;
import java.util.*;
import java.util.stream.Collectors;

public class OutcastTest {
    private WordNet wordnet;
    private Outcast o;
    private String synsetsFile = System.getProperty("user.dir") + "/src/test/wordnet/synsets.txt";
    private String hypernymsFile = System.getProperty("user.dir") + "/src/test/wordnet/hypernyms.txt";

    @Before
    public void set_up() {
        wordnet = new WordNet(synsetsFile, hypernymsFile);
        o = new Outcast(wordnet);
    }

    @Test
    public void checkCorrectnessOfOutcast() {
        // 20743 is the int val for "animal animate_being beast brute creature fauna"
        assertThat(o.outcast(new String[]{"horse", "zebra", "cat", "bear", "table"}), is(equalTo("table")));
    }
}