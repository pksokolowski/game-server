package com.github.pksokolowski.gameserver.engine.search

import com.github.pksokolowski.gameserver.engine.GameState
import kotlin.math.max

fun negamax(state: GameState, depthLeft: Int, a: Int, b: Int, timeLimit: Long, player: Int): Int {
    fun evaluate() = evaluateForActivePlayer(state)
    if (depthLeft == 0) return evaluate()
    val moves = possibleMovesFromOrNull(state) ?: return evaluate()

    var newA = a
    var score = Int.MIN_VALUE
    for (it in moves) {
        state.applyMove(it)
        score = max(score, -negamax(state, depthLeft - 1, -b, -newA, timeLimit, -player))
        state.undoMove(it)
        newA = max(score, newA)
        if (newA >= b) break
    }

    return score
}

// todo implement time limitation