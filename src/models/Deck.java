package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    public static Integer CardsDealtCount; // Nombre de cards distribu��es du paquet.
    public List<Card> cards = new ArrayList<>(); // Un tableau de 52 cards, repr�sentant le paquet.

    // Constructor.

    public Deck() {

    }

    // Create a deck containing 52 cards.

    public void createDeck() throws IOException {
        for (int suit = 1; suit < 5; suit++) {
            for (int value = 1; value < 14; value++) {
                Card card = new Card(value, suit);
                cards.add(card);
            }
        }
        CardsDealtCount = 0;
    }

    // Shuffle the deck.

    public void shuffle() {
        Collections.shuffle(cards);
        CardsDealtCount = 0;
    }

    // Deal one card from the deck.

    public Card dealOneCard() {
        int hazard = (int) (Math.random() * (cards.size()));
        Card card = cards.get(hazard);
        cards.remove(card);
        CardsDealtCount++;
        return card;
    }

}
