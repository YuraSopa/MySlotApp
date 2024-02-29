package com.example.myslotapp.presentation

import androidx.lifecycle.ViewModel
import com.example.myslotapp.R
import com.example.myslotapp.domain.Bet
import com.example.myslotapp.domain.Game
import com.example.myslotapp.domain.Symbol
import com.example.myslotapp.utils.Constants
import com.example.myslotapp.utils.Constants.symbols
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {


    private var _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean>
        get() = _isSpinning


    private var _game = MutableStateFlow(Game.DEFAULT_GAME)
    val game: StateFlow<Game>
        get() = _game

    private var _enoughCredits = MutableStateFlow(true)
    val enoughCredits: StateFlow<Boolean>
        get() = _enoughCredits

    private fun checkEnoughCredits() {
        _enoughCredits.value = game.value.credit - game.value.bet >= 0
    }

    fun changeBet() {
        _game.value = when (_game.value.bet) {
            Bet.BET_10.betValue -> _game.value.copy(bet = Bet.BET_20.betValue)
            Bet.BET_20.betValue -> _game.value.copy(bet = Bet.BET_30.betValue)
            Bet.BET_30.betValue -> _game.value.copy(bet = Bet.BET_10.betValue)
            else -> Game.DEFAULT_GAME
        }
        checkEnoughCredits()
    }

    fun setMaxBet() {
        _game.value = _game.value.copy(bet = Bet.BET_30.betValue)
        checkEnoughCredits()
    }


    fun startSpin() {
        if (!isSpinning.value && enoughCredits.value) {
            _isSpinning.value = true
            _game.value.credit -= _game.value.bet
            CoroutineScope(Dispatchers.Main).launch {
                spinReels()
            }
        } else {
            println("Machine is already spinning.")
        }
    }

    private suspend fun spinReels() {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < Constants.TIME_ANIMATION_SPIN) {
            updateImages()
            delay(Constants.TIME_INTERVAL_BETWEEN_ITEMS)
        }
        stopSpin()
    }


    private fun stopSpin() {
        checkEnoughCredits()
        val result1 = game.value.slot[0]
        val result2 = game.value.slot[1]
        val result3 = game.value.slot[2]


        // Логіка визначення виграшу в залежності від комбінацій
        _game.value = _game.value.copy(
            win = if (result1 == result2 && result2 == result3) {
                when (result1.value) {
                    symbols[0].value -> _game.value.win + _game.value.bet * 250
                    symbols[1].value -> _game.value.win + _game.value.bet * 100
                    symbols[2].value -> _game.value.win + _game.value.bet * 50
                    symbols[3].value -> _game.value.win + _game.value.bet * 20
                    symbols[4].value -> _game.value.win + _game.value.bet * 10
                    symbols[5].value -> _game.value.win + _game.value.bet * 5
                    symbols[6].value -> _game.value.win + _game.value.bet * 2
                    else -> _game.value.win
                }

            } else {
                _game.value.win
            }
        )

        _isSpinning.value = false
    }


    private fun updateImages() {

        val newGame = _game.value.copy(
            slot = listOf(
                getRandomItem(),
                getRandomItem(),
                getRandomItem()
            )
        )
        _game.value = newGame

    }

    private fun getRandomItem(): Symbol {
        val totalWeight = symbols.sumOf { it.weight }
        val randomValue = Random.nextInt(totalWeight)
        var cumulativeWeight = 0

        for (item in symbols) {
            cumulativeWeight += item.weight
            if (randomValue < cumulativeWeight) {
                // Вибір ітему згідно з його вагою
                return item
            }
        }
        return symbols.firstOrNull() ?: Symbol(R.drawable.item1, 0)
    }
}