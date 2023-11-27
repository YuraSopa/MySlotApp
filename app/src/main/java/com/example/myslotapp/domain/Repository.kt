package com.example.myslotapp.domain

interface Repository {

    fun setBet(bet: Bet): Int

    fun createGame(bet: Int, credit: Int): Game

    fun spinReels(game: Game): Game

    fun getResult(game: Game): List<Symbol>
}