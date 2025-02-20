package co.atlasgames.kems.models

import java.io.IOException

class Deck (private val cards: MutableList<Card> = ArrayList()) {

    private var cardsDealt: Int = 0 // Number of cards dealt from the deck
    init {

    }

    @Throws(IOException::class)
    fun createDeck() {
        for (suit in 1..4) {
            for (value in 1..13) {
                val card = Card(value, suit)
                cards.add(card)
            }
        }
        cardsDealt = 0
    }

    // Shuffle the deck
    fun shuffle() {
        cards.shuffle()
        cardsDealt = 0
    }

    // Deal a card from the deck
    fun deal(): Card {
        /* Deals one card from the deck */
        val random = (cards.indices).random()
        val card = cards.removeAt(random)
        //return card
        //val randomIndex = Random.nextInt(cards.size)
        //val card = cards.removeAt(randomIndex)
        cardsDealt++ // Increase the total number of cards dealt
        return card
    }

    // If the deck is empty
    fun isEmpty() = cards.isEmpty()
}