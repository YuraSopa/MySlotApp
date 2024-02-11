package com.example.myslotapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myslotapp.R
import com.example.myslotapp.databinding.ActivityGameBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val viewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.game.collectLatest {
                binding.tvBet.text = String.format("$%s", it.bet)
                binding.tvCredit.text = String.format("$%s", it.credit)
                binding.tvWin.text = String.format("$%s", it.win)
            }
        }

        lifecycleScope.launch{
            viewModel.isSpinning.collect{
                if (it){
                    binding.ivBetOne.setImageResource(R.drawable.btn_one_disabled)
                    binding.ivBetMax.setImageResource(R.drawable.btn_max_disabled)
                    binding.ivSpin.setImageResource(R.drawable.btn_spin_disabled)
                }else{
                    binding.ivBetOne.setImageResource(R.drawable.btn_one_default)
                    binding.ivBetMax.setImageResource(R.drawable.btn_max_default)
                    binding.ivSpin.setImageResource(R.drawable.btn_spin_default)
                }
            }
        }


        lifecycleScope.launch {
            viewModel.game.collect {
                binding.ivSlot1?.setImageResource(it.slot[0].value)
                binding.ivSlot2?.setImageResource(it.slot[1].value)
                binding.ivSlot3?.setImageResource(it.slot[2].value)
            }
        }

        binding.ivBetOne.setOnClickListener {
            viewModel.changeBet()
        }

        binding.ivBetMax.setOnClickListener {
            viewModel.setMaxBet()
        }


        binding.ivSpin.setOnClickListener {
            viewModel.startSpin()
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