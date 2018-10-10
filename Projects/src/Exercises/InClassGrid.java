/*
Kyle Hubbard
In-Class Exercise
CS 2450 - Programming Graphical User Interfaces
September 6, 2018
*/

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;

public class InClassGrid extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Node Declaration
        Label lEnter = new Label("Please Enter the Following: ");
        Label lName = new Label("Name");
        Label lPhone = new Label("Phone #");
        Label lEmail = new Label("Email");
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();

        //GridPane Formatting
        GridPane gridPane = new GridPane();
        gridPane.add(lEnter, 0, 1);
        gridPane.add(lName, 1, 0);
        gridPane.add(textField, 2, 0);
        gridPane.add(lPhone, 1, 1);
        gridPane.add(textField2, 2, 1);
        gridPane.add(lEmail, 1, 2);
        gridPane.add(textField3, 2, 2);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));

        //Layout Containers
        HBox buttonBox = new HBox(55, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 15, 0));
        VBox mainBox = new VBox(10, gridPane, buttonBox);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("In Class Grid");
        primaryStage.show();
    }
}
