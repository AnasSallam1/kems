package co.atlasgames.kems.models

data class Computer(
    private var score: Int = 0,      // Computer's score, defaulting to 0
    private var hand: MutableList<Card> = mutableListOf() // Computer's hand of cards
) {
    init {
        // Ensure the hand starts with exactly 4 cards (using placeholders if no deck is provided)
        require(hand.size <= 4) { "Initial hand cannot exceed 4 cards, got ${hand.size}" }
        while (hand.size < 4) {
            hand.add(Card(value = Card.ACE, suit = Card.SPADES))
        }
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