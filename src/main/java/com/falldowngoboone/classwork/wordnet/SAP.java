/******************************************************************************
 *  Compilation:  javac SAP.java
 *    
 *  An immutable data structure that finds the shortest ancestral path. An 
 *  ancestral path between two vertices v and w in a digraph is a directed path 
 *  from v to a common ancestor x, together with a directed path from w to the 
 *  same ancestor x. A shortest ancestral path is an ancestral path of minimum 
 *  total length.
 *
 ******************************************************************************/

package com.falldowngoboone.classwork.wordnet;

import edu.princeton.cs.algs4.*;
import java.util.*;

public class SAP {
    private final Digraph G;
    private final SET<Integer> roots;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (null == G) throw new IllegalArgumentException();
        this.G = new Digraph(G);
        this.roots = new SET<Integer>();
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) roots.add(i);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(this.G, w);
        int LCA = findLCA(vPaths, wPaths);
        if (-1 == LCA) return -1;
        return Math.max(vPaths.distTo(LCA), wPaths.distTo(LCA)) + 1; // add one to account for starting vertex
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vSubset, Iterable<Integer> wSubset) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(this.G, vSubset);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(this.G, wSubset);
        int LCA = findLCA(vPaths, wPaths);
        if (-1 == LCA) return -1;
        return Math.max(vPaths.distTo(LCA), wPaths.distTo(LCA)) + 1; // add one to account for starting vertex
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(this.G, w);
        return findLCA(vPaths, wPaths);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vSubset, Iterable<Integer> wSubset) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(this.G, vSubset);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(this.G, wSubset);
        return findLCA(vPaths, wPaths);
    }

    private int findLCA(BreadthFirstDirectedPaths vPaths, BreadthFirstDirectedPaths wPaths) {
        for (int root : this.roots) {
            // TODO something wrong with this logic
            if (vPaths.hasPathTo(root) && wPaths.hasPathTo(root)) {
                for (int vertex : vPaths.pathTo(root)) {
                    if (wPaths.hasPathTo(vertex)) return vertex;
                }
            }
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}