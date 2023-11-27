package com.example.myslotapp.domain.usecases

import com.example.myslotapp.domain.Game
import com.example.myslotapp.domain.Repository

class SpinReelsUseCase(private val repository: Repository) {
    operator fun invoke(game: Game): Game {
        return repository.spinReels(game)
    }
}