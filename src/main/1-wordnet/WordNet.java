/******************************************************************************
 *  Compilation:  javac WordNet.java
 *    
 *  A WordNet digraph.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.*;
import java.util.*;

class WordNet {
    private ST<String, Bag<Integer>> idsByNoun;
    private ST<Integer, String> synsetsById;
    private Digraph G;
    private SAP shortestPaths;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFileName, String hypernymsFileName) {
        if (null == synsetsFileName || null == hypernymsFileName) throw new IllegalArgumentException();
        idsByNoun = new ST<>();
        synsetsById = new ST<>();
        In synsets = new In(synsetsFileName);
        In hypernyms = new In(hypernymsFileName);
        int V = 0;
        
        while(synsets.hasNextLine()) {
            String[] args = synsets.readLine().split(",");
            String synset = args[1];
            synsetsById.put(V, synset);
            String[] nouns = synset.split(" ");
            int id = Integer.parseInt(args[0]);
            for (String noun : nouns) {
                if (!idsByNoun.contains(noun)) idsByNoun.put(noun, new Bag<Integer>());
                idsByNoun.get(noun).add(id);
            }
            V++;
        }

        G = new Digraph(V);

        while(hypernyms.hasNextLine()) {
            String[] args = hypernyms.readLine().split(",");
            int synsetId = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++) {
                int hypernymId = Integer.parseInt(args[i]);
                G.addEdge(synsetId, hypernymId);
            }
        }

        shortestPaths = new SAP(G);
    }
   
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return idsByNoun.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return idsByNoun.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        Iterable<Integer> nounAIds = getNounIds(nounA);
        Iterable<Integer> nounBIds = getNounIds(nounB);
        return shortestPaths.length(nounAIds, nounBIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Iterable<Integer> nounAIds = getNounIds(nounA);
        Iterable<Integer> nounBIds = getNounIds(nounB);
        int synsetId = shortestPaths.ancestor(nounAIds, nounBIds);
        return synsetsById.get(synsetId);
    }

    private Iterable<Integer> getNounIds(String noun) {
        return idsByNoun.get(noun);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Usage: WordNet synsets.txt hypernyms.txt");
            return;
        }
        String synsets = args[0];
        String hypernyms = args[1];

        WordNet wordnet = new WordNet(synsets, hypernyms);
        StdOut.println(wordnet.distance("worm", "bird"));
        StdOut.println(wordnet.sap("worm", "bird"));
    }
}