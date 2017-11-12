/******************************************************************************
 *  Compilation:  javac WordNet.java
 *    
 *  Outcast detection.
 *
 ******************************************************************************/

package com.falldowngoboone.classwork.wordnet;

import edu.princeton.cs.algs4.*;
import java.util.*;
import static java.util.stream.Collectors.*;

public class Outcast {
    private final WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max;
        List<Integer> distances = Arrays.asList(nouns).stream().map(noun -> {
            return Arrays.asList(nouns).stream().mapToInt(n -> wordnet.distance(noun, n)).reduce((total, dist) -> total + dist).getAsInt();
        }).collect(toList());
        max = distances.stream().reduce(0, (prev, curr) -> {
            return prev > curr ? prev : curr;
        });
        return nouns[distances.indexOf(max)];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}