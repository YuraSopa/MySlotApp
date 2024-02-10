package com.example.myslotapp.domain

import com.example.myslotapp.utils.Constants.START_CREDIT

data class Game(
    val slot: List<Symbol>,
    val bet: Int,
    val credit: Int,
    val win: Int
){
    companion object {
        val DEFAULT_GAME = Game(
            listOf(),
            bet = Bet.BET_10.betValue,
            START_CREDIT,
            0
        )
    }
}
