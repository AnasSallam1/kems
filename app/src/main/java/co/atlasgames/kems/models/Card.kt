package co.atlasgames.kems.models

data class Card(var value: Int, var suit: Int) {

    //
    companion object {
        // Constants for suit
        const val SPADES = 1
        const val DIAMONDS = 2
        const val HEARTS = 3
        const val CLUBS = 4

        // Constants for value
        const val ACE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
        const val SEVEN = 7
        const val EIGHT = 8
        const val NINE = 9
        const val TEN = 10
        const val JACK = 11
        const val QUEEN = 12
        const val KING = 13
    }

    //
    init {
        // Validate suit
        require(suit in SPADES..CLUBS) { "Suit must be between $SPADES and $CLUBS, got $suit" }

        // Validate value
        require(value in ACE..KING) { "Value must be between $ACE and $KING, got $value" }
    }

    // Getters and Setters
    fun getSuit(): Int = suit

    fun setSuit(suit: Int) {
        this.suit = suit
    }

    fun getValue(): Int = value

    fun setValue(value: Int) {
        this.value = value
    }
}