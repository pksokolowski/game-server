package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.motion.Move
import com.github.pksokolowski.gameserver.engine.pickBestMoveFrom
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.junit.Assert.assertEquals
import org.junit.Test

class BestMovePickerTest {
    @Test
    fun `picks the best move in an obvious case`() {
        val state = """
            00 00 00
            -1 00 -4
            00 +1 +1
        """.toGameState()

        val bestMove = Move(1, 0, 2, 1, -4)
        val pickedMove = pickBestMoveFrom(state, 8)

        assertEquals(bestMove, pickedMove)
    }

    @Test
    fun `works for the minus player, AI, too`() {
        val state = """
            00 00 -4
            00 +1 +1
            00 +1 00
        """.toGameState(1)

        val bestMove = Move(2, 2, 1, 1, 1)
        val pickedMove = pickBestMoveFrom(state, 8)

        assertEquals(bestMove, pickedMove)
    }
}