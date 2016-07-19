package main.java.ladar;

import main.java.ladar.Geo.Point;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.ladar.Geo.PointArray;

import java.util.concurrent.LinkedBlockingQueue;


public class Main extends Application {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
    public static final long REFRESH_RATE = 1000000000; // 1 second
    public static final Point CENTER = new Point(WIDTH /2, HEIGHT /2);

    StackPane root = new StackPane();
    static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    static GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
        primaryStage.setTitle("Hello World");
        root.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            long last;
            @Override
            public void handle(long now) {
                if (now - last > REFRESH_RATE){
                    ClearCanvas();
//                    DrawRandomPoints();
//                    DrawCircle();
                    DrawMap();
                    last = now;
                }
            }
        };
        timer.start();
        new ComReader().start();
    }


    public void ClearCanvas() {
        root.getChildren().remove(canvas);
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
    }

    public void DrawMap(){

        for (int j = 0; j < 1024; j++){
            PointArray.put(Point.getRandom());
        }

        LinkedBlockingQueue<Point> q = PointArray.get();
        for (int i = 0; i < q.size(); i++){
            Point p = q.poll();
            p.x += CENTER.x; // offset
            p.y += CENTER.y; // offset
            if (i == 0){
                gc.moveTo(p.x, p.y);
            } else {
                gc.lineTo(p.x, p.y);
            }
        }
        gc.stroke();
        gc.setFill(Color.RED);
        gc.fillOval(CENTER.x, CENTER.y, 10, 10);
    }

    public void DrawRandomPoints() {
        Point p = Point.getRandom();
        for (int i = 0; i <= 3; i++){
            gc.moveTo(p.x, p.y);
            p = Point.getRandom();
            gc.lineTo(p.x, p.y);
        }
        gc.stroke();
        gc.setFill(Color.RED);
        gc.fillOval(CENTER.x - 5, CENTER.y - 5, 10, 10);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
