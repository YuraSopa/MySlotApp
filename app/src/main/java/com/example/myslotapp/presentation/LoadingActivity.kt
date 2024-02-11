package com.example.myslotapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslotapp.databinding.ActivityLoadingBinding
import com.example.myslotapp.utils.Constants.LOADING_TIME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {

    private val binding: ActivityLoadingBinding by lazy {
        ActivityLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            repeat(100) {
                delay(LOADING_TIME / 100)
                val progress = it + 1
                binding.progressBar.setProgress(progress, true)
            }
            navigateToWelcomeActivity()
        }
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}