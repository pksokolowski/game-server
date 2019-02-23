package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.motion.Move
import com.github.pksokolowski.gameserver.engine.motion.possibleMovesFromOrNull

fun pickBestMoveFrom(state: GameState, depth: Int, timeLimit: Long = Long.MAX_VALUE, randomize: Boolean = false): Move? {
    val possibleMoves = possibleMovesFromOrNull(state) ?: return null
    var bestMove = possibleMoves[0]
    var bestScore = Int.MIN_VALUE

    for (move in possibleMoves) {
        state.applyMove(move)
        val score = -negamax(state, depth - 1, timeLimit)
        state.undoMove(move)

        if (score > bestScore) {
            bestMove = move
            bestScore = score
        }
    }

    return bestMove
}

// todo implement randomization, basically shuffle moves generated before ordering