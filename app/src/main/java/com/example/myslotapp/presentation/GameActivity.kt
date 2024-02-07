package com.example.myslotapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslotapp.R
import com.example.myslotapp.data.RepositoryImpl
import com.example.myslotapp.databinding.ActivityGameBinding
import com.example.myslotapp.domain.Bet
import com.example.myslotapp.domain.Symbol
import com.example.myslotapp.domain.usecases.CreateGameUseCase
import com.example.myslotapp.domain.usecases.SpinReelsUseCase
import com.example.myslotapp.utils.Constants
import com.example.myslotapp.utils.Constants.TIME_ANIMATION
import com.example.myslotapp.utils.Constants.TIME_INTERVAL_BETWEEN_ITEMS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }


    private val repository = RepositoryImpl
    private var bet = 10
    private var credit = 300
    private var win = 0

    private var isSpinning = false

    private val createGameUseCase = CreateGameUseCase(repository)
    private val spinReelsUseCase = SpinReelsUseCase(repository)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvBet.text = String.format("$%s", bet)
        binding.tvCredit.text = String.format("$%s", credit)
        binding.tvWin.text = String.format("$%s", win)

        binding.ivBetOne.setOnClickListener {
            when (bet) {
                Bet.BET_10.betValue -> bet = Bet.BET_20.betValue
                Bet.BET_20.betValue -> bet = Bet.BET_30.betValue
                Bet.BET_30.betValue -> bet = Bet.BET_10.betValue
            }
            binding.tvBet.text = String.format("$%s", bet)
        }

        binding.ivBetMax.setOnClickListener {
            bet = 30
            binding.tvBet.text = String.format("$%s", bet)
        }


        binding.ivSpin.setOnClickListener {
            startSpin()
        }
    }


    private fun startSpin() {
        if (!isSpinning) {
            isSpinning = true
            CoroutineScope(Dispatchers.Main).launch {
                spinReels()
            }
        } else {
            println("Machine is already spinning.")
        }
    }

    private suspend fun spinReels() {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < TIME_ANIMATION) {
            updateImages()
            delay(TIME_INTERVAL_BETWEEN_ITEMS)
        }
        stopSpin()
    }


    private fun stopSpin() {
        val result1 = getRandomItem(Constants.symbols).value
        val result2 = getRandomItem(Constants.symbols).value
        val result3 = getRandomItem(Constants.symbols).value

        // Встановлення зображень для результатів
        binding.ivSlot1?.setImageResource(result1)
        binding.ivSlot2?.setImageResource(result2)
        binding.ivSlot3?.setImageResource(result3)

        // Логіка визначення виграшу в залежності від комбінацій
        // Можете використовувати if-else або інші умови для цього
        win = when {
            result1 == result2 && result2 == result3 -> win + 100 // Три однакові ітеми
            result1 == result2 || result2 == result3 || result1 == result3 -> win + 50 // Два однакові ітеми
            else -> win // Немає виграшу
        }
        binding.tvWin.text = String.format("$%s", win)
        println("Win: $win")

        isSpinning = false
    }


    private fun updateImages() {
        // Оновлення зображень для кожного барабану під час обертання
        binding.ivSlot1?.setImageResource(getRandomItem(Constants.symbols).value)
        binding.ivSlot2?.setImageResource(getRandomItem(Constants.symbols).value)
        binding.ivSlot3?.setImageResource(getRandomItem(Constants.symbols).value)
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