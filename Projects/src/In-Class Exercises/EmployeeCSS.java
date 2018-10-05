/*
Aron Hubbard & Kyle Hubbard
In-Class Exercise: CSS with Event Handlers
CS 2450 - Programming Graphical User Interfaces
October 4, 2018
*/

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;

public class EmployeeCSS extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label editLabel = new Label("Edit Employee");
        editLabel.setStyle("-fx-font-weight: bold");
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-weight: bold");
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setStyle("-fx-font-weight: bold");
        Label idLabel = new Label("Employee ID:");
        idLabel.setStyle("-fx-font-weight: bold");

        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-radius: 5");
        saveButton.setDisable(true);

        TextField nameField = new TextField();
        nameField.setText("Kyle Hubbard");
        nameField.setId("name-field");
        nameField.textProperty().addListener((source, oldString, newString) -> {
            if(!newString.equals(oldString)) {
                nameField.setStyle("-fx-border-color: #FF0000 ;");
                saveButton.setDisable(false);
            }
        });

        TextField phoneField = new TextField();
        phoneField.setText("123-456-7890");
        phoneField.setId("phone-field");
        phoneField.textProperty().addListener((source, oldString, newString) -> {
            if(!newString.equals(oldString)) {
                phoneField.setStyle("-fx-border-color: #FF0000");
                saveButton.setDisable(false);
            }
        });

        TextField idField = new TextField();
        idField.setText("123ABCD");
        idField.setId("id-field");
        idField.textProperty().addListener((source, oldString, newString) -> {
            if(!newString.equals(oldString)) {
                idField.setStyle("-fx-border-color: #FF0000");
                saveButton.setDisable(false);
            }
        });

        saveButton.setOnAction(event -> {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Employee Changes");
            infoAlert.setHeaderText("Your changes have been saved.");
            infoAlert.show();

            saveButton.setDisable(false);
            nameField.setStyle("");
            phoneField.setStyle("");
            idField.setStyle("");
        });

        GridPane gPane = new GridPane();
        gPane.add(nameLabel, 0, 0);
        gPane.add(phoneLabel, 0, 1);
        gPane.add(idLabel, 0, 2);
        gPane.add(nameField, 1, 0);
        gPane.add(phoneField, 1, 1);
        gPane.add(idField, 1, 2);

        gPane.setHgap(15);
        gPane.setVgap(15);

        VBox mainBox = new VBox(15, editLabel, gPane, saveButton);
        mainBox.setPadding(new Insets(25));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Employee Changes");
        primaryStage.show();
    }
}
