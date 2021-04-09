package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // Initialize Adjacency list
	    AdjacencyList<String> myList = new AdjacencyList<>();

	    /* ---------- READ CSV -----------*/
        // If you get an IOexception or FileReadException replace this code with the first comment below Main class
        // Open CSV
	    String file = Main.class.getResource( "/adjacency.csv" ).getFile();

	    // Load values
        BufferedReader csvReader = new BufferedReader( new FileReader(  file ));
        String row;
        while ( (row = csvReader.readLine()) != null ) {    // Iterate through each row
            String[] lineData = row.split(",");       // Split row by commas
            myList.add( lineData[0], lineData[1], Integer.parseInt(lineData[2]) );  // Load data into adjacency list
        }
        /* ----------END READ CSV --------*/

        // Print out the adj list we just generated
        System.out.println( myList );

        // Prims algorithm for minimum spanning tree
        try {
            // Might throw an exception if all vertices arent connected
            // Run prims on the adjacency list
            ArrayList<Edge<String>> MST = myList.prims("Start");

            int totalDistance = 0;

            // Loop through the Min Span Tree (of edges) print the edges, and add up their distance
            System.out.println( "\nMinimum Spanning Tree:" );
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

/*
*  ---- DIRECT ADD OF THE DATA TO THE ADJ LIST -----
*  Copy paste this to replace the code between the READ CSV comments if the CSV file wont load
*  The information is exactly the same

myList.add("Eskildstrup", "Maribo", 28);
myList.add("Eskildstrup", "NykøbingF", 13);
myList.add("Eskildstrup", "Vordingborg", 24);
myList.add("Haslev", "Korsør", 60);
myList.add("Haslev", "Køge", 24);
myList.add("Haslev", "Næstved", 25);
myList.add("Haslev", "Ringsted", 19);
myList.add("Haslev", "Roskilde", 47);
myList.add("Haslev", "Slagelse", 48);
myList.add("Haslev", "Sorø", 34);
myList.add("Haslev", "Vordingborg", 40);
myList.add("Holbæk", "Jægerspris", 34);
myList.add("Holbæk", "Kalundborg", 44);
myList.add("Holbæk", "Korsør", 66);
myList.add("Holbæk", "Ringsted", 36);
myList.add("Holbæk", "Roskilde", 32);
myList.add("Holbæk", "Slagelse", 46);
myList.add("Holbæk", "Sorø", 34);
myList.add("Jægerspris", "Korsør", 95);
myList.add("Jægerspris", "Køge", 58);
myList.add("Jægerspris", "Ringsted", 56);
myList.add("Jægerspris", "Roskilde", 33);
myList.add("Jægerspris", "Slagelse", 74);
myList.add("Jægerspris", "Sorø", 63);
myList.add("Kalundborg", "Ringsted", 62);
myList.add("Kalundborg", "Roskilde", 70);
myList.add("Kalundborg", "Slagelse", 39);
myList.add("Kalundborg", "Sorø", 51);
myList.add("Korsør", "Næstved", 45);
myList.add("Korsør", "Slagelse", 20);
myList.add("Køge", "Næstved", 45);
myList.add("Køge", "Ringsted", 28);
myList.add("Køge", "Roskilde", 25);
myList.add("Køge", "Vordingborg", 60);
myList.add("Maribo", "Nakskov", 27);
myList.add("Maribo", "NykøbingF", 26);
myList.add("Næstved", "Roskilde", 57);
myList.add("Næstved", "Ringsted", 26);
myList.add("Næstved", "Slagelse", 37);
myList.add("Næstved", "Sorø", 32);
myList.add("Næstved", "Vordingborg", 28);
myList.add("Ringsted", "Roskilde", 31);
myList.add("Ringsted", "Sorø", 15);
myList.add("Ringsted", "Vordingborg", 58);
myList.add("Slagelse", "Sorø", 14);
*/

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

/* --------------- WAS USED FOR FINDING OUT WHY LINE's MINHEAP BROKE MY CODE --------------
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