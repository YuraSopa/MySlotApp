package com.example.myslotapp.domain

data class Game(
    val reels: List<Reel>,
    val bet: Int,
    val credit: Int,
    val win: Int,
    val results: List<Symbol>
)