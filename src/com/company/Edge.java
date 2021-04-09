package com.company;

/**
 * Implementation of the Edge system, mainly for making it look nice
 * @param <T> The same type as the adjacency list is made with, as it contains 2 nodes
 */
public class Edge<T> implements Comparable<Edge<T>> {
    public T from;
    public T to;
    public Integer weight;

    /**
     * Create a vertex with "from" node to the "to" node with weight "weight"
     * @param from Node of type T
     * @param to   Node of type T
     * @param weight : Integer - The cost of travelling this node
     */
    public Edge(T from, T to, Integer weight ) {
        this.from     =     from;
        this.to       =       to;
        this.weight   =   weight;
    }

    @Override
    public int compareTo(Edge o) {
        // Branchless because we are using int
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return this.from.toString() + " -- " + this.weight.toString() + "km -- " + this.to.toString();
    }
}
