// Kyle Hubbard
// In-Class Exercise
// CS 2450 - Programming Graphical User Interfaces
// September 6, 2018

import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.AlertType;

import javax.swing.event.*;
import javax.swing.event.ChangeListener;
import java.util.*;

public class CreateAccount extends Application {
    private TextField nameField = new TextField();
    private PasswordField passField = new PasswordField();
    private PasswordField passField2 = new PasswordField();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label prompt = new Label("Create an Account");
        Label userName = new Label("User Name");
        Label passWord = new Label("Password");
        Label passWord2 = new Label("Re-enter Password");

        TextFieldListener listener = new TextFieldListener();
        nameField.textProperty().addListener(listener);
        passField.textProperty().addListener(listener);
        passField2.textProperty().addListener(listener);

        Button submit = new Button("Submit");
        submit.setDisable(true);
        submit.setOnAction((source, oldValue, newValue) -> {
            Alert submitAlert = new Alert(AlertType.CONFIRMATION);
            submitAlert.showAndWait();
        });

        Button quit = new Button("Quit");
        quit.setOnAction(event -> {
            Alert accountAlert = new Alert(AlertType.CONFIRMATION);
            accountAlert.setTitle("");
            accountAlert.setHeaderText("Are you sure you want to quit?");
            Optional result = accountAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        GridPane gPane = new GridPane();
        gPane.setVgap(10);
        gPane.setHgap(10);
        gPane.add(userName, 0, 0);
        gPane.add(passWord, 0, 1);
        gPane.add(passWord2, 0, 2);
        gPane.add(nameField, 1, 0);
        gPane.add(passField, 1, 1);
        gPane.add(passField2, 1, 2);
        gPane.add(submit, 0, 3);
        gPane.add(quit, 1, 3);

        VBox mainBox = new VBox(10, prompt, gPane);
        mainBox.setPadding(new Insets(15));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Create an Account");
        primaryStage.show();
    }

    private class TextFieldListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue) {
            String nameValue = nameField.getText();
            String passValue = passField.getText();
            String pass2Value = passField2.getText();

            boolean notFilled = nameValue.equals("") && passField.equals("") && passField2.equals("");
            boolean notMatched = passValue.equals(pass2Value);

            submit.setDisable(notFilled && notMatched);
        }
    }
}
