package com.example.myslotapp.utils

import com.example.myslotapp.R
import com.example.myslotapp.domain.Symbol

object Constants {

    const val LOADING_TIME = 2000L

    const val START_CREDIT = 300

    const val TIME_INTERVAL_BETWEEN_ITEMS = 100L
    const val TIME_ANIMATION = 1500L

    val symbols = listOf(
        Symbol(value = R.drawable.item0, weight = 2),
        Symbol(value = R.drawable.item1, weight = 2),
        Symbol(value = R.drawable.item2, weight = 3),
        Symbol(value = R.drawable.item3, weight = 3),
        Symbol(value = R.drawable.item4, weight = 7),
        Symbol(value = R.drawable.item5, weight = 7),
        Symbol(value = R.drawable.item6, weight = 20)
    )
}