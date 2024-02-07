package com.example.myslotapp.utils

import com.example.myslotapp.R
import com.example.myslotapp.domain.Symbol

object Constants {

    const val TIME_INTERVAL_BETWEEN_ITEMS = 100L
    const val TIME_ANIMATION = 1500L

    val symbols = listOf(
        Symbol(value = R.drawable.item0, weight = 10),
        Symbol(value = R.drawable.item1, weight = 20),
        Symbol(value = R.drawable.item2, weight = 30),
        Symbol(value = R.drawable.item3, weight = 40),
        Symbol(value = R.drawable.item4, weight = 50),
        Symbol(value = R.drawable.item5, weight = 60),
        Symbol(value = R.drawable.item6, weight = 70)
    )
}