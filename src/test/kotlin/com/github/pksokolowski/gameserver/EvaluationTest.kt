package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.evaluate
import com.github.pksokolowski.gameserver.engine.evaluateForActivePlayer
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.junit.Assert.assertEquals
import org.junit.Test

class EvaluationTest {
    @Test
    fun `evaluates one player correctly`() {
        val matrix = makeMatrix(4, 4).apply {
            this[0][0] = 1
            this[3][0] = 3
        }
        val state = GameState(matrix, 1)

        assertEquals(4, evaluate(state))
    }

    @Test
    fun `evaluates correctly with pieces of both players`() {
        val matrix = makeMatrix(4, 4).apply {
            this[0][0] = 1
            this[1][0] = 3
            this[3][3] = -3
        }
        val state = GameState(matrix, 1)

        assertEquals(1, evaluate(state))
    }

    @Test
    fun `evaluateForActivePlayer assumes perspective of the player whose turn it is at the given state`() {
        val matrix = makeMatrix(8, 8).apply {
            this[0][0] = -3
            this[1][1] = -2
            this[4][7] = 1
            this[5][7] = 1
        }
        val state = GameState(matrix, 1)

        assertEquals(3, evaluateForActivePlayer(state))
    }

    @Test
    fun `evaluateForActivePlayer assumes perspective of the player whose turn it is at the given state 2`() {
        val state = """
            00 00 00 +1 +1 00 00 -4
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 -2 00 00 00 00 00 00
            -3 00 00 00 +1 00 00 +2
        """.toGameState()

        assertEquals(-4, evaluateForActivePlayer(state))
    }
}