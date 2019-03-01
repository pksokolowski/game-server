package com.github.pksokolowski.gameserver.engine

import kotlin.math.abs

class GameState(private val board: Array<IntArray>, movesCount: Int = 0) {

    fun copy() = GameState(getBoard(), movesCount)

    fun getBoard() = Array(board.size) { board[it].copyOf() }

    var movesCount: Int = movesCount
        private set
    val playerActive: Int
        get() {
            return if (movesCount % 2 == 0) 1 else -1
        }
    val width = board.size
    val height = board.getOrNull(0)?.size ?: 0

    private operator fun set(x: Int, y: Int, value: Int) {
        board[x][y] = value
    }

    operator fun get(x: Int, y: Int): Int {
        return board[x][y]
    }

    fun applyMove(move: Move) {
        val start = this[move.x1, move.y1]
        require(start != 0) { "Attempted to move a nonexistent piece." }

        val player = if (start > 0) 1 else -1
        val gain = abs(this[move.x2, move.y2]) * player

        this[move.x1, move.y1] = 0
        this[move.x2, move.y2] = start + gain

        movesCount++
    }

    fun undoMove(move: Move) {
        val destination = this[move.x2, move.y2]
        require(destination != 0) { "Attempted to undo a move of a nonexistent piece." }

        val player = if (destination > 0) 1 else -1
        val initialValue = this[move.x2, move.y2] - (player * abs(move.capture))

        this[move.x1, move.y1] = initialValue
        this[move.x2, move.y2] = move.capture

        movesCount--
    }
}