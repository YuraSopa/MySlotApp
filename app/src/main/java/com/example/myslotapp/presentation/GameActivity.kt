package com.example.myslotapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myslotapp.R
import com.example.myslotapp.data.RepositoryImpl
import com.example.myslotapp.databinding.ActivityGameBinding
import com.example.myslotapp.domain.usecases.CreateGameUseCase
import com.example.myslotapp.domain.usecases.SpinReelsUseCase
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val repository = RepositoryImpl


    private val createGameUseCase = CreateGameUseCase(repository)
    private val spinReelsUseCase = SpinReelsUseCase(repository)
    private val viewModel = GameViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        lifecycleScope.launch {
            viewModel.credit.collect {
                binding.tvCredit.text = String.format("$%s", it)
            }
        }

        lifecycleScope.launch {
            viewModel.bet.collect {
                binding.tvBet.text = String.format("$%s", it)
            }
        }

        lifecycleScope.launch {
            viewModel.win.collect {
                binding.tvWin.text = String.format("$%s", it)
            }
        }

        binding.ivBetOne.setOnClickListener {
            viewModel.changeBet()
        }

        binding.ivBetMax.setOnClickListener {
            viewModel.setMaxBet()
        }


        binding.ivSpin.setOnClickListener {
            viewModel.startSpin(
                binding.ivSlot1!!,
                binding.ivSlot2!!,
                binding.ivSlot3!!,
            )
        }

        binding.ivPayTable.setOnClickListener {
            launchFragment(TableFragment())
        }
    }


    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}