/**
 * The HelloWorld program implements an application that
 * simply displays "Hello World!" to the standard output.
 *
 * @author  Anas Sallam
 * @version 1.0
 * @since   2020-12-25
 */

package models;

public class Card {

    public final static Integer SPADES = 1, DIAMONDS = 2, HEARTS = 3, CLUBS = 4; // Codes pour les 4 suits.

    private Integer suit; // Suit � laquelle appartient la carte.
    private Integer value; // Values of cards, from 1 to 13.

    // Constructor.

    public Card(Integer value, Integer suit) {
        this.value = value;
        this.suit = suit;
    }

    // Getters & Setters.

    public Integer getSuit() {
        return suit;
    }

    public void setSuit(Integer suit) {
        this.suit = suit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
