/*
Kyle Hubbard
Homework 3: Dice Game
CS 2450 - Programming Graphical User Interfaces
October 30, 2018
*/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class DiceGame extends Application {

    Label overallScoreLabel = new Label();
    Label roundScoreLabel = new Label();
    Label result = new Label();
    int overallScore = 0;
    int roundScore = 0;
    int rollsRemaining = 3;

    Button button = new Button("Roll Dice");
    boolean newRound = false;

    ArrayList<Image> imageList = new ArrayList<>(12);
    ArrayList<Dice> diceList = new ArrayList<>(5);
    Dice dice1;
    Dice dice2;
    Dice dice3;
    Dice dice4;
    Dice dice5;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        overallScoreLabel.setId("overallScore");
        roundScoreLabel.setId("roundScore");
        result.setId("remainingRolls");

        updateText();
        buildDice();
        setHandlers();

        HBox diceBox = new HBox(10, dice1.image, dice2.image, dice3.image, dice4.image, dice5.image);
        diceBox.setAlignment(Pos.CENTER);
        diceBox.setId("diceBox");

        VBox mainBox = new VBox(15, overallScoreLabel, diceBox, button, roundScoreLabel, result);

        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setId("mainBox");

        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add("file:./res/dicegame_stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Game");
        primaryStage.show();
    }

    //Updates the text to be displayed in the program
    private void updateText() {

        if(newRound) {
            roundScore = 0;
            rollsRemaining = 3;
            button.setText("Roll Dice");
            result.setId("remainingRolls");
            newRound = false;
        }

        overallScoreLabel.setText("Overall Score: " + overallScore);
        roundScoreLabel.setText("Your Score: " + roundScore);

        if(rollsRemaining > 0) {
            result.setText("Rolls Remaining: " + rollsRemaining);
        }
    }

    //Loads the dice images and builds each dice object
    private void buildDice() {

        Image oneDice = new Image("file:./res/Dice1.png");
        Image oneDiceHeld = new Image("file:./res/Dice1Held.png");
        Image twoDice = new Image("file:./res/Dice2.png");
        Image twoDiceHeld = new Image("file:./res/Dice2Held.png");
        Image threeDice = new Image("file:./res/Dice3.png");
        Image threeDiceHeld = new Image("file:./res/Dice3held.png");
        Image fourDice = new Image("file:./res/Dice4.png");
        Image fourDiceHeld = new Image("file:./res/Dice4held.png");
        Image fiveDice = new Image("file:./res/Dice5.png");
        Image fiveDiceHeld = new Image("file:./res/Dice5held.png");
        Image sixDice = new Image("file:./res/Dice6.png");
        Image sixDiceHeld = new Image("file:./res/Dice6held.png");

        Collections.addAll(imageList, oneDice, twoDice, threeDice, fourDice, fiveDice, sixDice, oneDiceHeld, twoDiceHeld, threeDiceHeld, fourDiceHeld, fiveDiceHeld, sixDiceHeld);

        dice1 = new Dice();
        dice2 = new Dice();
        dice3 = new Dice();
        dice4 = new Dice();
        dice5 = new Dice();

        Collections.addAll(diceList, dice1, dice2, dice3, dice4, dice5);
    }

    //Applies all the required handlers to the dice classes and the button
    private void setHandlers() {

        dice1.image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(rollsRemaining < 3 && rollsRemaining > 0) {
                if(dice1.isHeld) {
                    dice1.setNotHeld();
                } else {
                    dice1.setHeld();
                }
            }
        });

        dice2.image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(rollsRemaining < 3 && rollsRemaining > 0) {
                if(dice2.isHeld) {
                    dice2.setNotHeld();
                } else {
                    dice2.setHeld();
                }
            }
        });

        dice3.image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(rollsRemaining < 3 && rollsRemaining > 0) {
                if(dice3.isHeld) {
                    dice3.setNotHeld();
                } else {
                    dice3.setHeld();
                }
            }
        });

        dice4.image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(rollsRemaining < 3 && rollsRemaining > 0) {
                if(dice4.isHeld) {
                    dice4.setNotHeld();
                } else {
                    dice4.setHeld();
                }
            }
        });

        dice5.image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(rollsRemaining < 3 && rollsRemaining > 0) {
                if(dice5.isHeld) {
                    dice5.setNotHeld();
                } else {
                    dice5.setHeld();
                }
            }
        });

        button.setOnAction(new updateScores());
    }

    //Button handler class to drive the program
    class updateScores implements EventHandler<ActionEvent> {

        //Handle method to perform actions based on how many rolls are remaining
        @Override
        public void handle(ActionEvent event) {

            rollsRemaining--;

            if (rollsRemaining >= 0) {
                for (Dice dice : diceList) {
                    if (!dice.isHeld) {
                        dice.randomRoll();
                    }
                }
                updateText();
                if (rollsRemaining == 0) {
                    determineScore();
                    button.setText("Play Again");
                    updateText();
                }
            } else if (rollsRemaining < 0) {
                newRound = true;
                for (Dice dice : diceList) {
                    if (dice.isHeld) {
                        dice.setNotHeld();
                    }
                    dice.setRoll(0);
                }
                updateText();
            }
        }

        //Handles figuring out the hand and displaying the correct outputs
        public void determineScore() {

            //Sort diceList in ascending order
            Collections.sort(diceList, new Comparator<Dice>() {
                @Override
                public int compare(Dice d1, Dice d2) {
                    return d1.roll > d2.roll ? 1 : (d1.roll < d2.roll) ? -1 : 0;
                }
            });

            result.setId("rolledHand");

            if(fiveOfAKind()) {
                result.setText("Five of a Kind!");
                overallScore += 10;
                roundScore = 10;
            }
            else if(straight()) {
                result.setText("Straight!");
                overallScore += 8;
                roundScore = 8;
            }
            else if(fourOfAKind()) {
                result.setText("Four of a Kind!");
                overallScore += 7;
                roundScore = 7;
            }
            else if(fullHouse()) {
                result.setText("Full House!");
                overallScore += 6;
                roundScore = 6;
            }
            else if(threeOfAKind()) {
                result.setText("Three of a Kind!");
                overallScore += 5;
                roundScore = 5;
            }
            else if(twoPair()) {
                result.setText("Two Pair!");
                overallScore += 4;
                roundScore = 4;
            }
            else if(twoOfAKind()) {
                result.setText("Two of a Kind!");
                overallScore += 1;
                roundScore = 1;
            }
            else {
                result.setText("You didn't roll anything!");
                overallScore += 0;
                roundScore = 0;
            }
        }

        //Checks for a Five of a Kind hand
        private boolean fiveOfAKind() {

            int matching = 0;
            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    matching++;
                }
            }

            if(matching == 4) {
                return true;
            } else {
                return false;
            }
        }

        //Checks for a Straight hand
        private boolean straight() {

            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll != (diceList.get(i).roll - 1)) {
                    return false;
                }
            }
            return true;
        }

        //Checks for a Four of a Kind hand
        private boolean fourOfAKind() {

            boolean firstFailed = false;
            //Check first 4 spots
            for(int i = 1; i < 4; i++) {
                if(diceList.get(i - 1).roll != diceList.get(i).roll) {
                    firstFailed = true;
                }
            }
            //Check last 4 spots
            if(firstFailed) {
                for(int i = 2; i < 5; i++) {
                    if(diceList.get(i - 1).roll != diceList.get(i).roll) {
                        return false;
                    }
                }
            }
            return true;
        }

        //Checks for a Full House hand
        private boolean fullHouse() {

            int matching = 0;
            int matchedValue = 0;
            int matching2 = 0;
            boolean foundFirstMatch = true;
            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    if(foundFirstMatch) {
                        matchedValue = diceList.get(i).roll;
                        break;
                    }
                }
            }

            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    if(diceList.get(i).roll == matchedValue) {
                        matching++;
                    }
                    if(!(diceList.get(i).roll == matchedValue)) {
                        matching2++;
                    }
                }
            }

            if((matching == 2 && matching2 == 1) || (matching == 1 && matching2 == 2)) {
                return true;
            } else {
                return false;
            }
        }

        //Checks for a Three of a Kind hand
        private boolean threeOfAKind() {

            boolean firstFailed = false;
            boolean secondFailed = false;
            //Check first 3 spots
            for(int i = 1; i < 3; i++) {
                if(diceList.get(i - 1).roll != diceList.get(i).roll) {
                    firstFailed = true;
                }
            }
            //Check last 3 spots
            if(firstFailed) {
                for(int i = 3; i < 5; i++) {
                    if(diceList.get(i - 1).roll != diceList.get(i).roll) {
                        secondFailed = true;
                    }
                }
            }

            //Check middle three
            if(secondFailed) {
                for(int i = 2; i < 4; i++) {
                    if(diceList.get(i - 1).roll != diceList.get(i).roll) {
                        return false;
                    }
                }
            }
            return true;
        }

        //Checks for a Two Pair hand
        private boolean twoPair() {

            int matching = 0;
            int matchedValue = 0;
            int matching2 = 0;
            boolean foundFirstMatch = true;
            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    if(foundFirstMatch) {
                        matchedValue = diceList.get(i).roll;
                        break;
                    }
                }
            }

            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    if(diceList.get(i).roll == matchedValue) {
                        matching++;
                    }
                    if(!(diceList.get(i).roll == matchedValue)) {
                        matching2++;
                    }
                }
            }

            if(matching == 1 && matching2 == 1) {
                return true;
            } else {
                return false;
            }
        }

        //Checks for a Two of a Kind hand
        private boolean twoOfAKind() {

            int matching = 0;
            for(int i = 1; i < 5; i++) {
                if(diceList.get(i - 1).roll == (diceList.get(i).roll)) {
                    matching++;
                }
            }

            if(matching == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    //Dice Class
    class Dice {

        ImageView image;
        int roll;
        boolean isHeld;

        public Dice() {
            this.roll = 0;
            isHeld = false;
            image = new ImageView(imageList.get(roll));
            image.setPreserveRatio(true);
            image.setFitHeight(100);
        }

        //Generates a random dice roll
        public void randomRoll() {
            Random random = new Random();
            roll = random.nextInt(6);
            image.setImage(imageList.get(roll));
        }

        public void setRoll(int roll) {
            this.roll = roll;
            image.setImage(imageList.get(roll));
        }

        //Two methods to handle setting the die as held or not held
        public void setHeld() {
            isHeld = true;
            image.setImage(imageList.get((roll) + 6));
        }

        public void setNotHeld() {
            isHeld = false;
            image.setImage(imageList.get(roll));
        }
    }
}