/******************************************************************************
 *  Compilation:  javac WordNet.java
 *    
 *  Outcast detection.
 *
 ******************************************************************************/

package com.falldowngoboone.classwork.wordnet;

import edu.princeton.cs.algs4.*;
import java.util.*;

public class Outcast {
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {}

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        return "";
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