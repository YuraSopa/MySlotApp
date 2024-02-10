package com.example.myslotapp.presentation

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.myslotapp.R
import com.example.myslotapp.data.RepositoryImpl
import com.example.myslotapp.domain.Bet
import com.example.myslotapp.domain.Symbol
import com.example.myslotapp.domain.usecases.CreateGameUseCase
import com.example.myslotapp.domain.usecases.SpinReelsUseCase
import com.example.myslotapp.utils.Constants
import com.example.myslotapp.utils.Constants.START_CREDIT
import com.example.myslotapp.utils.Constants.symbols
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private var isSpinning = false

    private var _credit = MutableStateFlow(START_CREDIT)
    val credit: StateFlow<Int>
        get() = _credit


    private var _win = MutableStateFlow(0)
    val win: StateFlow<Int>
        get() = _win

    private var _bet = MutableStateFlow(Bet.BET_10.betValue)
    val bet: StateFlow<Int>
        get() = _bet



    private val createGameUseCase = CreateGameUseCase(repository)
    private val spinReelsUseCase = SpinReelsUseCase(repository)


    fun changeBet() {
        when (bet.value) {
            Bet.BET_10.betValue -> _bet.value = Bet.BET_20.betValue
            Bet.BET_20.betValue -> _bet.value = Bet.BET_30.betValue
            Bet.BET_30.betValue -> _bet.value = Bet.BET_10.betValue
            else -> Bet.BET_10.betValue
        }
    }

    fun setMaxBet() {
        _bet.value = Bet.BET_30.betValue
    }


    fun startSpin(slot1: ImageView, slot2: ImageView, slot3: ImageView) {
        if (!isSpinning) {
            isSpinning = true
            _credit.value -= _bet.value
            CoroutineScope(Dispatchers.Main).launch {
                spinReels(slot1, slot2, slot3)
            }
        } else {
            println("Machine is already spinning.")
        }
    }

    private suspend fun spinReels(slot1: ImageView, slot2: ImageView, slot3: ImageView) {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < Constants.TIME_ANIMATION) {
            updateImages(slot1, slot2, slot3)
            delay(Constants.TIME_INTERVAL_BETWEEN_ITEMS)
        }
        stopSpin(slot1, slot2, slot3)
    }


    private fun stopSpin(slot1: ImageView, slot2: ImageView, slot3: ImageView) {
        val result1 = getRandomItem(symbols).value
        val result2 = getRandomItem(symbols).value
        val result3 = getRandomItem(symbols).value

        // Встановлення зображень для результатів
        slot1.setImageResource(result1)
        slot2.setImageResource(result2)
        slot3.setImageResource(result3)

        // Логіка визначення виграшу в залежності від комбінацій
        _win.value = if (result1 == result2 && result2 == result3) {
            when (result1) {
                symbols[0].value -> _win.value + bet.value * 250
                symbols[1].value -> _win.value + bet.value * 100
                symbols[2].value -> _win.value + bet.value * 50
                symbols[3].value -> _win.value + bet.value * 20
                symbols[4].value -> _win.value + bet.value * 10
                symbols[5].value -> _win.value + bet.value * 5
                symbols[6].value -> _win.value + bet.value * 2
                else -> _win.value
            }

        } else {
            _win.value
        }

        isSpinning = false
    }


    private fun updateImages(slot1: ImageView, slot2: ImageView, slot3: ImageView) {
        // Оновлення зображень для кожного барабану під час обертання
        slot1.setImageResource(getRandomItem(symbols).value)
        slot2.setImageResource(getRandomItem(symbols).value)
        slot3.setImageResource(getRandomItem(symbols).value)
    }

    private fun getRandomItem(itemsList: List<Symbol>): Symbol {
        val totalWeight = itemsList.sumOf { it.weight }
        val randomValue = Random.nextInt(totalWeight)
        var cumulativeWeight = 0

        for (item in itemsList) {
            cumulativeWeight += item.weight
            if (randomValue < cumulativeWeight) {
                // Вибір ітему згідно з його вагою
                return item
            }
        }

        // За замовчуванням, якщо не вдається вибрати ітем
        return itemsList.firstOrNull() ?: Symbol(R.drawable.item1, 0)
    }
}