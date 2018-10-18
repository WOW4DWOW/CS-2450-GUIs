/*
Aron Hubbard & Kyle Hubbard
In-Class Exercise: BorderPane, ListViews, and Menus
CS 2450 - Programming Graphical User Interfaces
October 16-18, 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class BorderPaneMenus extends Application {

    private BorderPane borderPane = new BorderPane();
    private Menu viewMenu;
    private Menu helpMenu;
    private ListView<String> bookList = new ListView<>();
    private ListView<String> movieList = new ListView<>();
    private boolean showBook = true;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();

        bookList.getItems().addAll("Book of Sorrows", "Marasenna", "The Joker's Wild");
        bookList.setPrefHeight(120);
        movieList.getItems().addAll("The Last Wish", "Wrath of The Machine", "King's Fall");
        movieList.setPrefHeight(120);

        buildViewMenu(primaryStage);
        buildHelpMenu();

        menuBar.getMenus().addAll(viewMenu, helpMenu);
        BorderPane.setMargin(bookList, new Insets(25));
        borderPane.setCenter(bookList);
        borderPane.setTop(menuBar);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("BorderPane Exercise");
        primaryStage.show();
    }

    private void buildViewMenu(Stage primaryStage) {

        viewMenu = new Menu("View");

        RadioMenuItem bookItem = new RadioMenuItem("Books");
        RadioMenuItem movieItem = new RadioMenuItem("Movies");
        ToggleGroup listGroup = new ToggleGroup();
        bookItem.selectedProperty().set(true);
        bookItem.setToggleGroup(listGroup);
        movieItem.setToggleGroup(listGroup);

        MenuItem exitItem = new MenuItem("Exit");

        bookItem.setOnAction(event -> {
            if(!showBook) {
                BorderPane.setMargin(bookList, new Insets(25));
                borderPane.setCenter(bookList);
                showBook = true;
            }
        });

        movieItem.setOnAction(event -> {
            if(showBook) {
                BorderPane.setMargin(movieList, new Insets(25));
                borderPane.setCenter(movieList);
                showBook = false;
            }
        });

        exitItem.setOnAction(event -> {
            primaryStage.close();
        });

        viewMenu.getItems().addAll(bookItem, movieItem, new SeparatorMenuItem(), exitItem);
    }

    private void buildHelpMenu() {

        helpMenu = new Menu("Help");

        MenuItem aboutItem = new MenuItem("About");

        aboutItem.setOnAction(event -> {
            Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
            aboutAlert.setTitle("BorderPane Exercise About Page");
            aboutAlert.setHeaderText("Aron Hubbard & Kyle Hubbard");
            aboutAlert.setContentText("Copyright 10/18/2018");
            aboutAlert.show();
        });

        helpMenu.getItems().add(aboutItem);
    }
}
