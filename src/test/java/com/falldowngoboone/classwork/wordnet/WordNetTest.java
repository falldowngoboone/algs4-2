package com.falldowngoboone.classwork.wordnet;

import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.*;

public class WordNetTest {
    private WordNet wordnet;
    private final String TEST_FILE_DIR = TestHelper.TEST_FILE_DIR.concat("/");
    // TODO create a build method for WordNet

    @Rule
    public ExpectedException errors = ExpectedException.none();

    @Test
    public void check_sap_correctness() {
        wordnet = buildWordNet("synsets.txt", "hypernyms.txt");
        assertThat(wordnet.sap("worm", "bird"), is(equalTo("animal animate_being beast brute creature fauna")));
    }

    @Test
    public void throws_error_for_graphs_with_cycle() {
        errors.expect(IllegalArgumentException.class);
        wordnet = buildWordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
    }

    private WordNet buildWordNet(String synsetsFile, String hypernymsFile) {
        return new WordNet(TEST_FILE_DIR + synsetsFile, TEST_FILE_DIR + hypernymsFile);
    }
}