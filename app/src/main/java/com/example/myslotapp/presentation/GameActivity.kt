package com.example.myslotapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslotapp.data.RepositoryImpl
import com.example.myslotapp.databinding.ActivityGameBinding
import com.example.myslotapp.domain.usecases.CreateGameUseCase
import com.example.myslotapp.domain.usecases.SpinReelsUseCase

class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }


    private val repository = RepositoryImpl
    private var bet = 10
    private val credit = 300
    private val win = 0

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
                10 -> bet = 20
                20 -> bet = 30
                30 -> bet = 10
            }
            binding.tvBet.text = String.format("$%s", bet)
        }


        binding.ivSpin.setOnClickListener {

        }
    }

}