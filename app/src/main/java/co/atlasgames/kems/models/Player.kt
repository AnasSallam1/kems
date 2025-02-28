package co.atlasgames.kems.models

data class Player(val name: String, var score: Int = 0, val hand: MutableList<Card> = mutableListOf()) {
    init {

    }
}