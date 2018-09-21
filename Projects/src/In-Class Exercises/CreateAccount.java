/*
Kyle Hubbard
In-Class Exercise: Alerts
CS 2450 - Programming Graphical User Interfaces
September 20, 2018
*/

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class CreateAccount extends Application {
    private TextField nameField = new TextField();
    private PasswordField passField = new PasswordField();
    private PasswordField passField2 = new PasswordField();
    private Button submit = new Button("Submit");

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Declarations
        Label prompt = new Label("Create an Account");
        Label userName = new Label("User Name");
        Label passWord = new Label("Password");
        Label passWord2 = new Label("Re-enter Password");

        //Attach listener to each Text/PassField
        TextFieldListener listener = new TextFieldListener();
        nameField.textProperty().addListener(listener);
        passField.textProperty().addListener(listener);
        passField2.textProperty().addListener(listener);

        //Submit Button and Alert
        submit.setDisable(true);
        submit.setOnAction(event -> {
            Alert submitAlert = new Alert(AlertType.INFORMATION);
            submitAlert.setTitle("Submit Alert");
            submitAlert.setHeaderText("Account Created!");
            submitAlert.showAndWait();
        });

        //Quit Button and Alert
        Button quit = new Button("Quit");
        quit.setOnAction(event -> {
            Alert quitAlert = new Alert(AlertType.CONFIRMATION);
            quitAlert.setTitle("Quit Alert");
            quitAlert.setHeaderText("Are you sure you want to quit?");
            Optional result = quitAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        //Formatting
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

    //Text & PassField Listener
    private class TextFieldListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> source, String oldValue, String newValue) {
            String nameValue = nameField.getText();
            String passValue = passField.getText();
            String pass2Value = passField2.getText();

            boolean fieldsFilled = !(nameValue.trim().equals("")) && !(passValue.trim().equals("")) && !(pass2Value.trim().equals(""));
            boolean passMatch = passValue.equals(pass2Value);

            submit.setDisable(!fieldsFilled || !passMatch);
        }
    }
}
