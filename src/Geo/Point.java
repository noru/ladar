package Geo;

import sample.Main;

import java.util.Random;

public class Point{
    public double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Point getRandom(){
        Random r = new Random();
        return new Point(r.nextDouble() * Main.WIDTH, r.nextDouble() * Main.HEIGHT);
    }

    public static Point fromPolar(double modulus, double angle){
        return new Point(modulus * Math.cos(angle), modulus * Math.sin(angle));
    }
}