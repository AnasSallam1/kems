package co.atlasgames.kems.models

data class Player(
    var name: String,              // Player's name
    var score: Int = 0,            // Player's score, defaulting to 0
    var hand: MutableList<Card> = mutableListOf(),  // Player's hand of cards
    val deck: Deck? = null             // Deck to draw cards from
) {
    init {
        // Draw 4 cards from the deck
        deck?.let {
            repeat(4 - hand.size) {
                if (deck.cards.isNotEmpty()) {
                    hand.add(deck.deal())
                } else {
                    throw IllegalStateException("Deck is empty, cannot deal 4 cards to $name")
                }
            }
        }
    }

    // Getter and Setter for name
    fun getName(): String {
        return name
    }

    fun setName(newName: String) {
        this.name = newName
    }

    // Getter and Setter for score
    fun getScore(): Int {
        return score
    }

    fun setScore(newScore: Int) {
        this.score = newScore
    }

    // Getter and Setter for hand
    fun getHand(): MutableList<Card> {
        return hand
    }

    fun setHand(newHand: MutableList<Card>) {
        require(newHand.size <= 4) { "Hand cannot exceed 4 cards, got ${newHand.size}" }
        this.hand = newHand
        // Ensure hand has exactly 4 cards after setting
        while (hand.size < 4) {
            hand.add(Card(value = Card.ACE, suit = Card.SPADES))
        }
    }
}