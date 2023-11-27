package com.example.myslotapp.domain.usecases

import com.example.myslotapp.domain.Game
import com.example.myslotapp.domain.Repository
import com.example.myslotapp.domain.Symbol

class GetResultUseCase(private val repository: Repository) {

    operator fun invoke(game: Game): List<Symbol> {
        return repository.getResult(game)
    }
}