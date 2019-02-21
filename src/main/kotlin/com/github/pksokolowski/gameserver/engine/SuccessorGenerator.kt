package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.MotionDirections.*
import com.github.pksokolowski.gameserver.engine.MoveTypes.*

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

        fun add(direction: MotionDirections, type: MoveTypes, stepsRange: IntRange = 1..1) {
            val (dx, dy) = direction

            for (s in stepsRange) {
                val destinationX = x + (s * dx * player)
                val destinationY = y + (s * dy * player)
                if (destinationX !in 0 until state.width || destinationY !in 0 until state.height) return
                val destinationValue = state[destinationX, destinationY]

                if (type == MoveTypes.CAPTURE_ONLY && destinationValue == 0) continue
                if (type == MoveTypes.NON_CAPTURE && destinationValue != 0) break

                val move = Move(x, y, destinationX, destinationY, destinationValue)
                moves.add(move)
            }
        }

        fun addCapturesForward() {
            add(FORWARD_LEFT, CAPTURE_ONLY)
            add(FORWARD_RIGHT, CAPTURE_ONLY)
        }

        fun addCapturesForwardAndBackwards() {
            addCapturesForward()
            add(BACKWARDS_LEFT, CAPTURE_ONLY)
            add(BACKWARDS_RIGHT, CAPTURE_ONLY)
        }

        fun addVerticalAndHorizontal(range: IntRange, type: MoveTypes = ANY) {
            add(FORWARD, ANY, range)
            add(BACKWARDS, ANY, range)
            add(LEFT, ANY, range)
            add(RIGHT, ANY, range)
        }

        fun addDiagonal(range: IntRange, type: MoveTypes = ANY) {
            add(FORWARD_LEFT, ANY, range)
            add(FORWARD_RIGHT, ANY, range)
            add(BACKWARDS_LEFT, ANY, range)
            add(BACKWARDS_RIGHT, ANY, range)
        }

        val energy = Math.abs(value)

        when (energy) {
            0 -> {
            }
            1 -> {
                add(FORWARD, NON_CAPTURE)
                addCapturesForward()
            }
            2 -> {
                add(FORWARD, NON_CAPTURE)
                add(BACKWARDS, NON_CAPTURE)
                addCapturesForwardAndBackwards()
            }
            3 -> {
                addCapturesForwardAndBackwards()
                addVerticalAndHorizontal(1..2)
            }
            else -> {
                addVerticalAndHorizontal(1..2)
                addDiagonal(1..4)
            }
        }
    }

    return moves
}
