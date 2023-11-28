package com.example.myslotapp.presentation

import androidx.lifecycle.ViewModel
import com.example.myslotapp.data.RepositoryImpl
import com.example.myslotapp.domain.usecases.CreateGameUseCase
import com.example.myslotapp.domain.usecases.SpinReelsUseCase

class GameViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val createGameUseCase = CreateGameUseCase(repository)
    private val spinReelsUseCase = SpinReelsUseCase(repository)
    
}