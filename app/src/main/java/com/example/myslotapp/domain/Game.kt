package com.example.myslotapp.domain

import com.example.myslotapp.utils.Constants.START_CREDIT
import com.example.myslotapp.utils.Constants.symbols

data class Game(
    val slot: List<Symbol>,
    var bet: Int,
    var credit: Int,
    var win: Int
){
    companion object {
        val DEFAULT_GAME = Game(
            listOf(
                symbols.random(),
                symbols.random(),
                symbols.random()
            ),
            bet = Bet.BET_10.betValue,
            START_CREDIT,
            0
        )
    }
}
