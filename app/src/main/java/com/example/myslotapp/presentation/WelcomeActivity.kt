package com.example.myslotapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslotapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivStart.setOnClickListener {
            Intent(this, GameActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.ivExit.setOnClickListener {
            finish()
        }
    }
}