package co.atlasgames.kems.models

import androidx.lifecycle.MutableLiveData
import java.io.IOException

class Deck (val cards: MutableList<Card> = ArrayList()) {
    val tableCards: MutableLiveData<MutableList<Card>> = MutableLiveData(mutableListOf()) // LiveData for table cards
    private var cardsDealt: Int = 0 // Number of cards dealt from the deck

    init {
        try {
            createDeck() // Automatically populate the deck with 52 cards
        } catch (e: IOException) {
            throw IllegalStateException("Failed to initialize deck due to IO error", e)
        }
        // Validate that the initial cards list doesn't exceed 52
        require(cards.size <= 52) { "Deck cannot contain more than 52 cards, got ${cards.size}" }
        // Optionally, ensure cardsDealt is consistent with initial state
        if (cards.isEmpty()) cardsDealt = 0
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
        cardsDealt++ // Increase the total number of cards dealt
        return card
    }

    // Deal 4 cards to the table
    fun dealCardsToTable() {
        if (cards.size < 4) throw IllegalStateException("Not enough cards to deal 4 to the table, only ${cards.size} left")
        val dealtCards = mutableListOf<Card>()
        repeat(4) {
            val card = deal()
            dealtCards.add(card)
        }
        // Update LiveData with the new table cards
        tableCards.value = dealtCards
    }

    // If the deck is empty
    fun isEmpty() = cards.isEmpty()
}