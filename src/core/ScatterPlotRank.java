package core;

import database.DataStore;
import database.ListElement;
import database.ScatterPlotIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ScatterPlotRank {
    public DataStore dataStore;
    public ScatterPlotIndex xList;
    public ScatterPlotIndex yList;
    public Rectangle[] rectangles = new Rectangle[2];
    HashMap<String, Double> hm = new HashMap<String, Double>();//will be used to map product-count pairs


    //constructor
    public ScatterPlotRank(ScatterPlotIndex x, ScatterPlotIndex y, Rectangle[] userRect, DataStore d) {
        dataStore = d;
        xList = x;
        yList = y;
        rectangles = userRect;

    }


    /**
     * Main logic function that is called from Controller
     *
     * @param printWorking
     */
    public void ranking(boolean printWorking) {

        //iterate through all user Rectangles
        for (int i = 0; rectangles[i] != null; i++) {
            ArrayList<ListElement> xSet = new ArrayList<ListElement>();
            ArrayList<ListElement> ySet = new ArrayList<ListElement>();
            //starting position in indexed lists
            int xIndexCounter = xList.getIndexByCoordinate(rectangles[i].x1);
            int yIndexCounter = yList.getIndexByCoordinate(rectangles[i].y1);

            //ending coordinates
            double xEnd = rectangles[i].x2;
            double yEnd = rectangles[i].y2;

            //is there even one point in the lists?
            boolean xPossible = true;
            boolean yPossible = true;

            ListElement xCurrent;
            ListElement yCurrent;

            try {
                xCurrent = xList.list.get(xIndexCounter);
            } catch (IndexOutOfBoundsException e) {
                xPossible = false;
            }

            try {
                yCurrent = yList.list.get(yIndexCounter);
            } catch (IndexOutOfBoundsException e) {
                yPossible = false;
            }

            //only proceeds from here if the points existed

            if (xPossible)
                while (xList.list.get(xIndexCounter).coordinate < xEnd) {
//				System.out.println(xIndexCounter);
                    xCurrent = xList.list.get(xIndexCounter);
                    xSet.add(xCurrent);
                    xIndexCounter++;
                    if (xIndexCounter >= xList.list.size())
                        break;
                }

            if (yPossible)
                while (yList.list.get(yIndexCounter).coordinate < yEnd) {
                    yCurrent = yList.list.get(yIndexCounter);
                    ySet.add(yCurrent);
                    yIndexCounter++;
                    if (yIndexCounter >= yList.list.size())
                        break;
                }

            xSet.retainAll(ySet);
            rectangles[i].hm = countProductsInList(xSet);
        }
        // ---------- only prints if the printWorking is set to true
//		if(printWorking)
//		{
//			System.out.println("Printing DataStore.");
//			dataStore.printDataStore();
//
//			System.out.println("Printing X-List:");
//			printTupleList(xList.list);
//
//			System.out.println("Printing Y-List:");
//			printTupleList(yList.list);
//
//			System.out.println("Printing X-Set:");
//			printTupleList(xSet);
//			System.out.println("Printing Y-Set:");
//			printTupleList(ySet);
//
//			System.out.println("Printing intersection");
//			xSet.retainAll(ySet);
//			printTupleList(xSet);
//		}
//		else
//			xSet.retainAll(ySet);
//
//		countProductsInList(xSet);
    }

    /**
     * This function uses a Hash Map to first count the number
     * of points in each category
     * and saves in hashmap hm
     *
     * @param list
     */
    public HashMap<String, Double> countProductsInList(ArrayList<ListElement> list) {
        HashMap<String, Double> hm = new HashMap<String, Double>();
        // Put elements to the map
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).t.product;
            double score = list.get(i).t.score;
            Double value = hm.get(key);
            if (value != null) {
                hm.put(key, new Double(value + score));

            } else {
                hm.put(key, new Double(score));
            }

        }
        return hm;
    }


    /**
     * and then prints out the results
     */
    public String printRanking(HashMap<String, Double> hm) {
        TreeMap<String, Double> tree = new TreeMap<String, Double>();
        tree.putAll(hm);

        // we are going to return this string
        StringBuilder toReturn = new StringBuilder();

        for (Map.Entry<String, Double> entry : tree.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            toReturn.append(key + ":" + value + '\n');

        }
        return toReturn.toString();
    }

    /**
     * Self-explanatory
     *
     * @param list to print
     */
    public void printTupleList(ArrayList<ListElement> list) {
        if (list.size() == 0) {
            System.out.println("Nothing to print. List empty");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).t.print();
        }
        System.out.println();
    }
}
