package co.atlasgames.kems.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import co.atlasgames.kems.models.Player
import co.atlasgames.kems.ui.theme.KemsTheme
import co.atlasgames.kems.viewModels.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KemsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel()) {
    val player = Player("Player 1")
    val boardCards = viewModel.boardCards.observeAsState().value ?: emptyList()

    Column(modifier = modifier) {
        // Display a greeting or title
        Text(text = "Welcome to Kems, ${player.getName()}!")

        // Deal cards to board when the screen loads (could be triggered by a button instead)
        if (boardCards.isEmpty()) {
            viewModel.dealCardsToBoard() // Pass the player parameter
        }

        // Display board cards
        Text(text = "Board Cards:")
        boardCards.forEach { card ->
            Text(text = "${card.getValue()} of ${card.getSuit()}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    KemsTheme {
        GameScreen()
    }
}