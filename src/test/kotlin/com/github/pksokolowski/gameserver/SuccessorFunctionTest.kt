package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.motion.Move
import com.github.pksokolowski.gameserver.engine.motion.possibleMovesFrom
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class SuccessorFunctionTest {
    @Test
    fun `generates possible moves and no others`() {
        val state = """
            00 00 00 00
            00 00 00 00
            00 00 00 00
            +1 00 00 +1
        """.toGameState()

        val possibleMoves = arrayOf(
                Move(0, 0, 0, 1, 0),
                Move(3, 0, 3, 1, 0)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }

    @Test
    fun `generates advanced moves for a high energy piece`() {
        val state = """
            00 00 00 00 00
            00 00 00 00 00
            00 00 +4 00 00
            00 00 00 00 00
            00 00 00 00 00
        """.toGameState()

        val possibleMoves = arrayOf(
                // up, down, left, right; 2 in each direction
                Move(2, 2, 2, 3, 0),
                Move(2, 2, 2, 4, 0),
                Move(2, 2, 2, 1, 0),
                Move(2, 2, 2, 0, 0),
                Move(2, 2, 1, 2, 0),
                Move(2, 2, 0, 2, 0),
                Move(2, 2, 3, 2, 0),
                Move(2, 2, 4, 2, 0),

                // diagonals 2 in each direction
                Move(2, 2, 1, 3, 0),
                Move(2, 2, 0, 4, 0),
                Move(2, 2, 3, 3, 0),
                Move(2, 2, 4, 4, 0),
                Move(2, 2, 1, 1, 0),
                Move(2, 2, 0, 0, 0),
                Move(2, 2, 3, 1, 0),
                Move(2, 2, 4, 0, 0)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }

    @Test
    fun `handles collision on NON_CAPTURE mobility`() {
        val matrix = makeMatrix(8, 8).apply {
            this[2][1] = 1
            this[2][2] = 1
        }
        val state = GameState(matrix, 1)
        val possibleMoves = arrayOf<Move>(
                Move(2, 2, 2, 3, 0)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }

    @Test
    fun `only generates moves for the player whose turn it is`() {
        val matrix = makeMatrix(8, 8).apply {
            this[2][0] = 1
            this[2][7] = -1
        }
        val state = GameState(matrix, 1)
        val possibleMoves = arrayOf(
                Move(2, 7, 2, 6, 0)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }

    @Test
    fun `generates captures for energy 1 pieces`() {
        val matrix = makeMatrix(8, 8).apply {
            this[3][3] = 1
            this[4][4] = -1
            this[2][4] = -2
        }
        val state = GameState(matrix)
        val possibleMoves = arrayOf(
                Move(3, 3, 3, 4, 0),
                Move(3, 3, 2, 4, -2),
                Move(3, 3, 4, 4, -1)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }

    @Test
    fun `supports high energy pieces`() {
        val matrix = makeMatrix(8, 8).apply {
            this[0][0] = 3
        }
        val state = GameState(matrix)
        val possibleMoves = arrayOf(
                Move(0, 0, 0, 1, 0),
                Move(0, 0, 0, 2, 0),
                Move(0, 0, 1, 0, 0),
                Move(0, 0, 2, 0, 0)
        )
        val generatedMoves = possibleMovesFrom(state).toTypedArray()
        assertArrayEquals(possibleMoves, generatedMoves)
    }
}