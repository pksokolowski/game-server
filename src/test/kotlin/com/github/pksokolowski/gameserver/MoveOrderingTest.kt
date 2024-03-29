package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.Move
import com.github.pksokolowski.gameserver.engine.search.orderMoves
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class MoveOrderingTest {
    @Test
    fun `orders captures first, for human player`() {
        val actual = mutableListOf(NEUTRAL, BIG_CAPTURE, CAPTURE).orderMoves(-1)
        val expected = arrayOf(BIG_CAPTURE, CAPTURE, NEUTRAL)

        assertArrayEquals(expected, actual.toTypedArray())
    }

    @Test
    fun `orders captures first, for AI player`() {
        val actual = mutableListOf(NEUTRAL, BIG_CAPTURE_HUMAN, CAPTURE_HUMAN).orderMoves(1)
        val expected = arrayOf(BIG_CAPTURE_HUMAN, CAPTURE_HUMAN, NEUTRAL)

        assertArrayEquals(expected, actual.toTypedArray())
    }

    @Test
    fun `self captures are on par with non-captures, the order of those two categories of moves doesn't alter`() {
        val actual = mutableListOf(CAPTURE_HUMAN, NEUTRAL, BIG_CAPTURE, CAPTURE).orderMoves(-1)
        val expected = arrayOf(BIG_CAPTURE, CAPTURE, CAPTURE_HUMAN, NEUTRAL)

        assertArrayEquals(expected, actual.toTypedArray())
    }

    companion object {
        val NEUTRAL = Move(-4, 1, 1, 2, 2, 0)
        val CAPTURE = Move(-4, 1, 2, 2, 3, 1)
        val BIG_CAPTURE = Move(-4, 1, 2, 2, 3, 4)
        val CAPTURE_HUMAN = Move(4, 1, 2, 2, 3, -1)
        val BIG_CAPTURE_HUMAN = Move(4, 1, 2, 2, 3, -4)
    }
}