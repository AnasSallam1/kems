package co.atlasgames.kems.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.atlasgames.kems.models.Card
import co.atlasgames.kems.models.Computer
import co.atlasgames.kems.models.Deck
import co.atlasgames.kems.models.Player

// Assuming GameState is a custom class; replace with your actual implementation if different
data class GameState(
    val players: List<Player>,
    val deck: Deck,
    val currentTurn: Player?
)

class GameViewModel : ViewModel() {
    private val deck = Deck()
    private val _boardCards = MutableLiveData<List<Card>>(emptyList())
    val boardCards: LiveData<List<Card>> = _boardCards

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    init {
        // Initialize game state (example)
        deck.createDeck()
        deck.shuffle()
        _gameState.value = GameState(
            players = listOf(Player("Player 1"), Player("Computer")),
            deck = deck,
            currentTurn = null
        )
    }

    fun dealCardsToBoard() {
        if (deck.cards.size < 4) {
            // Handle insufficient cards (e.g., log or update game state)
            return
        }
        val dealtCards = mutableListOf<Card>()
        repeat(4) {
            if (!deck.isEmpty()) {
                dealtCards.add(deck.deal())
            }
        }
        _boardCards.value = dealtCards
        // Optionally update game state if needed
        _gameState.value = _gameState.value?.copy()
    }

    fun dealCardsToPlayer(player: Player) {
        if (deck.isEmpty()) {
            // Handle empty deck (e.g., log or update game state)
            return
        }
        val card = deck.deal()
        player.getHand().add(card)
        // Update game state to reflect the player's new hand
        _gameState.value = _gameState.value?.copy()
    }

    fun dealCardsToComputer(computer: Computer) {
        if (deck.isEmpty()) {
            // Handle empty deck (e.g., log or update game state)
            return
        }
        val card = deck.deal()
        computer.getHand().add(card)
        // Update game state to reflect the computer's new hand
        _gameState.value = _gameState.value?.copy()
    }
}