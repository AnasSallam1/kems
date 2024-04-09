package models;

import controllers.BoardController;
import controllers.MenuController;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

public class Computer {

    public static Integer targetedValue = 0; // La value que l'ordinateur doit cibler.
    public static Card cardChosen1 = null;
    public static Card cardChosen2 = null;

    // Constructeur.
    private static Card[] computerCards = new Card[4]; // Cards of the computer.

    // Getters & Setters.
    private static boolean winner = false; // Si l'ordinateur wins une partie.
    private static Integer foisGagne = 0; // Number of rounds won by the computer.

    public Computer() {

    }

    public static boolean isGagnant() {
        return winner;
    }

    public void setGagnant(boolean w) {
        Computer.winner = w;
    }

    public static Integer getFoisGagne() {
        return foisGagne;
    }

    // .

    public static void setFoisGagne(Integer n) {
        Computer.foisGagne = n;
    }

    public static void verifyCards() {
        if (computerCards[0].getValue() == computerCards[1].getValue()
                && computerCards[0].getValue() == computerCards[2].getValue()
                && computerCards[0].getValue() == computerCards[3].getValue()) {
            BoardController.computer.setGagnant(true);
            String carteId;
            for (int i = 0; i < 4; i++) {
                if (computerCards[i].getSuit().equals(1))
                    carteId = "spades";
                else if (computerCards[i].getSuit().equals(2))
                    carteId = "diamonds";
                else if (computerCards[i].getSuit().equals(3))
                    carteId = "hearts";
                else
                    carteId = "clubs";
                BoardController.cards.lookup("#" + carteId + "-" + computerCards[i].getValue()).setVisible(true);
                BoardController.cards.lookup("#" + carteId + "-" + computerCards[i].getValue() + "-" + "back").setVisible(false);
            }
            ordinateurGagne();
        }
    }

    public static void setTargetedValue() {
        Integer occurrence = 0;
        Card carteReference;
        for (int i = 0; i < 4; i++) {
            carteReference = computerCards[i];
            for (int j = 0; j < 4; j++) {
                if (carteReference.getValue() == computerCards[j].getValue())
                    occurrence++;
            }
            if (occurrence >= 2) {
                targetedValue = carteReference.getValue();
                break;
            } else
                occurrence = 0;
        }
        // S'il n'y a aucune occurrence.
        if (targetedValue == 0) {
            targetedValue = computerCards[new Random().nextInt(computerCards.length)].getValue();
        }
    }

    // V�rifier si les quatre cartes sont identiques.

    public static void analyserTable() {
        for (int i = 0; i < 4; i++) {
            if (BoardController.cardsTable[i].getValue() == targetedValue) {
                cardChosen2 = BoardController.cardsTable[i];
                for (int j = 0; j < 4; j++) {
                    if (computerCards[j].getValue() != cardChosen2.getValue()) {
                        cardChosen1 = computerCards[j];
                        break;
                    }
                }
            }
        }
        if (cardChosen2 != null)
            swapCards(cardChosen1, cardChosen2);
        verifyCards();
    }

    // Choose a targeted card.

    public static void swapCards(Card card1, Card card2) {
        String carte1Id, carte2Id;
        double x1, x2, y1, y2;
        // .
        if (card1 != null) {
            // .
            if (card1.getSuit().equals(1))
                carte1Id = "spades";
            else if (card1.getSuit().equals(2))
                carte1Id = "diamonds";
            else if (card1.getSuit().equals(3))
                carte1Id = "hearts";
            else
                carte1Id = "clubs";

            // .
            if (card2.getSuit().equals(1))
                carte2Id = "spades";
            else if (card2.getSuit().equals(2))
                carte2Id = "diamonds";
            else if (card2.getSuit().equals(3))
                carte2Id = "hearts";
            else
                carte2Id = "clubs";

            // .
            Node carte1Node1 = BoardController.cards.lookup("#" + carte1Id + "-" + card1.getValue());
            Node carte1Node2 = BoardController.cards.lookup("#" + carte1Id + "-" + card1.getValue() + "-" + "back");
            Node carte2Node1 = BoardController.cards.lookup("#" + carte2Id + "-" + card2.getValue());
            Node carte2Node2 = BoardController.cards.lookup("#" + carte2Id + "-" + card2.getValue() + "-" + "back");
            x1 = carte1Node1.getTranslateX();
            y1 = carte1Node1.getTranslateY();
            x2 = carte2Node1.getTranslateX();
            y2 = carte2Node1.getTranslateY();

            // .
            carte1Node1.setOnMouseClicked(event -> {
                Player.chosenCard2 = card1;
                Player.swapCards(Player.chosenCard1, Player.chosenCard2);
            });

            // .
            TranslateTransition carte1Translate1 = new TranslateTransition(Duration.millis(1000));
            TranslateTransition carte1Translate2 = new TranslateTransition(Duration.millis(1000));
            carte1Translate1.setNode(carte1Node1);
            carte1Translate2.setNode(carte1Node2);
            carte1Translate1.setToX(x2);
            carte1Translate2.setToX(x2);
            carte1Translate1.setToY(y2);
            carte1Translate2.setToY(y2);
            carte1Translate1.play();
            carte1Translate2.play();
            carte1Node1.setVisible(true);
            carte1Node2.setVisible(false);
            //BoardController.effet("resources/sounds/deal.wav");

            // .
            TranslateTransition carte2Translate1 = new TranslateTransition(Duration.millis(1000));
            TranslateTransition carte2Translate2 = new TranslateTransition(Duration.millis(1000));
            carte2Translate1.setNode(carte2Node1);
            carte2Translate2.setNode(carte2Node2);
            carte2Translate1.setToX(x1);
            carte2Translate2.setToX(x1);
            carte2Translate1.setToY(y1);
            carte2Translate2.setToY(y1);
            carte2Translate1.play();
            carte2Translate2.play();
            carte2Node1.setVisible(false);
            carte2Node2.setVisible(true);
            //BoardController.effet("resources/sounds/deal.wav");

            // .
            for (int i = 0; i < 4; i++) {
                if (BoardController.cardsTable[i] == card2) {
                    Card temp = card1;
                    for (int j = 0; j < 4; j++) {
                        if (computerCards[j] == card1) {
                            computerCards[j] = card2;
                        }
                    }
                    BoardController.cardsTable[i] = temp;
                }
            }

            // .
            carte2Translate2.setOnFinished(event -> {
                cardChosen1 = null;
                cardChosen2 = null;
                analyserTable();
            });

        }

    }

    // Bluffing.
    public static void bluff() {

    }

    // Chercher une carte dans la table de la m�me value que la carte cibl�e.

    public static void ordinateurGagne() {
        if (isGagnant() == true) {
            MenuController.board.lookup("#computerWin").setVisible(true);
            //BoardController.effet("resources/sounds/crowd-aww.wav");
            targetedValue = 0;
            foisGagne++;
        }
    }

    // Changer une carte avec une autre de la table.

    public Card[] getComputerCards() {
        return computerCards;
    }

    // D�clarer l'ordinateur comme le gagnant.

    public void setComputerCards(Card[] computerCards) {
        Computer.computerCards = computerCards;
    }

}
