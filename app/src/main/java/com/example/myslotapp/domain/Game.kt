package com.example.myslotapp.domain

data class Game(
    val reel: Reel,
    val bet: Int,
    val credit: Int,
    val win: Int,
    val results: List<Symbol>
)
