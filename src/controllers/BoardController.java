package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Card;
import models.Computer;
import models.Deck;
import models.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    public static Pane cards;
    public static Deck deck = new Deck(); // Create a new deck.
    public static Player player = new Player(); // Creat a player.
    public static Computer computer = new Computer(); // Create a computer.
    public static Card[] cardsTable = new Card[4];
    static boolean firstTime = true; // La premi�re distribution des cards.
    Integer cardsDeck = 52;
    Integer lowerBound = 0;
    Integer upperBound = 4;
    List<Card> cardsDealt = new ArrayList<>(12); // Cards dealt.
    List<Card> remainingCards = new ArrayList<>(44); // Cards remaining.
    List<Card> cardsPoubelle = new ArrayList<>(44); // Cards sent to the recycle.

    // Effet sonore lors de la distribution des cards.
    Card[] cardsDis = new Card[12]; // Cards dealt in the first round.

    // Go back to the menu.
    Card[] cardsRes = new Card[40]; // Cards dealt in the following rounds.

    // Restart the game.
    @FXML
    private Label cardsCount;
    @FXML
    private Label playerScore;
    @FXML
    private Label computerScore;

    public static void effet(String url) {
        //AudioClip effet = new AudioClip(new File(url).toURI().toString());
        //effet.play();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            deck.createDeck(); // Create a new deck.
            deck.shuffle(); // Shuffle the deck.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void menu(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Menu");
        alert.setHeaderText("Do you want to go back to the menu?");
        Stage icon = (Stage) alert.getDialogPane().getScene().getWindow();
        icon.getIcons().add(new Image(new File("resources/icon.png").toURI().toString()));
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        alert.getButtonTypes().setAll(noButton, yesButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            // If the user choose "Yes".
            clearBoard(); // Empty the game table.
            Parent board = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(board, 950, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            //effet("resources/sounds/click.wav");
            stage.show();
        } else {
            // If the user choose "No".
            alert.close();
        }
    }

    public void reset(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Reset");
        alert.setHeaderText("Do you want to start over?\nYour progress will be lost!");
        Stage icon = (Stage) alert.getDialogPane().getScene().getWindow();
        icon.getIcons().add(new Image(new File("resources/icon.png").toURI().toString()));
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        alert.getButtonTypes().setAll(noButton, yesButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            clearBoard(); // Vider la table de jeu.
            MenuController.board = FXMLLoader.load(getClass().getResource("Board.fxml"));
            Scene scene = new Scene(MenuController.board, 950, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            //effet("resources/sounds/click.wav");
            stage.show();
        } else {
            // If the user choose "No".
            alert.close();
        }
    }

    // Distribuer les cards.

    @SuppressWarnings("static-access")
    public void deal() {
        String carteId;
        double x = -731;
        cards = (Pane) (MenuController.board.getChildren().get(2));
        cardsCount = (Label) MenuController.board.lookup("#cardsCount");
        if (firstTime == true) {
            deck.shuffle();

            // .
            for (int i = 0; i < 12; i++) {
                cardsDis[i] = deck.dealOneCard();
                cardsDealt.add(cardsDis[i]);
            }

            // .
            for (int i = 0; i < 40; i++) {
                cardsRes[i] = deck.dealOneCard();
                remainingCards.add(cardsRes[i]);
            }

            // Deal 4 cards to the computer.
            for (int j = 0; j < 4; j++) {
                computer.getComputerCards()[j] = cardsDis[j];
                if (cardsDis[j].getSuit().equals(1))
                    carteId = "spades";
                else if (cardsDis[j].getSuit().equals(2))
                    carteId = "diamonds";
                else if (cardsDis[j].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";

                // .
                Node node1 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue());
                Node node2 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue() + "-" + "back");
                node1.setVisible(false);
                node2.setVisible(true);

                // .
                TranslateTransition translate1 = new TranslateTransition(Duration.millis(1000));
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(1000));
                translate1.setNode(node1);
                translate1.setToX(x);
                translate1.setToY(-139);
                translate1.play();
                translate2.setNode(node2);
                translate2.setToX(x);
                translate2.setToY(-139);
                translate2.play();
                //effet("resources/sounds/deal.wav");

                // .
                cardsDeck--; // Update number of computer in the deck.
                cardsCount.setText(Integer.toString(cardsDeck));
                x = x + 133;
            }

            // Distribuer 4 cards au player.
            x = -731;
            // TimeUnit.SECONDS.sleep((long) 0.5);
            // Thread.sleep(1000);
            for (int j = 4; j < 8; j++) {
                player.getPlayerCards()[j - 4] = cardsDis[j];
                if (cardsDis[j].getSuit().equals(1))
                    carteId = "spades";
                else if (cardsDis[j].getSuit().equals(2))
                    carteId = "diamonds";
                else if (cardsDis[j].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";

                // .
                Node node1 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue());
                Node node2 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue() + "-" + "back");
                node1.setVisible(false);
                node2.setVisible(true);

                // .
                TranslateTransition translate1 = new TranslateTransition(Duration.millis(1000));
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(1000));
                translate1.setNode(node1);
                translate2.setNode(node2);
                translate1.setToX(x);
                translate2.setToX(x);
                translate1.setToY(233);
                translate2.setToY(233);
                translate1.play();
                translate2.play();
                //effet("resources/sounds/deal.wav");

                // .
                translate1.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        node1.setVisible(true);
                    }
                });

                // .
                translate2.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        node2.setVisible(false);
                    }
                });

                // Si le joueur clique sur une carte.
                Card carteChoisie = cardsDis[j];
                node1.setOnMouseClicked(event -> {
                    player.selectionnerUneCarte(node1, carteChoisie);
                });

                // .
                cardsDeck--; // Mise-�-jour du nombre de cartes dans le paquet.
                cardsCount.setText(Integer.toString(cardsDeck));
                x = x + 133;
            }

            // Distribuer 4 cards au milieu du Table.
            x = -650;
            for (int j = 8; j < 12; j++) {
                cardsTable[j - 8] = cardsDis[j];
                if (cardsDis[j].getSuit().equals(1))
                    carteId = "spades";
                else if (cardsDis[j].getSuit().equals(2))
                    carteId = "diamonds";
                else if (cardsDis[j].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";

                // .
                Node node1 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue());
                Node node2 = cards.lookup("#" + carteId + "-" + cardsDis[j].getValue() + "-" + "back");

                // .
                TranslateTransition translate1 = new TranslateTransition(Duration.millis(1000));
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(1000));
                translate1.setNode(node1);
                translate2.setNode(node2);
                translate1.setToX(x);
                translate2.setToX(x);
                translate1.setToY(45);
                translate2.setToY(45);
                translate1.play();
                translate2.play();
                //effet("resources/sounds/deal.wav");

                translate1.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        computer.verifyCards();
                        computer.setTargetedValue();
                        computer.analyserTable();
                    }
                });

                // Si le player clique sur une card.
                Card chosenCard = cardsDis[j];

                // .
                node1.setOnMouseClicked(event -> {
                    player.chosenCard2 = chosenCard;
                    player.swapCards(Player.chosenCard1, Player.chosenCard2);
                });

                // .
                cardsDeck--; // Update the number of computer in the deck.
                cardsCount.setText(Integer.toString(cardsDeck));
                x = x + 82;
            }

            // .
            firstTime = false;
            computer.verifyCards(); // V�rifier les cards de l'computer.
            player.verifyCards(); // V�rifier les cards du player.
        }

        // After the first round.
        else {
            //deck.shuffle(); // Shuffle the deck.
            for (int i = 0; i < 4; i++) {
                if (cardsTable[i].getSuit().equals(1))
                    carteId = "spades";
                else if (cardsTable[i].getSuit().equals(2))
                    carteId = "diamonds";
                else if (cardsTable[i].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";

                Node node1 = cards.lookup("#" + carteId + "-" + cardsDis[i].getValue());
                Node node2 = cards.lookup("#" + carteId + "-" + cardsDis[i].getValue() + "-" + "back");
                Card card = cardsTable[i];

                // .
                node1.setOnMouseClicked(event -> {
                    player.chosenCard2 = card;
                    player.swapCards(Player.chosenCard1, Player.chosenCard2);
                });

                // .
                TranslateTransition translate1 = new TranslateTransition(Duration.millis(1000));
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(1000));
                translate1.setNode(node1);
                translate2.setNode(node2);
                translate1.setToX(-875);
                translate2.setToX(-875);
                translate1.play();
                translate2.play();
                cardsPoubelle.add(card);

                translate1.setOnFinished(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if (cardsDeck == 0) {
                            String carteId;
                            cards.lookup("#deck").setVisible(true);
                            for (int i = 0; i < 44; i++) {
                                Card card = cardsPoubelle.get(i);
                                cardsRes[i] = card;
                                cardsPoubelle.remove(card);
                                if (cardsRes[i].getSuit().equals(1))
                                    carteId = "spades";
                                else if (cardsRes[i].getSuit().equals(2))
                                    carteId = "diamonds";
                                else if (cardsRes[i].getSuit().equals(3))
                                    carteId = "hearts";
                                else
                                    carteId = "clubs";
                                Node node = cards.lookup("#" + carteId + "-" + cardsRes[i].getValue());
                                TranslateTransition translate = new TranslateTransition(Duration.millis(10));
                                translate.setNode(node);
                                translate.setToX(0);
                                translate.setToY(0);
                                translate.play();
                            }

                        }
                    }

                });
            }

            // Si le talon est vide.
            if (cardsDeck == 0) {
                // .
                cardsDeck = 44;
                lowerBound = 0;
                upperBound = 4;
            }

            // Distribuer 4 cards au milieu.
            for (int j = lowerBound; j < upperBound; j++) {
                cardsTable[j - lowerBound] = cardsRes[j];
                if (cardsRes[j].getSuit().equals(1))
                    carteId = "spades";
                else if (cardsRes[j].getSuit().equals(2))
                    carteId = "diamonds";
                else if (cardsRes[j].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";

                // .
                Node node1 = cards.lookup("#" + carteId + "-" + cardsRes[j].getValue()); //cardsDis
                Node node2 = cards.lookup("#" + carteId + "-" + cardsRes[j].getValue() + "-" + "back");
                Card card = cardsRes[j];

                // .
                node1.setOnMouseClicked(event -> {
                    player.chosenCard2 = card;
                    player.swapCards(player.chosenCard1, player.chosenCard2);
                });

                // .
                TranslateTransition translate1 = new TranslateTransition(Duration.millis(1000));
                TranslateTransition translate2 = new TranslateTransition(Duration.millis(1000));
                translate1.setNode(node1);
                translate2.setNode(node2);
                translate1.setToX(x + 80);
                translate2.setToX(x + 80);
                translate1.setToY(45);
                translate2.setToY(45);
                translate1.play();
                translate2.play();
                //effet("resources/sounds/deal.wav");

                // .
                translate1.setOnFinished(event -> {
                    computer.verifyCards();
                    computer.setTargetedValue();
                    computer.analyserTable();
                });

                // .
                cardsDeck--; // Update the number of cards in the deck.
                if (cardsDeck == 0)
                    cards.lookup("#deck").setVisible(false);
                cardsCount.setText(Integer.toString(cardsDeck));
                x = x + 82;
            }

            // Update the bounds.
            lowerBound += 4;
            upperBound += 4;
        }

    }

    // Vider la table de jeu.

    public void clearBoard() {
        if (!firstTime) {
            firstTime = true;
            cardsDealt.clear();
            remainingCards.clear();
            cardsPoubelle.clear();
            cardsTable = new Card[4];
            cardsDis = new Card[12];
            cardsRes = new Card[44];
            computer.setComputerCards(new Card[4]);
            player.setPlayerCards(new Card[4]);
        }

    }

    // Restart the game.

    public void ok(ActionEvent event) throws IOException {
        MenuController.board.lookup("#computerWin").setVisible(false);
        MenuController.board.lookup("#playerWin").setVisible(false);
        clearBoard(); // Vider la table de jeu.
        MenuController.board = FXMLLoader.load(getClass().getResource("Board.fxml"));
        Scene scene = new Scene(MenuController.board, 950, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //effet("resources/sounds/click.wav");
        stage.show();
    }

}