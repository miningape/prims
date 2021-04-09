package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        /* --------------- WAS USED FOR FINDINGOUT WHY LINE's MINHEAP BROKE MY CODE --------------
            MinHeapLine<Integer> KQ = new MinHeapLine<>();
            KQ.add( 5 );
            KQ.add( 2 );
            KQ.add( 1 );
            System.out.println(KQ.poll());
            System.out.println(KQ.poll());
            System.out.println(KQ.poll());
            KQ.add(15);
            System.out.println(KQ.poll());
        */

        // Initialize Adjacency list
	    AdjacencyList<String> myList = new AdjacencyList<String>();

	    String file = Main.class.getResource( "/adjacency.csv" ).getFile();
	    System.out.println(file);

	    // Add the table from the assignment (This is the exact format is is given in
        BufferedReader csvReader = new BufferedReader( new FileReader(  file ));
        String row;
        while ( (row = csvReader.readLine()) != null ) {
            String[] lineData = row.split(",");
            myList.add( lineData[0], lineData[1], Integer.parseInt(lineData[2]) );
        }
        // Print out the adj list
        System.out.println( myList );

        try {
            // Might throw an exception if all vertices arent connected
            ArrayList<Edge<String>> MST = myList.prims("Start");
            System.out.println( "\nMinimum Spanning Tree:" );

            int totalDistance = 0;

            // Loop through the Min Span Tree (of edges) print the edges, and add up their distance
            for ( Edge<String> e : MST ) {
                // ! This could also be done in the prims method but I decided to make it separate
                System.out.println( "\t" + e );
                totalDistance += e.weight;
            }

            // Print the distance and cost of the tree
            System.out.println( "Total Distance: " + totalDistance + " km");
            System.out.println( "Total Cost: " + totalDistance * 10_000 + " DKK" );

        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }
}

// Smaller graph for testing, based on the graph on (https://algotree.org/algorithms/minimum_spanning_tree/prims_java/)
        /*myList.add( "0", "1", 4 );
        myList.add( "0", "2", 1 );
        myList.add( "0", "3", 5 );
        myList.add( "1", "3", 2 );
        myList.add( "1", "4", 3 );
        myList.add( "1", "5", 3 );
        myList.add( "2", "3", 2 );
        myList.add( "2", "4", 8 );
        myList.add( "4", "3", 1 );
        myList.add( "4", "5", 4 );*/

// Slightly larger graph for testing, based on the 2nd graph on (https://algotree.org/algorithms/minimum_spanning_tree/prims_java/)
        /*myList.add("1", "2", 2);
        myList.add("1", "0", 1);
        myList.add("1", "6", 2);
        myList.add("6", "0", 1);
        myList.add("6", "5", 1);
        myList.add("5", "0", 2);
        myList.add("5", "4", 2);
        myList.add("4", "0", 1);
        myList.add("4", "3", 2);
        myList.add("3", "2", 1);
        myList.add("3", "0", 1);
        myList.add("2", "0", 2);*/