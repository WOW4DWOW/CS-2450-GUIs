/*
Aron Hubbard & Kyle Hubbard
Homework 4: Shape3D Viewer
CS 2450 - Programming Graphical User Interfaces
December 2018
*/

import javafx.application.*;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShapeViewer extends Application {

    private final double SCENE_WIDTH = 1000.0;
    private final double SCENE_HEIGHT = 750.0;
    private Shape3D selectedObject = null;
    private Label selectedObjectLabel;
    private ArrayList<String> shapeList = new ArrayList<>();
    private Group shapesGroup = new Group();
    private SubScene shapesSub;
    private TextArea sceneDataText;
    private BorderPane borderPane = new BorderPane();
    private MenuBar menuBar = new MenuBar();
    private Menu fileMenu;
    private File sceneFile;
    private HBox viewer;
    private VBox objectSettings;
    private GridPane viewerSettings;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        buildFileMenu(primaryStage);
        buildInterface(primaryStage);

        Scene scene = new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Shape3D Viewer");
        primaryStage.show();
    }

    //Builds the file menu and supporting logic
    private void buildFileMenu(Stage primaryStage) {

        fileMenu = new Menu("File");

        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem exitItem = new MenuItem("Exit");

        //Opens 3D Viewer File
        openItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Viewer Files (.txt)", "*.txt"));
            sceneFile = fileChooser.showOpenDialog(primaryStage);
            try {
                shapeList.clear();
                shapesGroup.getChildren().clear();
                Scanner sc = new Scanner(sceneFile);
                while(sc.hasNextLine()) {
                    shapeList.add(sc.nextLine());
                }
                updateSceneData();
                parseOpenFile();
                sc.close();
            }
            catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Opening File!");
                alert.show();
            }
        });

        //Saves 3D Viewer File
        saveItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Viewer Files (.txt)", "*.txt"));
            sceneFile = fileChooser.showSaveDialog(primaryStage);
            try {
                PrintWriter printWriter = new PrintWriter(sceneFile);
                printWriter.write(sceneDataText.getText());
                printWriter.close();
            }
            catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error Creating File!");
                alert.show();
            }
        });

        exitItem.setOnAction(event -> {
            primaryStage.close();
        });

        fileMenu.getItems().addAll(openItem, saveItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
    }

    //Handles loading 3D Scene files by recreating every object
    private void parseOpenFile() {

        for(int i = 0; i < shapeList.size(); i++) {
            String[] parts = shapeList.get(i).split(" ");

            if(parts[0].equalsIgnoreCase("background")) {
                setBackground(parts[1]);
            } else if(parts[0].equalsIgnoreCase("sphere")) {
                createSphere(true, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
            } else if(parts[0].equalsIgnoreCase("box")) {
                createBox(true, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10]);
            } else if(parts[0].equalsIgnoreCase("cylinder")) {
                createCylinder(true, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        }
    }

    //Builds the entire GUI for the program and all supporting logic
    private void buildInterface(Stage primaryStage) {

        //Creates 3D Viewport
        viewer = new HBox();
        viewer.setAlignment(Pos.CENTER);
        viewer.setPrefSize(750.0, 562.0);

        shapesSub = new SubScene(shapesGroup, 750, 562, true, SceneAntialiasing.BALANCED);
        shapesSub.setFill(Color.WHITE);
        shapeList.add("background white");
        PerspectiveCamera pCamera = new PerspectiveCamera(true);
        pCamera.setFieldOfView(60);
        pCamera.setNearClip(0.1);
        pCamera.setFarClip(2000.0);

        Translate cTransform = new Translate(0, 0, -200);
        Rotate xRotate = new Rotate(-45, Rotate.X_AXIS);
        pCamera.getTransforms().addAll(xRotate, cTransform);
        shapesSub.setCamera(pCamera);
        viewer.getChildren().add(shapesSub);

        //Creates Object Tools side panel
        Label tools = new Label("Object Tools");
        tools.setStyle("-fx-font-size: 24px");
        selectedObjectLabel = new Label("Selected Object: None");
        selectedObjectLabel.setPadding(new Insets(0, 0, 20, 0));

        //Translate slider and logic
        Label translate = new Label("Translate X: 0");
        translate.setPadding(new Insets(10, 0, 5, 0));
        Slider translateSlider = new Slider(-100, 100, 0);
        translateSlider.setMajorTickUnit(50);
        translateSlider.setMinorTickCount(5);
        translateSlider.setShowTickLabels(true);
        translateSlider.setShowTickMarks(true);
        translateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int translateValue = (int) translateSlider.getValue();
            translate.setText("Translate X: " + translateValue);

            selectedObject.setTranslateX(translateValue);

            if(shapeList.size() > 1) {
                int index = shapesGroup.getChildren().indexOf(selectedObject) + 1;
                String[] parts = shapeList.get(index).split(" ", 3);
                shapeList.set(index, parts[0] + " " + translateValue + " " + parts[2]);
                updateSceneData();
            }
        });

        //Rotate logic and slider
        Label rotate = new Label("Rotate Vertical: 0");
        rotate.setPadding(new Insets(10, 0, 5, 0));
        Slider rotateSlider = new Slider(-180, 180, 0);
        rotateSlider.setMajorTickUnit(90);
        rotateSlider.setMinorTickCount(3);
        rotateSlider.setShowTickLabels(true);
        rotateSlider.setShowTickMarks(true);
        rotateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int rotateValue = (int) rotateSlider.getValue();
            rotate.setText("Rotate Vertical: " + rotateValue);

            selectedObject.rotationAxisProperty().setValue(Rotate.X_AXIS);
            selectedObject.rotateProperty().setValue(rotateValue);

            if(shapeList.size() > 1) {
                int index = shapesGroup.getChildren().indexOf(selectedObject) + 1;
                String[] parts = shapeList.get(index).split(" ");
                parts[parts.length - 5] = String.valueOf(rotateValue);
                shapeList.set(index, String.join(" ", parts));
                updateSceneData();
            }
        });

        //Scale slider and logic
        Label scale = new Label("Scale: 1");
        scale.setPadding(new Insets(10, 0, 5, 0));
        Slider scaleSlider = new Slider(0, 2, 1);
        scaleSlider.setPadding(new Insets(0, 0, 20, 0));
        scaleSlider.setMajorTickUnit(1);
        scaleSlider.setMinorTickCount(5);
        scaleSlider.setShowTickLabels(true);
        scaleSlider.setShowTickMarks(true);
        scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double scaleValue = scaleSlider.getValue();
            scale.setText("Scale: " + String.format("%.2f", scaleValue));

            selectedObject.setScaleX(scaleValue);
            selectedObject.setScaleY(scaleValue);
            selectedObject.setScaleZ(scaleValue);

            if(shapeList.size() > 1) {
                int index = shapesGroup.getChildren().indexOf(selectedObject) + 1;
                String[] parts = shapeList.get(index).split(" ");
                parts[parts.length - 2] = String.format("%.2f", scaleValue);
                shapeList.set(index, String.join(" ", parts));
                updateSceneData();
            }
        });

        //Object color and logic
        Label objectColor = new Label("Object Color");
        objectColor.setPadding(new Insets(20, 0, 5, 0));
        ListView<String> objectColors = new ListView<>();
        objectColors.getItems().addAll("Black", "White", "Red", "Orange", "Yellow", "Green", "Blue", "Violet");
        objectColors.getSelectionModel().select(0);

        objectColors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setObjectColor(selectedObject, newValue);
            updateSceneData();
        });

        //Creates Object Settings section
        objectSettings = new VBox();
        objectSettings.getChildren().addAll(tools, selectedObjectLabel, new Separator(), translate, translateSlider, rotate, rotateSlider, scale, scaleSlider, new Separator(), objectColor, objectColors);
        objectSettings.setPrefSize(250.0, 562.0);
        objectSettings.setAlignment(Pos.TOP_CENTER);
        objectSettings.setPadding(new Insets(10));
        objectSettings.setStyle("-fx-background-color: #BBBBBB");

        Label controls = new Label("Controls");
        controls.setStyle("-fx-font-size: 24px");
        controls.setPadding(new Insets(0, 0, 5, 0));

        Button addShape = new Button("Add Shape");
        addShape.setOnAction(event -> {
            newShape(primaryStage);
        });

        Label backgroundColor = new Label("Background Color");
        backgroundColor.setPadding(new Insets(10, 0, 5, 0));
        ChoiceBox<String> backgroundColors = new ChoiceBox<>();
        backgroundColors.getItems().addAll("White", "Black", "Red", "Orange", "Yellow", "Green", "Blue", "Violet");
        backgroundColors.getSelectionModel().select(0);

        backgroundColors.valueProperty().addListener((observable, oldValue, newValue) -> {

            setBackground(newValue);
            shapeList.set(0, "background " + newValue.toLowerCase());
            updateSceneData();
        });

        VBox sceneOptions = new VBox(controls, addShape, backgroundColor, backgroundColors);
        sceneOptions.setAlignment(Pos.CENTER_LEFT);

        Label about = new Label("About");
        TextArea aboutField = new TextArea();
        aboutField.setEditable(false);
        aboutField.setPrefSize(614, 143.0);
        aboutField.setWrapText(true);
        aboutField.setText("This program is a Shape3D viewer and editor created by Aron and Kyle Hubbard for CS 2450.\n\nNew objects and the background color can be handled in the Controls tab.\nObject properties such as transforms, and color are controlled with the Object Tools tab.\n\nScene data is recorded to the right which will show all of the objects and settings of the current scene.");
        VBox aboutData = new VBox(about, aboutField);
        aboutData.setAlignment(Pos.CENTER);

        Label sceneDataLabel = new Label("Scene Data");
        sceneDataText = new TextArea();
        sceneDataText.setEditable(false);
        sceneDataText.setPrefSize(230.0, 143.0);
        VBox sceneData = new VBox(sceneDataLabel, sceneDataText);
        sceneData.setAlignment(Pos.CENTER_RIGHT);

        viewerSettings = new GridPane();
        viewerSettings.setPadding(new Insets(10));
        viewerSettings.setAlignment(Pos.CENTER);
        viewerSettings.setPrefSize(1000.0, 163.0);
        viewerSettings.setHgap(20);
        viewerSettings.setStyle("-fx-background-color: #999999");
        viewerSettings.add(sceneOptions, 0, 0);
        viewerSettings.add(aboutData, 1, 0);
        viewerSettings.add(sceneData, 2, 0);

        borderPane.setCenter(viewer);
        borderPane.setRight(objectSettings);
        borderPane.setBottom(viewerSettings);
        borderPane.setTop(menuBar);
        updateSceneData();
    }

    //Sets the background color of the 3D Viewport
    private void setBackground(String newValue) {
        if(newValue.equalsIgnoreCase("White")) {
            shapesSub.setFill(Color.WHITE);
        } else if (newValue.equalsIgnoreCase("Black")) {
            shapesSub.setFill(Color.BLACK);
        } else if (newValue.equalsIgnoreCase("Red")) {
            shapesSub.setFill(Color.DARKRED);
        } else if (newValue.equalsIgnoreCase("Orange")) {
            shapesSub.setFill(Color.DARKORANGE);
        } else if (newValue.equalsIgnoreCase("Yellow")) {
            shapesSub.setFill(Color.DARKGOLDENROD);
        } else if (newValue.equalsIgnoreCase("Green")) {
            shapesSub.setFill(Color.DARKGREEN);
        } else if (newValue.equalsIgnoreCase("Blue")) {
            shapesSub.setFill(Color.DARKBLUE);
        } else {
            shapesSub.setFill(Color.DARKVIOLET);
        }
    }

    //Sets the color of the 3D Object
    private void setObjectColor(Shape3D object, String newValue) {

        if(shapeList.size() > 1) {

            if(newValue.equalsIgnoreCase("White")) {
                object.setMaterial(new PhongMaterial(Color.WHITE));
            } else if (newValue.equalsIgnoreCase("Black")) {
                object.setMaterial(new PhongMaterial(Color.DARKGRAY));
            } else if (newValue.equalsIgnoreCase("Red")) {
                object.setMaterial(new PhongMaterial(Color.RED));
            } else if (newValue.equalsIgnoreCase("Orange")) {
                object.setMaterial(new PhongMaterial(Color.ORANGE));
            } else if (newValue.equalsIgnoreCase("Yellow")) {
                object.setMaterial(new PhongMaterial(Color.YELLOW));
            } else if (newValue.equalsIgnoreCase("Green")) {
                object.setMaterial(new PhongMaterial(Color.GREEN));
            } else if (newValue.equalsIgnoreCase("Blue")) {
                object.setMaterial(new PhongMaterial(Color.BLUE));
            } else {
                object.setMaterial(new PhongMaterial(Color.VIOLET));
            }

            int index = shapesGroup.getChildren().indexOf(selectedObject) + 1;
            String parts = shapeList.get(index).substring(0, shapeList.get(index).lastIndexOf(" "));
            shapeList.set(index, parts + " " + newValue.toLowerCase());
        }
    }

    //Dialog box for adding a new shape
    private void newShape(Stage primaryStage) {

        Stage shapeStage = new Stage();
        Label menuDesc = new Label("Shape Creator Dialog");
        menuDesc.setStyle("-fx-font-size: 24px");
        Label shape = new Label("Shape Type:");
        shape.setPadding(new Insets(0, 5, 0, 0));
        ChoiceBox<String> objectChoice = new ChoiceBox<>();
        objectChoice.getItems().addAll("Sphere", "Box", "Cylinder");
        objectChoice.getSelectionModel().select(0);
        HBox objectSelect = new HBox(shape, objectChoice);
        objectSelect.setAlignment(Pos.CENTER);
        VBox shapeCreator = new VBox(menuDesc, objectSelect, showSphereOptions(shapeStage));

        objectChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == "Sphere") {
                shapeCreator.getChildren().set(2, showSphereOptions(shapeStage));
            } else if (newValue == "Box") {
                shapeCreator.getChildren().set(2, showBoxOptions(shapeStage));
            } else {
                shapeCreator.getChildren().set(2, showCylinderOptions(shapeStage));
            }
        });

        shapeCreator.setPadding(new Insets(50));
        shapeCreator.setAlignment(Pos.CENTER);

        shapeStage.setScene(new Scene(shapeCreator));
        shapeStage.setResizable(false);
        shapeStage.setTitle("Create New Shape");
        shapeStage.initModality(Modality.WINDOW_MODAL);
        shapeStage.initOwner(primaryStage);
        shapeStage.show();
    }

    //Dialog box for creating a Sphere
    private VBox showSphereOptions(Stage shapeStage) {

        Label xCoord = new Label("X Position: ");
        Label yCoord = new Label("Y Position: ");
        Label radius = new Label("Radius: ");
        TextField xField = new TextField();
        TextField yField = new TextField();
        TextField radiusField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(xCoord, 0, 0);
        gridPane.add(yCoord, 0, 1);
        gridPane.add(radius, 0, 2);
        gridPane.add(xField, 1, 0);
        gridPane.add(yField, 1, 1);
        gridPane.add(radiusField, 1, 2);

        Button submit = new Button("Create Shape");
        submit.setOnAction(event -> {
            createSphere(false, xField.getText(), yField.getText(), radiusField.getText(), "0", "0", "0", "1", "black");
            shapeStage.close();
        });

        VBox finalBox = new VBox(10, gridPane, submit);
        finalBox.setAlignment(Pos.CENTER);
        finalBox.setPadding(new Insets(10, 0, 0, 0));
        return finalBox;
    }

    //Method to create a new sphere object with the desired properties
    private void createSphere(boolean openFile, String xCoord, String yCoord, String radius, String xRot, String yRot, String zRot, String scale, String color) {

        Sphere sphere = new Sphere(Double.parseDouble(radius));
        sphere.setMaterial(new PhongMaterial(Color.DARKGRAY));
        Translate sphereTransform = new Translate(Double.parseDouble(xCoord), Double.parseDouble(yCoord), 0);
        Rotate sphereRotate = new Rotate(Double.parseDouble(xRot), Rotate.X_AXIS);
        Scale sphereScale = new Scale(Double.parseDouble(scale), Double.parseDouble(scale), Double.parseDouble(scale));
        setObjectColor(sphere, color);
        sphere.getTransforms().addAll(sphereTransform, sphereRotate, sphereScale);

        shapesGroup.getChildren().add(sphere);

        sphere.setOnMouseClicked(event -> {
            selectedObject = sphere;
            selectedObjectLabel.setText("Selected Object: Sphere (" + shapesGroup.getChildren().indexOf(selectedObject) + ")");
        });


        if(!openFile) {
            shapeList.add(String.format("sphere %s %s %s %s %s %s %s %s", xCoord, yCoord, radius, xRot, yRot, zRot, scale, "black"));
            updateSceneData();
        }
    }

    //Dialog box for creating a Box
    private VBox showBoxOptions(Stage shapeStage) {

        Label xCoord = new Label("X Position: ");
        Label yCoord = new Label("Y Position: ");
        Label width = new Label("Width: ");
        Label height = new Label("Height: ");
        Label depth = new Label("Depth: ");
        TextField xField = new TextField();
        TextField yField = new TextField();
        TextField widthField = new TextField();
        TextField heightField = new TextField();
        TextField depthField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(xCoord, 0, 0);
        gridPane.add(yCoord, 0, 1);
        gridPane.add(width, 0, 2);
        gridPane.add(height, 0, 3);
        gridPane.add(depth, 0, 4);
        gridPane.add(xField, 1, 0);
        gridPane.add(yField, 1, 1);
        gridPane.add(widthField, 1, 2);
        gridPane.add(heightField, 1, 3);
        gridPane.add(depthField, 1, 4);

        Button submit = new Button("Create Shape");
        submit.setOnAction(event -> {
            createBox(false, xField.getText(), yField.getText(), widthField.getText(), heightField.getText(), depthField.getText(), "0", "0", "0", "1", "black");
            shapeStage.close();
        });

        VBox finalBox = new VBox(10, gridPane, submit);
        finalBox.setAlignment(Pos.CENTER);
        finalBox.setPadding(new Insets(10, 0, 0, 0));
        return finalBox;
    }

    //Method to create a new box object with the desired properties
    private void createBox(boolean openFile, String xCoord, String yCoord, String width, String height, String depth, String xRot, String yRot, String zRot, String scale, String color) {
        Box box = new Box(Double.parseDouble(width), Double.parseDouble(height), Double.parseDouble(depth));
        box.setMaterial(new PhongMaterial(Color.DARKGRAY));

        Translate boxTransform = new Translate(Double.parseDouble(xCoord), 0, 0);
        Rotate boxRotate = new Rotate(Double.parseDouble(xRot), Rotate.X_AXIS);
        Scale boxScale = new Scale(Double.parseDouble(scale), Double.parseDouble(scale), Double.parseDouble(scale));
        box.getTransforms().addAll(boxTransform, boxRotate, boxScale);
        setObjectColor(box, color);

        shapesGroup.getChildren().add(box);

        box.setOnMouseClicked(event -> {
            selectedObject = box;
            selectedObjectLabel.setText("Selected Object: Box (" + shapesGroup.getChildren().indexOf(selectedObject) + ")");
        });

        if(!openFile) {
            shapeList.add(String.format("box %s %s %s %s %s %s %s %s %s %s", xCoord, yCoord, width, height, depth, xRot, yRot, zRot, scale, "black"));
            updateSceneData();
        }
    }

    //Dialog box for creating a Cylinder
    private VBox showCylinderOptions(Stage shapeStage) {

        Label xCoord = new Label("X Position: ");
        Label yCoord = new Label("Y Position: ");
        Label radius = new Label("Radius: ");
        Label height = new Label("Height: ");
        TextField xField = new TextField();
        TextField yField = new TextField();
        TextField radiusField = new TextField();
        TextField heightField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(xCoord, 0, 0);
        gridPane.add(yCoord, 0, 1);
        gridPane.add(radius, 0, 2);
        gridPane.add(height, 0, 3);
        gridPane.add(xField, 1, 0);
        gridPane.add(yField, 1, 1);
        gridPane.add(radiusField, 1, 2);
        gridPane.add(heightField, 1, 3);

        Button submit = new Button("Create Shape");
        submit.setOnAction(event -> {
            createCylinder(false, xField.getText(), yField.getText(), radiusField.getText(), heightField.getText(), "0", "0", "0", "1", "black");
            shapeStage.close();
        });

        VBox finalBox = new VBox(10, gridPane, submit);
        finalBox.setAlignment(Pos.CENTER);
        finalBox.setPadding(new Insets(10, 0, 0, 0));
        return finalBox;
    }

    //Method to create a new cylinder object with the desired properties
    private void createCylinder(boolean openFile, String xCoord, String yCoord, String radius, String height, String xRot, String yRot, String zRot, String scale, String color) {
        Cylinder cylinder = new Cylinder(Double.parseDouble(radius), Double.parseDouble(height));
        cylinder.setMaterial(new PhongMaterial(Color.DARKGRAY));
        Translate cylTransform = new Translate(Double.parseDouble(xCoord), 0, 0);
        Rotate cylRotate = new Rotate(Double.parseDouble(xRot), Rotate.X_AXIS);
        Scale cylScale = new Scale(Double.parseDouble(scale), Double.parseDouble(scale), Double.parseDouble(scale));
        setObjectColor(cylinder, color);
        cylinder.getTransforms().addAll(cylTransform, cylRotate, cylScale);

        shapesGroup.getChildren().add(cylinder);

        cylinder.setOnMouseClicked(event -> {
            selectedObject = cylinder;
            selectedObjectLabel.setText("Selected Object: Cylinder (" + shapesGroup.getChildren().indexOf(selectedObject) + ")");
        });

        if(!openFile) {
            shapeList.add(String.format("cylinder %s %s %s %s %s %s %s %s %s", xCoord, yCoord, radius, height, xRot, yRot, zRot, scale, "black"));
            updateSceneData();
        }
    }

    //Updates the scene data whenever values are changed
    private void updateSceneData() {
        sceneDataText.clear();
        for(int i = 0; i < shapeList.size(); i++) {
            if(i != shapeList.size() - 1) {
                sceneDataText.appendText(shapeList.get(i) + "\n");
            } else {
                sceneDataText.appendText(shapeList.get(i));
            }
        }
    }
}
