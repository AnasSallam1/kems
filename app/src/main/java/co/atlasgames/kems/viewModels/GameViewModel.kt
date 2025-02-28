package co.atlasgames.kems.viewModels

import android.app.GameState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.atlasgames.kems.models.Player

class GameViewModel : ViewModel() {
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    fun dealCardsToBoard(player: Player) {
        // Logic to deal card and update UI through LiveData
    }

    fun dealCardToPlayer(player: Player) {
        // Logic to deal card and update UI through LiveData
    }

    // More game logic methods
    fun dealCardToComputer(player: Player) {
        // Logic to deal card and update UI through LiveData
    }
}