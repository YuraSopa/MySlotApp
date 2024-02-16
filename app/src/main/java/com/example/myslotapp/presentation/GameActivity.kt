package com.example.myslotapp.presentation

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myslotapp.R
import com.example.myslotapp.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val viewModel = GameViewModel()

    private var previousBet: Int? = null
    private var previousCredit: Int? = null
    private var previousWin: Int? = null

    private var enoughCredits = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.game.collect {

                if (it.bet != previousBet) {
                    binding.tvBet.startAnimation(createAnimation())
                    binding.tvBet.text = String.format("$%s", it.bet)
                    previousBet = it.bet
                }

                if (it.credit != previousCredit) {
                    binding.tvCredit.startAnimation(createAnimation())
                    binding.tvCredit.text = String.format("$%s", it.credit)
                    previousCredit = it.credit
                }

                if (it.win != previousWin) {
                    binding.tvWin.startAnimation(createAnimation())
                    binding.tvWin.text = String.format("$%s", it.win)
                    previousWin = it.win
                }
            }
        }


        lifecycleScope.launch {
            viewModel.enoughCredits.collect {
                enoughCredits = it
                if (it) {
                    binding.ivSpin.setImageResource(R.drawable.btn_spin_default)
                    binding.ivSpin.isClickable = true
                } else {
                    binding.ivSpin.setImageResource(R.drawable.btn_spin_disabled)
                    binding.ivSpin.isClickable = false
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isSpinning.collect {
                if (it) {
                    disabledButtons(true)
                } else {
                    if (enoughCredits) {
                        disabledButtons(false)
                    } else {
                        disabledButtons(false, disableOnlySpin = true)
                    }
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
            clickAnimation(
                binding.ivBetOne, R.drawable.btn_one_pressed, R.drawable.btn_one_default
            )
            viewModel.changeBet()
        }

        binding.ivBetMax.setOnClickListener {
            clickAnimation(
                binding.ivBetMax, R.drawable.btn_max_pressed, R.drawable.btn_max_default
            )
            viewModel.setMaxBet()
        }

        binding.ivPayTable.setOnClickListener {
            clickAnimation(
                binding.ivPayTable, R.drawable.btn_table_pressed, R.drawable.btn_table_default
            )
            launchFragment(TableFragment())
        }

        binding.ivSpin.setOnClickListener {
            viewModel.startSpin()
        }
    }

    private fun disabledButtons(isDisable: Boolean, disableOnlySpin: Boolean = false) {
        if (isDisable) {
            with(binding) {
                ivBetOne.setImageResource(R.drawable.btn_one_disabled)
                ivBetMax.setImageResource(R.drawable.btn_max_disabled)
                ivSpin.setImageResource(R.drawable.btn_spin_disabled)
                ivBetOne.isClickable = false
                ivBetMax.isClickable = false
                ivSpin.isClickable = false
            }
        } else {
            with(binding) {
                binding.ivBetOne.setImageResource(R.drawable.btn_one_default)
                binding.ivBetMax.setImageResource(R.drawable.btn_max_default)
                binding.ivSpin.setImageResource(R.drawable.btn_spin_default)
                ivBetOne.isClickable = true
                ivBetMax.isClickable = true
                ivSpin.isClickable = true
            }
        }

        if (disableOnlySpin) {
            binding.ivSpin.setImageResource(R.drawable.btn_spin_disabled)
            binding.ivSpin.isClickable = false
        }

    }

    private fun clickAnimation(imageView: ImageView, resIdPressed: Int, resIdDefault: Int) {
        lifecycleScope.launch {
            if (imageView.isClickable) {
                imageView.setImageResource(resIdPressed)
                delay(200)
                imageView.setImageResource(resIdDefault)
            }

        }
    }

    private fun createAnimation(): Animation {
        return AnimationUtils.loadAnimation(this, R.anim.text_animation)
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}