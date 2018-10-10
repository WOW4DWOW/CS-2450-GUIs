/*
Kyle Hubbard
In-Class Exercise: RadioBoxes and CheckBoxes
CS 2450 - Programming Graphical User Interfaces
October 9, 2018
*/

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CellPhonePlan extends Application {

    Label result = new Label("Payment Plan: $299.95 and $45.00 per month");

    RadioButton phone1 = new RadioButton("Model 100: $299.95");
    RadioButton phone2 = new RadioButton("Model 110: $399.95");
    RadioButton phone3 = new RadioButton("Model 200: $499.95");

    RadioButton plan1 = new RadioButton("8 GB per month: $45.00 per month");
    RadioButton plan2 = new RadioButton("16 GB per month: $65.00 per month");
    RadioButton plan3 = new RadioButton("20 GB per month: $90.00 per month");

    CheckBox insuranceCheck = new CheckBox("Insurance: $5.00 per month");
    CheckBox wifiCheck = new CheckBox("Wifi Hotspot Capability: $10.00 per month");

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label phoneL = new Label("Phones (can only select 1)");
        phoneL.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        Label dataL = new Label("Data Plans (can only select 1)");
        dataL.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        Label extraL = new Label("Extras (can select up to 2)");
        extraL.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        result.setStyle("-fx-font-weight: bold");

        ToggleGroup phoneGroup = new ToggleGroup();
        phone1.setToggleGroup(phoneGroup);
        phone1.setSelected(true);
        phone1.setOnAction(new UpdatePlan());
        phone2.setToggleGroup(phoneGroup);
        phone2.setOnAction(new UpdatePlan());
        phone3.setToggleGroup(phoneGroup);
        phone3.setOnAction(new UpdatePlan());

        ToggleGroup planGroup = new ToggleGroup();
        plan1.setToggleGroup(planGroup);
        plan1.setSelected(true);
        plan1.setOnAction(new UpdatePlan());
        plan2.setToggleGroup(planGroup);
        plan2.setOnAction(new UpdatePlan());
        plan3.setToggleGroup(planGroup);
        plan3.setOnAction(new UpdatePlan());

        insuranceCheck.setOnAction(new UpdatePlan());
        wifiCheck.setOnAction(new UpdatePlan());

        VBox phoneBox = new VBox(15, phoneL, phone1, phone2, phone3);
        VBox planBox = new VBox(15, dataL, plan1, plan2, plan3);
        VBox extraBox = new VBox(15, extraL, insuranceCheck, wifiCheck);

        HBox selectionBox = new HBox(20, phoneBox, planBox, extraBox);

        VBox mainBox = new VBox(20, selectionBox, result);
        mainBox.setPadding(new Insets(25));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Cell Phone Plan");
        primaryStage.show();
    }

    private class UpdatePlan implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            double phonePrice;
            double planPrice;

            if(phone1.isSelected()) {
                phonePrice = 299.95;
            } else if(phone2.isSelected()) {
                phonePrice = 399.95;
            } else {
                phonePrice = 499.95;
            }

            if(plan1.isSelected()) {
                planPrice = 45.00;
            } else if(plan2.isSelected()) {
                planPrice = 65.00;
            } else {
                planPrice = 90.00;
            }

            if(insuranceCheck.isSelected()) {
                planPrice += 5.00;
            }
            if(wifiCheck.isSelected()) {
                planPrice += 10.00;
            }

            String resultString = String.format("Payment Plan: $%.2f and $%.2f per month", phonePrice, planPrice);
            result.setText(resultString);
        }
    }
}
