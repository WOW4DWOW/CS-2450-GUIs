/*
Kyle Hubbard
In-Class Exercise: BorderPane, ListViews, and Menus
CS 2450 - Programming Graphical User Interfaces
November 1, 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Rainfall extends Application {

    private BorderPane borderPane = new BorderPane();
    private Menu fileMenu;
    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;
    private AreaChart<String, Number> areaChart;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar();

        buildFileMenu(primaryStage);
        menuBar.getMenus().addAll(fileMenu);

        Label prompt = new Label("Enter Rainfall in Inches");
        Label janL = new Label("January");
        Label febL = new Label("February");
        Label marL = new Label("March");
        Label aprL = new Label("April");
        TextField janT = new TextField();
        TextField febT = new TextField();
        TextField marT = new TextField();
        TextField aprT = new TextField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {

            buildCharts(janT.getText(), febT.getText(), marT.getText(), aprT.getText());

            borderPane.setMargin(barChart, new Insets(20));
            borderPane.setCenter(barChart);
        });

        GridPane submitPane = new GridPane();
        submitPane.add(janL, 0, 0);
        submitPane.add(febL, 0, 1);
        submitPane.add(marL, 0, 2);
        submitPane.add(aprL, 0, 3);
        submitPane.add(janT, 1, 0);
        submitPane.add(febT, 1, 1);
        submitPane.add(marT, 1, 2);
        submitPane.add(aprT, 1, 3);
        submitPane.setVgap(10);
        submitPane.setHgap(10);

        VBox mainBox = new VBox(20, prompt, submitPane, submitButton);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));

        borderPane.setCenter(mainBox);
        borderPane.setTop(menuBar);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("Rainfall Charts");
        primaryStage.show();
    }

    //The graphs wouldn't work if I used the same axis, data sets for each graph. I don't know why.
    private void buildCharts(String janText, String febText, String marText, String aprText) {
        CategoryAxis hAxis = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis = new NumberAxis();
        vAxis.setLabel("Rainfall in Inches");

        CategoryAxis hAxis2 = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis2 = new NumberAxis();
        vAxis.setLabel("Rainfall in Inches");

        CategoryAxis hAxis3 = new CategoryAxis();
        hAxis.setLabel("Months");
        NumberAxis vAxis3 = new NumberAxis();
        vAxis.setLabel("Rainfall in Inches");

        barChart = new BarChart<>(hAxis, vAxis);
        barChart.setTitle("Monthly Rainfall");
        lineChart = new LineChart<>(hAxis2, vAxis2);
        lineChart.setTitle("Monthly Rainfall");
        areaChart = new AreaChart<>(hAxis3, vAxis3);
        areaChart.setTitle("Monthly Rainfall");

        XYChart.Series<String, Number> rainfallData1 = new XYChart.Series<>();
        rainfallData1.getData().add(new XYChart.Data<>("January", Integer.parseInt(janText)));
        rainfallData1.getData().add(new XYChart.Data<>("February", Integer.parseInt(febText)));
        rainfallData1.getData().add(new XYChart.Data<>("March", Integer.parseInt(marText)));
        rainfallData1.getData().add(new XYChart.Data<>("April", Integer.parseInt(aprText)));

        XYChart.Series<String, Number> rainfallData2 = new XYChart.Series<>();
        rainfallData2.getData().add(new XYChart.Data<>("January", Integer.parseInt(janText)));
        rainfallData2.getData().add(new XYChart.Data<>("February", Integer.parseInt(febText)));
        rainfallData2.getData().add(new XYChart.Data<>("March", Integer.parseInt(marText)));
        rainfallData2.getData().add(new XYChart.Data<>("April", Integer.parseInt(aprText)));

        XYChart.Series<String, Number> rainfallData3 = new XYChart.Series<>();
        rainfallData3.getData().add(new XYChart.Data<>("January", Integer.parseInt(janText)));
        rainfallData3.getData().add(new XYChart.Data<>("February", Integer.parseInt(febText)));
        rainfallData3.getData().add(new XYChart.Data<>("March", Integer.parseInt(marText)));
        rainfallData3.getData().add(new XYChart.Data<>("April", Integer.parseInt(aprText)));

        barChart.getData().add(rainfallData1);
        barChart.setLegendVisible(false);
        lineChart.getData().add(rainfallData2);
        lineChart.setLegendVisible(false);
        areaChart.getData().add(rainfallData3);
        areaChart.setLegendVisible(false);
    }

    private void buildFileMenu(Stage primaryStage) {

        fileMenu = new Menu("File");

        RadioMenuItem barChartItem = new RadioMenuItem("Bar Chart");
        RadioMenuItem lineChartItem = new RadioMenuItem("Line Chart");
        RadioMenuItem areaChartItem = new RadioMenuItem("Area Chart");
        ToggleGroup chartGroup = new ToggleGroup();

        barChartItem.setToggleGroup(chartGroup);
        lineChartItem.setToggleGroup(chartGroup);
        areaChartItem.setToggleGroup(chartGroup);

        barChartItem.selectedProperty().set(true);

        barChartItem.setOnAction(event -> {
            borderPane.setMargin(barChart, new Insets(20));
            borderPane.setCenter(barChart);
        });

        lineChartItem.setOnAction(event -> {
            borderPane.setMargin(lineChart, new Insets(20));
            borderPane.setCenter(lineChart);
        });

        areaChartItem.setOnAction(event -> {
            borderPane.setMargin(areaChart, new Insets(20));
            borderPane.setCenter(areaChart);
        });

        MenuItem exitItem = new MenuItem("Exit");

        exitItem.setOnAction(event -> {
            primaryStage.close();
        });

        fileMenu.getItems().addAll(barChartItem, lineChartItem, areaChartItem, new SeparatorMenuItem(), exitItem);
    }
}
