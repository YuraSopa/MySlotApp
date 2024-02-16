package com.example.myslotapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myslotapp.R
import com.example.myslotapp.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivStart.setOnClickListener {
            Intent(this, GameActivity::class.java).also {
                lifecycleScope.launch {
                    binding.ivStart.setImageResource(R.drawable.btn_start_pressed)
                    delay(200)
                    binding.ivStart.setImageResource(R.drawable.btn_start)
                    startActivity(it)
                }
            }
        }

        binding.ivExit.setOnClickListener {
            finish()
        }
    }
}