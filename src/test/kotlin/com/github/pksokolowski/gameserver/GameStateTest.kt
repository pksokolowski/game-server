package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.search.evaluate
import com.github.pksokolowski.gameserver.engine.Move
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.junit.Assert.*
import org.junit.Test

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
        val move = Move(3, 1, 1, 2, 2, 0)
        state.applyMove(move)

        assertEquals(3, state[2, 2])
        assertEquals(0, state[1, 1])
    }

    @Test
    fun `applying a move doesn't exceed max meaningful energy`() {
        val matrix = makeMatrix()
        matrix[1][1] = 3
        matrix[2][1] = -2
        val state = GameState(matrix)
        val move = Move(3, 1, 1, 2, 1, -2)
        state.applyMove(move)

        assertEquals(4, state[2, 1])
    }

    @Test
    fun `undoing a move works`() {
        val matrix = makeMatrix()
        matrix[1][1] = 5
        val state = GameState(matrix)

        val move = Move(2, 0, 0, 1, 1, -3)
        state.undoMove(move)

        assertEquals("failed to back the piece off", 2, state[0, 0])
        assertEquals("failed to restore captured piece", -3, state[1, 1])
    }

    @Test
    fun `undoing a move leaves no traces behind`() {
        val state = """
            00 -1
            +1 00
        """.toGameState()
        val move = Move(1, 0, 0, 1, 1, -1)

        val evaluationBefore = evaluate(state)
        state.applyMove(move)
        state.undoMove(move)
        val evaluationAfter = evaluate(state)

        assertEquals(evaluationBefore, evaluationAfter)
    }

    @Test
    fun `undoing a move leaves no traces behind with the minus player capturing`() {
        val state = """
            00 -1
            +1 00
        """.toGameState(1)
        val move = Move(-1, 1, 1, 0, 0, 1)

        val evaluationBefore = evaluate(state)
        state.applyMove(move)
        state.undoMove(move)
        val evaluationAfter = evaluate(state)

        assertEquals(evaluationBefore, evaluationAfter)
    }

    @Test
    fun `undoing a self_capture move leaves no traces behind`() {
        val state = """
            00 +1
            +1 00
        """.toGameState()
        val move = Move(1, 0, 0, 1, 1, 1)

        val evaluationBefore = evaluate(state)
        state.applyMove(move)
        state.undoMove(move)
        val evaluationAfter = evaluate(state)

        assertEquals(evaluationBefore, evaluationAfter)
    }

    @Test
    fun `undoing a self_capture move leaves no traces behind with the minus player`() {
        val state = """
            00 -1
            -1 00
        """.toGameState()
        val move = Move(-1, 1, 1, 0, 0, -1)

        val evaluationBefore = evaluate(state)
        state.applyMove(move)
        state.undoMove(move)
        val evaluationAfter = evaluate(state)

        assertEquals(evaluationBefore, evaluationAfter)
    }

    @Test
    fun `copy() does a deep copy`() {
        val originalState = """
            00 +1 +1 +1
            00 -1 -1 -1
        """.toGameState(3)
        val copy = originalState.copy()

        val move = Move(-1, 1, 0, 2, 1, 1)
        copy.applyMove(move)

        assertEquals(-1, originalState[1, 0])
    }

    private fun prepareState(width: Int = 8, height: Int = width): GameState {
        val matrix = makeMatrix(width, height)
        return GameState(matrix)
    }
}