package com.simon.rockpaperscissors.model

import com.simon.rockpaperscissors.R

//val ROCK_MOVE = 0
//val PAPER_MOVE = 1
//val SCISSORS_MOVE = 2

class Move (
    val rockMove: Int = 0,
    val paperMove: Int = 1,
    val scissorsMove: Int = 2
){
    fun getMoveImage(moveIndex: Int): Int {
        return when (moveIndex) {
            rockMove -> { R.drawable.rock }
            paperMove -> { R.drawable.paper }
            scissorsMove -> { R.drawable.scissors }
            else -> R.drawable.rock
        }
    }
}