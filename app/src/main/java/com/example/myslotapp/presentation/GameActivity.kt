package com.example.myslotapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslotapp.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivSpin.setOnClickListener {

        }
    }

}