/******************************************************************************
 *  Compilation:  javac BFSPaths.java
 *  Dependencies: Digraph.java Queue.java Stack.java
 * 
 *  Run breadth-first search on a digraph.
 *
 ******************************************************************************/

package com.falldowngoboone.classwork.wordnet;

import edu.princeton.cs.algs4.*;

public class LCA {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked1;  // marked[v] = is there an s1->v path?
    private int[] distTo1;      // distTo[v] = length of shortest s1->v path
    private boolean[] marked2;  // marked[v] = is there an s2->v path?
    private int[] distTo2;      // distTo[v] = length of shortest s2->v path
    private int lca;            // LCA       = lowest common ancestor between s1 and s2
    private int dist;

    /**
     * @param G the digraph
     * @param s1 the first source vertex
     * @param s2 the second source vertex
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public LCA(Digraph G, int s1, int s2) {
        marked1 = new boolean[G.V()];
        distTo1 = new int[G.V()];
        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo1[v] = INFINITY;
            distTo2[v] = INFINITY;
        }
        lca = -1;
        dist = INFINITY;
        validateVertex(s1);
        validateVertex(s2);
        bfs(G, s1, s2);
    }

    /**
     * @param G the digraph
     * @param sources1 the first source vertices
     * @param sources2 the second source vertices
     * @throws IllegalArgumentException unless each vertex {@code v} in
     *         {@code sources} satisfies {@code 0 <= v < V}
     */
    public LCA(Digraph G, Iterable<Integer> sources1, Iterable<Integer> sources2) {
        marked1 = new boolean[G.V()];
        distTo1 = new int[G.V()];
        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo1[v] = INFINITY;
            distTo2[v] = INFINITY;
        }
        lca = -1;
        dist = INFINITY;
        validateVertices(sources1);
        validateVertices(sources2);
        bfs(G, sources1, sources2);
    }

    // BFS from single source
    private void bfs(Digraph G, int s1, int s2) {
        Queue<Integer> q1 = new Queue<Integer>();
        marked1[s1] = true;
        distTo1[s1] = 0;
        q1.enqueue(s1);
        Queue<Integer> q2 = new Queue<Integer>();
        marked2[s2] = true;
        distTo2[s2] = 0;
        q2.enqueue(s2);
        while ((!q1.isEmpty() || !q2.isEmpty())) {
            if (!q1.isEmpty()) {
                int v = q1.dequeue();
                
                if (marked2[v] && dist > distTo1[v] + distTo2[v]) {
                    lca = v;
                    dist = distTo1[v] + distTo2[v];
                }
                
                for (int w : G.adj(v)) {
                    if (!marked1[w]) {
                        distTo1[w] = distTo1[v] + 1;
                        marked1[w] = true;
                        q1.enqueue(w);
                    }
                }
            }
            if (!q2.isEmpty()) {
                int v = q2.dequeue();

                if (marked1[v] && dist > distTo1[v] + distTo2[v]) {
                    lca = v;
                    dist = distTo1[v] + distTo2[v];
                }
                
                for (int w : G.adj(v)) {
                    if (!marked2[w]) {
                        distTo2[w] = distTo2[v] + 1;
                        marked2[w] = true;
                        q2.enqueue(w);
                    }
                }
            }
        }
    }

    // BFS from multiple sources
    private void bfs(Digraph G, Iterable<Integer> sources1, Iterable<Integer> sources2) {
        Queue<Integer> q1 = new Queue<Integer>();
        for (int s : sources1) {
            marked1[s] = true;
            distTo1[s] = 0;
            q1.enqueue(s);
        }
        Queue<Integer> q2 = new Queue<Integer>();
        for (int s : sources2) {
            marked2[s] = true;
            distTo2[s] = 0;
            q2.enqueue(s);
        }
        while ((!q1.isEmpty() || !q2.isEmpty())) {
            if (!q1.isEmpty()) {
                int v = q1.dequeue();

                if (marked2[v] && dist > distTo1[v] + distTo2[v]) {
                    lca = v;
                    dist = distTo1[v] + distTo2[v];
                }

                for (int w : G.adj(v)) {
                    if (!marked1[w]) {
                        distTo1[w] = distTo1[v] + 1;
                        marked1[w] = true;
                        q1.enqueue(w);
                    }
                }
            }
            if (!q2.isEmpty()) {
                int v = q2.dequeue();

                if (marked1[v] && dist > distTo1[v] + distTo2[v]) {
                    lca = v;
                    dist = distTo1[v] + distTo2[v];
                }

                for (int w : G.adj(v)) {
                    if (!marked2[w]) {
                        distTo2[w] = distTo2[v] + 1;
                        marked2[w] = true;
                        q2.enqueue(w);
                    }
                }
            }
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked1.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked1.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

    public int getLCA() {
        return lca;
    }

    public int getSAP() {
        return lca != -1 ? distTo1[lca] + distTo2[lca] : -1;
    }
}