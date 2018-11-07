/*
Kyle Hubbard
In-Class Exercise: Shapes
CS 2450 - Programming Graphical User Interfaces
November 6, 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Shapes extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Rectangle house = new Rectangle(200, 250, 300, 200);
        house.setFill(Color.BROWN);
        Line roofLine1 = new Line(350, 100, 200, 250);
        Line roofLine2 = new Line(200, 250, 500, 250);
        Line roofLine3 = new Line(500, 250, 350, 100);

        Rectangle window1 = new Rectangle(235, 350, 50, 50);
        Rectangle windowInner1 = new Rectangle(245, 360, 30, 30);
        windowInner1.setFill(Color.LIGHTBLUE);

        Rectangle window2 = new Rectangle(415, 350, 50, 50);
        Rectangle windowInner2 = new Rectangle(425, 360, 30, 30);
        windowInner2.setFill(Color.LIGHTBLUE);

        Rectangle door = new Rectangle(325, 363, 50, 85);

        Rectangle grass = new Rectangle(0, 450, 700, 300);
        grass.setFill(Color.GREEN);

        Pane pane = new Pane(house, roofLine1, roofLine2, roofLine3, window1, windowInner1, window2, windowInner2, door, grass);

        primaryStage.setScene(new Scene(pane, 700, 700));
        primaryStage.setTitle("Shapes");
        primaryStage.show();
    }
}
