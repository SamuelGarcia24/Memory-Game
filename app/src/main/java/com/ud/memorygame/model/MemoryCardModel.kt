package com.ud.memorygame.model

data class GameCard(

    val id: Int,

    val imageRes: Int,

    var isFlipped: Boolean = false,

    var isMatched: Boolean = false

)