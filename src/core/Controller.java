package core;

import database.*;
import dataloader.DataLoader;

public class Controller {
    DataLoader dataLoader;
    DataStore dataStore;
    ScatterPlotIndex xIndex;
    ScatterPlotIndex yIndex;

    //Sets up datastore, indexes the points
    void setup() {

        // Load data and gets datastore
        dataStore = new DataStore();
        dataLoader = new DataLoader(dataStore, 2); //load by random test data

        // attach normalized scores to every distinct category
        CategoryPointsCount c = new CategoryPointsCount(dataStore);

        // Index
        xIndex = new ScatterPlotIndex(dataStore, 0);
        yIndex = new ScatterPlotIndex(dataStore, 1);

    }


    // Takes userInput and performs the main logic 
    public void getScatterPlots(Rectangle[] userInput, boolean printWorking) {

        // Initialize scatterPlotRank
        ScatterPlotRank scatterPlotRank = new ScatterPlotRank(xIndex, yIndex, userInput, dataStore);

        // Call logic function in scatterPlotRank
        scatterPlotRank.ranking(printWorking);

        // Print Results.
        System.out.println(scatterPlotRank.printRanking(userInput[0].hm));
        System.out.println("--------------");
    }


    public static void main(String[] args) {

        //will be used to check how much time the code took
        long startTime = System.nanoTime();

        //CODE BEGINS HERE

        Controller c = new Controller();

        // It first setups
        c.setup();

        // allocates space for taking 4 rectangles
        Rectangle[] userInput = new Rectangle[4];

        // first rectangle
        Rectangle userRect = new Rectangle();
        userRect.inputCoordinates(); // takes input from command line
        userInput[0] = userRect;

        //  ------ to add more rectangles, just repeat the above three lines of code
        // ------- for other rectangles


        c.getScatterPlots(userInput, false);


        //CODE ENDS HERE
        // The next lines are to print the time it took to run the program.
        long endTime = System.nanoTime();
        double seconds = (endTime - startTime) / Math.pow(10, 9);
        System.out.println("Took " + seconds + " s");


    }


}
