/*
Kyle Hubbard
Homework 2: Travel Expenses
CS 2450 - Programming Graphical User Interfaces
September 20, 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class TravelExpenses extends Application {

    private VBox mainBox = new VBox(10);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Node Declaration
        Label labelPrompt = new Label("Please enter all applicable information.");
        Label labelNumDays = new Label("Number of Days on Trip:");
        Label labelAirfare = new Label("Total Airfare:");
        Label labelRentalFees = new Label("Car Rental Fees:");
        Label labelMiles = new Label("Miles Driven (private vehicle):");
        Label labelParkingFees = new Label("Parking Fees:");
        Label labelRegistrationFees = new Label("Seminar Registration Fees:");
        Label labelLodging = new Label("Lodging Charges (per night):");

        TextField fieldNumDays = new TextField();
        TextField fieldAirfare = new TextField();
        TextField fieldRentalFees = new TextField();
        TextField fieldMiles = new TextField();
        TextField fieldParkingFees = new TextField();
        TextField fieldRegistrationFees = new TextField();
        TextField fieldLodging = new TextField();

        //Button and EventHandler Declaration
        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(event -> {

            //Reading in TextFields
            int numDays = Integer.parseInt(fieldNumDays.getText());
            double airfare = Double.parseDouble(fieldAirfare.getText());
            double rentalFees = Double.parseDouble(fieldRentalFees.getText());
            double milesDriven = Double.parseDouble(fieldMiles.getText());
            double parkingFees = Double.parseDouble(fieldParkingFees.getText());
            double registrationFees = Double.parseDouble(fieldRegistrationFees.getText());
            double lodgingCharges = Double.parseDouble(fieldLodging.getText()) * numDays;

            //Parking Reimbursement Logic
            double reimburseParking;
            if (20 * numDays >= parkingFees) {
                reimburseParking = parkingFees;
            } else {
                reimburseParking = 20 * numDays;
            }

            //Lodging Reimbursement Logic
            double reimburseLodging;
            if (195 * numDays >= lodgingCharges) {
                reimburseLodging = lodgingCharges;
            } else {
                reimburseLodging = 195 * numDays;
            }

            double reimburseMiles = milesDriven * 0.42;

            //Result Value Declaration
            double totalExpenses = airfare + rentalFees + parkingFees + registrationFees + lodgingCharges;
            double totalReimburse = (numDays * 47) + reimburseParking + reimburseLodging + reimburseMiles;
            double totalExcess;
            double totalSaved;

            //Excess and Saved Amount Logic
            if (totalExpenses > totalReimburse) {
                totalExcess = totalExpenses - totalReimburse;
                totalSaved = 0;
            } else {
                totalExcess = 0;
                totalSaved = totalReimburse - totalExpenses;
            }

            //---Return Information Formatting---
            labelPrompt.setText("Here is your expenses statement report.");

            //Remove gPane and hide submit button
            mainBox.getChildren().remove(1);
            buttonSubmit.setVisible(false);

            Label labelTotalExpenses = new Label("Total Expenses:");
            Label labelTotalReimburse = new Label("Total Reimbursement:");
            Label labelExcess = new Label("Excess not Reimbursed:");
            Label labelSaved = new Label("Total Amount Saved:");

            //Declare read-only TextFields for return information
            TextField fieldTotalExpenses = new TextField();
            fieldTotalExpenses.setText(String.format("%,.2f", totalExpenses));
            fieldTotalExpenses.setEditable(false);

            TextField fieldTotalReimburse = new TextField();
            fieldTotalReimburse.setText(String.format("%,.2f", totalReimburse));
            fieldTotalReimburse.setEditable(false);

            TextField fieldExcess = new TextField();
            fieldExcess.setText(String.format("%,.2f", totalExcess));
            fieldExcess.setEditable(false);

            TextField fieldSaved = new TextField();
            fieldSaved.setText(String.format("%,.2f", totalSaved));
            fieldSaved.setEditable(false);

            //Formatting GridPane resultPane
            GridPane resultPane = new GridPane();
            resultPane.setHgap(10);
            resultPane.setVgap(10);
            resultPane.setAlignment(Pos.CENTER);
            resultPane.add(labelTotalExpenses, 0, 0);
            resultPane.add(labelTotalReimburse, 0, 1);
            resultPane.add(labelExcess, 0, 2);
            resultPane.add(labelSaved, 0, 3);
            resultPane.add(fieldTotalExpenses, 1, 0);
            resultPane.add(fieldTotalReimburse, 1, 1);
            resultPane.add(fieldExcess, 1, 2);
            resultPane.add(fieldSaved, 1, 3);

            //Replace gPane with resultPane
            mainBox.getChildren().add(1, resultPane);

            //Fix padding to accommodate hiding the submit button
            mainBox.setPadding(new Insets(25, 25, 0, 25));
        });

        //Formatting GridPane gPane
        GridPane gPane = new GridPane();
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setAlignment(Pos.CENTER);
        gPane.add(labelNumDays, 0, 0);
        gPane.add(labelAirfare, 0, 1);
        gPane.add(labelRentalFees, 0, 2);
        gPane.add(labelMiles, 0, 3);
        gPane.add(labelParkingFees, 0, 4);
        gPane.add(labelRegistrationFees, 0, 5);
        gPane.add(labelLodging, 0, 6);
        gPane.add(fieldNumDays, 1, 0);
        gPane.add(fieldAirfare, 1, 1);
        gPane.add(fieldRentalFees, 1, 2);
        gPane.add(fieldMiles, 1, 3);
        gPane.add(fieldParkingFees, 1, 4);
        gPane.add(fieldRegistrationFees, 1, 5);
        gPane.add(fieldLodging, 1, 6);

        //Formatting VBox mainBox
        mainBox.getChildren().addAll(labelPrompt, gPane, buttonSubmit);
        mainBox.setPadding(new Insets(25));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Travel Expenses");
        primaryStage.show();
    }
}
