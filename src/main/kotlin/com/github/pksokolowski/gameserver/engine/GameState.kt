package com.github.pksokolowski.gameserver.engine

class GameState(private val board: Array<IntArray>, val movesCount: Int = 0) {

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
        val gain = this[move.x2, move.y2] * player

        this[move.x1, move.y1] = 0
        this[move.x2, move.y2] = start + gain
    }

    fun undoMove(move: Move) {
        val destination = this[move.x2, move.y2]
        require(destination != 0) { "Attempted to undo a move of a nonexistent piece." }

        val initialValue = this[move.x2, move.y2] - Math.abs(move.capture)

        this[move.x1, move.y1] = initialValue
        this[move.x2, move.y2] = move.capture
    }
}