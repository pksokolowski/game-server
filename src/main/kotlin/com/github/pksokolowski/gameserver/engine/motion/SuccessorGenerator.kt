package com.github.pksokolowski.gameserver.engine.motion

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.motion.MotionDirections.*
import com.github.pksokolowski.gameserver.engine.motion.MoveTypes.*

fun possibleMovesFrom(state: GameState): List<Move> {
    val moves = mutableListOf<Move>()
    val player = if (state.movesCount % 2 == 0) -1 else 1

    for (x in 0 until state.width) for (y in 0 until state.height) {
        // skip empty squares
        val value = state[x, y]
        if (value == 0) continue

        // skip pieces of the other player
        val owner = if (value > 0) 1 else -1
        if (owner != player) continue

        fun add(direction: MotionDirections, type: MoveTypes, stepsRange: IntRange = 1..1) =
                addToList(state, player, moves, x, y, direction, type, stepsRange)

        val energy = Math.abs(value)

        when (energy) {
            0 -> {
            }
            1 -> {
                add(FORWARD, NON_CAPTURE)
                presetCapturesForward(::add)
            }
            2 -> {
                add(FORWARD, NON_CAPTURE)
                add(BACKWARDS, NON_CAPTURE)
                presetCapturesForwardAndBackwards(::add)
            }
            3 -> {
                presetCapturesForwardAndBackwards(::add)
                presetVerticalAndHorizontal(::add, 1..2)
            }
            else -> {
                presetVerticalAndHorizontal(::add, 1..2)
                presetDiagonal(::add, 1..4)
            }
        }
    }

    return moves
}

private fun addToList(state: GameState, player: Int, list: MutableList<Move>, x: Int, y: Int, direction: MotionDirections, type: MoveTypes, stepsRange: IntRange) {
    val (dx, dy) = direction

    for (s in stepsRange) {
        val destinationX = x + (s * dx * player)
        val destinationY = y + (s * dy * player)
        if (destinationX !in 0 until state.width || destinationY !in 0 until state.height) return
        val destinationValue = state[destinationX, destinationY]

        if (type == MoveTypes.CAPTURE_ONLY && destinationValue == 0) continue
        if (type == MoveTypes.NON_CAPTURE && destinationValue != 0) break

        val move = Move(x, y, destinationX, destinationY, destinationValue)
        list.add(move)
    }
}