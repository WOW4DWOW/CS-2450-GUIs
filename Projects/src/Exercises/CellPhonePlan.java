/*
Aron Hubbard & Kyle Hubbard
In-Class Exercise: CSS with Event Handlers
CS 2450 - Programming Graphical User Interfaces
October 4, 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CellPhonePlan extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label phoneL = new Label("Phones (can only select 1)");
        Label dataL = new Label("Data Plans (can only select 1)");
        Label extraL = new Label("Extras (can select up to 2)");
        Label result = new Label("Choose your Cell Phone Model, Data Plan, and Extras");

        ToggleGroup phoneGroup = new ToggleGroup();
        RadioButton phone1 = new RadioButton("Model 100: $299.95");
        phone1.setToggleGroup(phoneGroup);
        RadioButton phone2 = new RadioButton("Model 100: $299.95");
        phone2.setToggleGroup(phoneGroup);
        RadioButton phone3 = new RadioButton("Model 100: $299.95");
        phone3.setToggleGroup(phoneGroup);

        ToggleGroup planGroup = new ToggleGroup();
        RadioButton plan1 = new RadioButton("8 GB per month: $45.00 per month");
        plan1.setToggleGroup(planGroup);
        RadioButton plan2 = new RadioButton("16 GB per month: $65.00 per month");
        plan2.setToggleGroup(planGroup);
        RadioButton plan3 = new RadioButton("20 GB per month: $90.00 per month");
        plan3.setToggleGroup(planGroup);

        CheckBox insuranceCheck = new CheckBox("Insurance: $5.00 per month");
        CheckBox wifiBox = new CheckBox("Wifi Hotspot Capability: $10.00 per month");

        VBox phoneBox = new VBox(15, phoneL, phone1, phone2, phone3);
        VBox planBox = new VBox(15, dataL, plan1, plan2, plan3);
        VBox extraBox = new VBox(15, extraL, insuranceCheck, wifiBox);

        HBox selectionBox = new HBox(20, phoneBox, planBox, extraBox);

        VBox mainBox = new VBox(20, selectionBox, result);
        mainBox.setPadding(new Insets(25));
        mainBox.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Cell Phone Plan");
        primaryStage.show();
    }
}
