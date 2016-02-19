package core;

import java.util.*;

public class Rectangle {
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    HashMap<String, Double> hm;

    public Rectangle(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        System.out.println(this);
    }

    public Rectangle() {
    }

    // input from command line
    public void inputCoordinates() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter X1");
        x1 = scanner.nextDouble();
        System.out.print("Enter X2");
        x2 = scanner.nextDouble();
        System.out.print("Enter Y1");
        y1 = scanner.nextDouble();
        System.out.print("Enter Y2");
        y2 = scanner.nextDouble();

    }

    // complete this function input from a JSON file
    public void getCoordinates(int arrayEndPoints[]) {

    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                '}';
    }
}
