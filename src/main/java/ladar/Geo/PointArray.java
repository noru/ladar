package main.java.ladar.Geo;

import java.util.concurrent.LinkedBlockingQueue;

public class PointArray {

    public final static int SIZE = 1024;
    private static LinkedBlockingQueue<Point> pointQueue = new LinkedBlockingQueue<>(SIZE);

    public static LinkedBlockingQueue<Point> get() {
        return new LinkedBlockingQueue<>(pointQueue);
    }

    public static void put(Point point) {
        if (pointQueue.remainingCapacity() == 0){
            pointQueue.poll();
        }
        try {
            pointQueue.put(point);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
