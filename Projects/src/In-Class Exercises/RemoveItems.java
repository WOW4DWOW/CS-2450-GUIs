// Kyle Hubbard
// In-Class Exercise: EventHandling and ObservableLists
// CS 2450 - Programming Graphical User Interfaces
// September 11, 2018

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class RemoveItems extends Application {

    private VBox mainBox = new VBox(10);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label l1 = new Label("Acura");
        Label l2 = new Label("Nissan");
        Label l3 = new Label("Toyota");
        Button b1 = new Button("Remove");
        Button b2 = new Button("Remove");
        Button b3 = new Button("Remove");

        b1.setOnAction(new removeButton());
        b2.setOnAction(new removeButton());
        b3.setOnAction(new removeButton());

        HBox acuraBox = new HBox(10, l1, b1);
        HBox nissanBox = new HBox(10, l2, b2);
        HBox toyotaBox = new HBox(10, l3, b3);
        mainBox.getChildren().addAll(acuraBox, nissanBox, toyotaBox);
        mainBox.setPadding(new Insets(100));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Remove Items");
        primaryStage.show();
    }

    class removeButton implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //Casts the event source to a button and then calls getParent to retrieve the HBox to delete.
            mainBox.getChildren().remove(((Button)event.getSource()).getParent());

            //Exit the program if the VBox is empty.
            if(mainBox.getChildren().isEmpty()) {
                System.exit(0);
            }
        }
    }
}
