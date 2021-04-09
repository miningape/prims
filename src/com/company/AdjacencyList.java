package com.company;

import java.util.*;

/**
 * Creates an adjacency list of elements with type T
 * @param <T> the type each node/vertex is
 *
 * @apiNote The class used for the object type must override .equals( T obj ) and .hashCode()
 * @apiNote The distance/Cost is always of Integer type
 */
public class AdjacencyList<T> {
    public HashMap<T, ArrayList<T>>           vertices;
    public HashMap<T, ArrayList<Integer>>     weights;

    /**
     * Creates an adjacency list of elements with type T, that stores weights
     * <T> is the the type each node/vertex is
     *
     * @apiNote The class used for the object type must override .equals( T obj ) and .hashCode()
     * @apiNote The distance/Cost is always of Integer type
     */
    public AdjacencyList() {
        // Using hashmaps for O(1) lookup
        // The arrayList maintains its order but can grow so I use it to store the node that is adjacent and its distance on the same index
        this.vertices = new HashMap<>();
        this.weights = new HashMap<>();
    }

    /**
     * Adds nodes to the adjacency list, in a bidirectional way, it just calls the unidirectional method in both directions
     *
     * @param node1 The first node/vertex
     * @param node2 The second node/vertex
     * @param distance : Integer - The distance/cost for travelling from the first node to the second or vice versa
     */
    public void add(T node1, T node2, Integer distance ) {
        addImplementation(node1, node2, distance);   // Add node2 to node1's adj list
        addImplementation(node2, node1, distance);   // Add node1 to node2's adj list
    }

    /**
     * Actual implementation of the add method, but it only adds in a directional way
     *
     * @param list The node/vertex whose list we will be appending to
     * @param element The node/vertex we are appending to the list
     * @param distance : Integer - The weight to travel to the node "element" from "list"
     */
    public void addImplementation(T list, T element, Integer distance) {
        // If the vertex from has not been used yet we need to create a hashmap for it
        if ( ! this.vertices.containsKey( list ) ) {
            // Add the hashmap to the vertex
            this.vertices.put( list, new ArrayList<>() );
            this.weights.put( list, new ArrayList<>() );
        }

        // Add the values
        if ( ! this.vertices.get(list).contains( element ) ) {
            // Push "element" and "distance" to the "list" nodes adjacency list
            this.vertices.get(list).add( element );
            this.weights.get(list).add( distance );
        } else {
            // If these exact elements have already been added to the list (in this combination)
            // Ill "throw" an error so I can see where the data con
            System.out.println("conflict:\n\tfrom: " + list + "\n\tto: " + element + "\n\tCurrent Distance: " +
                    this.weights.get(list).get(this.vertices.get(list).indexOf(element)) +
                    "\n\tNew Distance (ignored): " + distance );
        }
    }

    /**
     * Method runs Prims Algorithm on the Adjacency List and returns a minimum spanning tree of all the edges
     *
     * @param startTitle What to name the start of the list
     * @return of type ArrayList<Edge<T>>> where T is the type used to construct the arraylist class
     * @apiNote The class used for the object type must override .equals( T obj ) and .hashCode()
     * @apiNote This method runs in O( (V+E)log E ), this is because this algorithm uses edges instead vertices inside
     *          the MH this is because I didn't realize we were supposed to modify values inside the MH until it was too late
     */
    public ArrayList<Edge<T>> prims(T startTitle ) throws NoSuchElementException {
        PriorityQueue<Edge<T>> PQ        = new PriorityQueue<>();       // Our Priority queue O(log n) (Since it is set to put the lowest element in front it is also a min-heap)

        /*
        * --------------- DIFFERENT MIN HEAPS FOR TESTING -------------------
        * MinHeapKyle<Edge<T>> PQ          = new MinHeapKyle<>();       ->  My minheap
        * MinHeapLine<Edge<T>> PQ          = new MinHeapLine<>();       ->  The one made in class (available on moodle) (Doesnt work)
        * MinHeapOnline<Edge<T>> PQ        = new MinHeapOnline<>();     ->  A test minheap I found on google (just the first result)
        * PriorityQueue<Edge<T>> PQ        = new PriorityQueue<>();     ->  Java's implementation of a priority queue, depending on the comparator this is either min or max heap, in this case it is a minheap
        * */

        Set<T> visited                  = new HashSet<>();            // Using this for the O(1) lookup time to check if an element is inside it
        ArrayList<Edge<T>>     MST    = new ArrayList<>();           // Variable that contains our minimum spanning tree

        T current = this.vertices.keySet().iterator().next();         // Get a "random" element to start (its just the element at the top of the vertices key set)
        PQ.add( new Edge<>( startTitle, current, 0 ) );        // Add an edge from "start" to the first node we will explore from (distance:0 so it doesnt affect calculations)

        int numVertices = this.vertices.keySet().size();

        // Repeat until we have examined every edge available
        while ( ! PQ.isEmpty() && visited.size() < numVertices ) {  //O(E) in practise probably a lot faster
            // O( V log E + E log E ) -> O( E+V log E ) (Adding complexities because the for loop is implicitely called O(2E) times as that is the total number of elements in adjacency graph)
            // Pop the queue for the shortest edge
            Edge<T> min = PQ.poll();                                //O(Log E)
            current = min.to;

            if ( ! visited.contains( current ) ) {   // If we havent travelled here before ( O(1) )
                visited.add( current );               // Note that we have been here ( Amortized O(1) )
                MST.add( min );                      // O(1)

                // Get adjacent vertices from adj list along with their weights O(1)
                ArrayList<T> adjList          =  this.vertices.get( min.to );
                ArrayList<Integer> weightList =  this.weights.get( min.to );

                // For each vertex that is adjacent
                for (int i = 0; i < adjList.size(); i++) {          // special case because runtime is not affected by outer loop: O(V*deg(V)) = O ( sum( deg(V) ) ) = O( 2E ) (Handshaking lemma) -> O(E)
                    // Create edge and add to Priority queue if it connects to an unknown
                    if ( !visited.contains(adjList.get(i)) ) {      // No need to check if cost is lower because it is always lowest thanks to PQ
                        // Add this new edge to the min-heap
                        PQ.add( new Edge<>( current, adjList.get(i), weightList.get(i) ) ); // O(log E)
                    }
                }
            }
        }

        // Little bit of error checking to make sure we are getting a minimum spanning tree to all vertices
        if ( visited.size() != numVertices ) {
            for ( Edge<T> e : MST ) {
                // ! This could also be done in the prims method but I decided to make it separate
                System.out.println( "\t" + e );
            }
            throw new NoSuchElementException("Can't Reach Every Vertex");
        }

        return MST;
    }

    @Override
    public String toString() {
        String builtList = "";

        for ( T v : vertices.keySet() ) {
            builtList += "\n\t" + v.toString() + ": ";

            ArrayList<T> adjList    = vertices.get(v);
            ArrayList<Integer> weightList = weights.get(v);

            for ( int i = 0; i < adjList.size(); i++ ) {
                builtList += adjList.get(i).toString() + "(" + weightList.get(i).toString() + " km), ";
            }
        }

        return "AdjacencyList:" + builtList;
    }
}
