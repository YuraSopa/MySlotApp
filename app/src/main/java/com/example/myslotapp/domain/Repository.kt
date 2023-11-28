package com.example.myslotapp.domain

interface Repository {

    fun createGame(bet: Int, credit: Int): Game

    fun spinReels(game: Game): Game

}
