package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import com.github.pksokolowski.gameserver.engine.negamax
import com.github.pksokolowski.gameserver.engine.utils.makeMatrix
import org.junit.Assert.assertEquals
import org.junit.Test

class NegamaxTest{
    @Test
    fun `basic case works`(){
        val matrix = makeMatrix(3, 3).apply {
            this[0][1] = -1
            this[1][0] = 3
        }
        val state = GameState(matrix, 0)
        val score = negamax(state, 1)
        assertEquals(4, score)
    }
}