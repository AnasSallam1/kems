package models;

import controllers.BoardController;
import controllers.MenuController;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Player {

    public static Card chosenCard1 = null;
    public static Card chosenCard2 = null;
    public static boolean carteSelect = false; // Selected card.
    public static Node nodeSelect = null;

    // Constructeur.
    private static Card[] playerCards = new Card[4]; // Les cartes du player.

    // Getters & Setters.
    private static boolean winner = false; // Si le player a gagn� une partie.
    private static Integer foisGagne = 0; // Nombre de parties gagn�es par le player.
    private String name;

    public Player() {

    }

    public static boolean isGagnant() {
        return winner;
    }

    public void setGagnant(boolean w) {
        Player.winner = w;
    }

    public static Integer getFoisGagne() {
        return foisGagne;
    }

    public static void setFoisGagne(Integer n) {
        Player.foisGagne = n;
    }

    // .

    public static void verifyCards() {
        if (playerCards[0].getValue() == playerCards[1].getValue()
                && playerCards[0].getValue() == playerCards[2].getValue()
                && playerCards[0].getValue() == playerCards[3].getValue()) {
            BoardController.player.setGagnant(true);
            playerGagne();
        }
    }

    public static void selectionnerUneCarte(Node card, Card chosenCard) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(200));
        translate.setNode(card);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.valueOf("#2874A6"));
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setSpread(4);
        dropShadow.setHeight(8);
        dropShadow.setWidth(8);
        if (!carteSelect) {
            translate.setByX(-10);
            translate.setByY(-10);
            translate.play();
            nodeSelect = card;
            nodeSelect.setEffect(dropShadow);
            chosenCard1 = chosenCard;
            carteSelect = true;
        } else if (nodeSelect != card) {
            translate.setNode(nodeSelect);
            translate.setByX(10);
            translate.setByY(10);
            translate.play();
            nodeSelect.setEffect(null);
            TranslateTransition translateBack = new TranslateTransition(Duration.millis(200));
            translateBack.setNode(card);
            translateBack.setByX(-10);
            translateBack.setByY(-10);
            translateBack.play();
            nodeSelect = card;
            nodeSelect.setEffect(dropShadow);
            chosenCard1 = chosenCard;
        } else {
            translate.setByX(10);
            translate.setByY(10);
            translate.play();
            nodeSelect.setEffect(null);
            nodeSelect = null;
            chosenCard1 = null;
            carteSelect = false;
        }
    }

    public static void swapCards(Card card1, Card card2) {
        String card1Suit, card2Suit;
        double x1, x2, y1, y2;
        // .
        if (card1 != null) {
            if (card1.getSuit().equals(1))
                card1Suit = "spades";
            else if (card1.getSuit().equals(2))
                card1Suit = "diamonds";
            else if (card1.getSuit().equals(3))
                card1Suit = "hearts";
            else
                card1Suit = "clubs";

            // .
            if (card2.getSuit().equals(1))
                card2Suit = "spades";
            else if (card2.getSuit().equals(2))
                card2Suit = "diamonds";
            else if (card2.getSuit().equals(3))
                card2Suit = "hearts";
            else
                card2Suit = "clubs";

            // .
            Node card1Front = BoardController.cards.lookup("#" + card1Suit + "-" + card1.getValue());
            Node card1Back = BoardController.cards.lookup("#" + card1Suit + "-" + card1.getValue() + "-" + "back");
            Node card2Front = BoardController.cards.lookup("#" + card2Suit + "-" + card2.getValue());
            Node card2Back = BoardController.cards.lookup("#" + card2Suit + "-" + card2.getValue() + "-" + "back");

            // Save current coordinations of the cards.
            x1 = card1Front.getTranslateX() + 10;
            y1 = card1Front.getTranslateY() + 10;
            x2 = card2Front.getTranslateX();
            y2 = card2Front.getTranslateY();

            // .
            card1Front.setOnMouseClicked(event -> {
                chosenCard2 = card1;
                swapCards(Player.chosenCard1, Player.chosenCard2);
            });

            // .
            card2Front.setOnMouseClicked(event -> {
                selectionnerUneCarte(card2Front, card2);
            });

            // .
            TranslateTransition carte1Translate1 = new TranslateTransition(Duration.millis(200));
            TranslateTransition carte1Translate2 = new TranslateTransition(Duration.millis(200));
            carte1Translate1.setNode(card1Front);
            carte1Translate2.setNode(card1Back);
            carte1Translate1.setToX(x2);
            carte1Translate2.setToX(x2);
            carte1Translate1.setToY(y2);
            carte1Translate2.setToY(y2);
            carte1Translate1.play();
            carte1Translate2.play();
            BoardController.effet("resources/sounds/deal.wav");

            // .
            TranslateTransition carte2Translate1 = new TranslateTransition(Duration.millis(200));
            TranslateTransition carte2Translate2 = new TranslateTransition(Duration.millis(200));
            carte2Translate1.setNode(card2Front);
            carte2Translate2.setNode(card2Back);
            carte2Translate1.setToX(x1);
            carte2Translate2.setToX(x1);
            carte2Translate1.setToY(y1);
            carte2Translate2.setToY(y1);
            carte2Translate1.play();
            carte2Translate2.play();
            nodeSelect.setEffect(null);
            BoardController.effet("resources/sounds/deal.wav");

            // .
            for (int i = 0; i < 4; i++) {
                if (BoardController.cardsTable[i] == card2) {
                    Card temp = card1;
                    for (int j = 0; j < 4; j++) {
                        if (BoardController.player.getPlayerCards()[j] == card1) {
                            BoardController.player.getPlayerCards()[j] = card2;
                        }
                    }
                    BoardController.cardsTable[i] = temp;
                }
            }

            // .
            carte2Translate2.setOnFinished(event -> {
                chosenCard1 = null;
                chosenCard2 = null;
                nodeSelect = null;
                carteSelect = false;
                verifyCards();
                Computer.analyserTable();
            });

        }

    }

    public static void playerGagne() {
        if (isGagnant()) {
            MenuController.board.lookup("#playerWin").setVisible(true);
            BoardController.effet("resources/sounds/applause.wav");
            Computer.targetedValue = 0;
            foisGagne++;
        }
    }

    // Check if the four cards are of the same mark.

    public String getName() {
        return name;
    }


    // Select one card to swap.

    public void setName(String name) {
        this.name = name;
    }

    // Changer une card avec une autre de la table.

    public Card[] getPlayerCards() {
        return playerCards;
    }

    // Announce the player as the winner.

    public void setPlayerCards(Card[] playerCards) {
        Player.playerCards = playerCards;
    }

}
