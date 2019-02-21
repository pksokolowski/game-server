package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.Move
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import java.awt.Point

class GameStateTest {
    @Test
    fun `can access fields of the board`() {
        val matrix = makeMatrix(5, 5)
        matrix[0][0] = 1
        matrix[1][1] = 2

        val state = GameState(matrix)
        val retrievedValue = state[0, 0]

        assertEquals(1, retrievedValue)
    }

    @Test
    fun `can access the last element in the last row`() {
        val matrix = makeMatrix(5, 5)
        matrix[4][4] = 10

        val state = GameState(matrix)
        val retrievedValue = state[4, 4]

        assertEquals(10, retrievedValue)
    }

    @Test
    fun `crashes on out of range cases`() {
        val state = prepareState(3, 3)
        try {
            val retrievedValue = state[3, 3]
            fail("didn't crash despite assigning to an index outside bounds of array")
        } catch (e: Exception) {
        }
    }

    @Test
    fun `returns correct width`() {
        val state = prepareState(5, 6)
        assertEquals(5, state.width)
    }

    @Test
    fun `returns 0 width when it is zero indeed`() {
        val state = prepareState(0, 3)
        assertEquals(0, state.width)
    }

    @Test
    fun `returns correct height`() {
        val state = prepareState(5, 6)
        assertEquals(6, state.height)
    }

    @Test
    fun `applying a move works`() {
        val matrix = makeMatrix()
        matrix[1][1] = 3
        val state = GameState(matrix)
        val move = Move(1, 1, 2, 2, 0)
        state.applyMove(move)

        assertEquals(3, state[2, 2])
        assertEquals(0, state[1, 1])
    }

    @Test
    fun `undoing a move works`() {
        val matrix = makeMatrix()
        matrix[1][1] = 5
        val state = GameState(matrix)

        val move = Move(0, 0, 1, 1, -3)
        state.undoMove(move)

        assertEquals("failed to back the piece off", 2, state[0, 0])
        assertEquals("failed to restore captured piece", -3, state[1, 1])
    }

    private fun prepareState(width: Int = 8, height: Int = width): GameState {
        val matrix = makeMatrix(width, height)
        return GameState(matrix)
    }
}