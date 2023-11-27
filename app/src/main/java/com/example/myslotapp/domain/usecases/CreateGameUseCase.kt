package com.example.myslotapp.domain.usecases

import com.example.myslotapp.domain.Game
import com.example.myslotapp.domain.Repository

class CreateGameUseCase(private val repository: Repository) {

    operator fun invoke(bet: Int, credit: Int): Game {
        return repository.createGame(bet, credit)
    }
}