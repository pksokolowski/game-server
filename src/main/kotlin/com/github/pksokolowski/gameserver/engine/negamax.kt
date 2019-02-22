package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.motion.possibleMovesFromOrNull

fun negamax(state: GameState, depth: Int, timeLimit: Long = Long.MAX_VALUE): Int {
    val player = state.playerActive
    val a = Int.MIN_VALUE
    val b = Int.MAX_VALUE
    return negamax(state, depth, a, b, timeLimit, player)
}

private fun negamax(state: GameState, depthLeft: Int, a: Int, b: Int, timeLimit: Long, player: Int): Int {
    fun evaluate() = evaluateForActivePlayer(state)
    if (depthLeft == 0) return evaluate()
    val moves = possibleMovesFromOrNull(state) ?: return evaluate()

    var newA = a
    var score = Int.MIN_VALUE
    for (it in moves) {
        state.applyMove(it)
        score = Math.max(score, -negamax(state, depthLeft - 1, -b, -newA, timeLimit, -player))
        state.undoMove(it)
        newA = Math.max(score, newA)
        if (newA >= b) break
    }

    return score
}

// todo implement time limitation