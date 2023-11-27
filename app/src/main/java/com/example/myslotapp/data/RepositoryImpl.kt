package com.example.myslotapp.data

import com.example.myslotapp.R
import com.example.myslotapp.domain.Bet
import com.example.myslotapp.domain.Game
import com.example.myslotapp.domain.Reel
import com.example.myslotapp.domain.Repository
import com.example.myslotapp.domain.Symbol

object RepositoryImpl : Repository {

    private val DEFAULT_SYMBOLS = listOf(
        Symbol(R.drawable.item0, 0),
        Symbol(R.drawable.item1, 1),
        Symbol(R.drawable.item2, 2)
    )

    override fun setBet(bet: Bet): Int {
        return when (bet) {
            Bet.BET_10 -> 10
            Bet.BET_20 -> 20
            Bet.BET_30 -> 30
        }
    }

    override fun createGame(bet: Int, credit: Int): Game {
        val reels = mutableListOf(
            Reel(
                listOf(
                    Symbol(R.drawable.item0, 0),
                    Symbol(R.drawable.item1, 1),
                    Symbol(R.drawable.item2, 2),
                    Symbol(R.drawable.item3, 3),
                    Symbol(R.drawable.item4, 4),
                    Symbol(R.drawable.item5, 5),
                    Symbol(R.drawable.item6, 6)
                )
            ),
            Reel(
                listOf(
                    Symbol(R.drawable.item0, 0),
                    Symbol(R.drawable.item1, 1),
                    Symbol(R.drawable.item2, 2),
                    Symbol(R.drawable.item3, 3),
                    Symbol(R.drawable.item4, 4),
                    Symbol(R.drawable.item5, 5),
                    Symbol(R.drawable.item6, 6)
                )
            ),
            Reel(
                listOf(
                    Symbol(R.drawable.item0, 0),
                    Symbol(R.drawable.item1, 1),
                    Symbol(R.drawable.item2, 2),
                    Symbol(R.drawable.item3, 3),
                    Symbol(R.drawable.item4, 4),
                    Symbol(R.drawable.item5, 5),
                    Symbol(R.drawable.item6, 6)
                )
            )
        )
        return Game(reels, bet, credit, 0, DEFAULT_SYMBOLS)
    }

    override fun spinReels(game: Game): Game {
        val results = mutableListOf<Symbol>()
        for (reel in game.reels) {
            results.add(reel.symbols.random())
        }
        return game.copy(results = results)
    }

    override fun getResult(game: Game): List<Symbol> {
        return game.results
    }


}