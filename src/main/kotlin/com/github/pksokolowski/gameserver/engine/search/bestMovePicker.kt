package com.github.pksokolowski.gameserver.engine.search

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.Move

fun pickBestMoveFrom(state: GameState, depth: Int, timeLimit: Long = Long.MAX_VALUE, randomize: Boolean = false): Move? {
    val player = state.playerActive
    val possibleMoves = possibleMovesFromOrNull(state)?.orderMoves(player)
            ?: return null
    var bestMove = possibleMoves[0]
    var bestScore = Int.MIN_VALUE + 1

    for (move in possibleMoves) {
        state.applyMove(move)
        val score = -negamax(state, depth - 1, -Int.MAX_VALUE, -bestScore, timeLimit, -player)
        state.undoMove(move)

        if (score > bestScore) {
            bestMove = move
            bestScore = score
        }
    }

    return bestMove
}

// todo implement randomization, basically shuffle moves generated before ordering