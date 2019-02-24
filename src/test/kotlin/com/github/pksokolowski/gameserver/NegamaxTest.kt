package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.search.negamax
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NegamaxTest {
    @Test
    fun `basic case works`() {
        val matrix = makeMatrix(3, 3).apply {
            this[0][1] = -1
            this[1][0] = 3
        }
        val state = GameState(matrix, 0)
        val score = negamax(state, 1)
        assertEquals(4, score)
    }

    @Test
    fun `finds inevitable victory`() {
        val state = """
            -1 -1 -1
            00 00 00
            +2 +3 +2
        """.toGameState()
        val score = negamax(state, 9)
        assertTrue(score > 0)
    }

    @Test
    fun `returns score being the sum of absolute piece values on the board`() {
        val state = """
            -1 -1
            +1 +2
        """.toGameState()
        val score = negamax(state, 3)
        assertEquals(5, score)
    }

    private fun negamax(state: GameState, depth: Int, timeLimit: Long = Long.MAX_VALUE): Int {
        val player = state.playerActive
        val a = Int.MIN_VALUE + 1
        val b = Int.MAX_VALUE
        return negamax(state, depth, a, b, timeLimit, player)
    }
}