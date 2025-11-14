package co.atlasgames.kems.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.atlasgames.kems.R
import co.atlasgames.kems.models.Deck
import co.atlasgames.kems.models.Player

class GameActivity : AppCompatActivity() {
    private val deck = Deck()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        deck.tableCards.observe(this) { cards ->
            // Update UI with the 4 table cards, e.g., display in a RecyclerView
            println("Table updated with: $cards")
        }

        val player = Player("Anas")
        deck.dealCardsToTable()
    }
}