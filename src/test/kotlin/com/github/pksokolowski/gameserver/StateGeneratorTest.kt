package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.utils.generateState
import org.junit.Assert.assertEquals
import org.junit.Test

class StateGeneratorTest {
    @Test
    fun `generates basic state`() {
        val state = generateState("""
            00 00 00
            -1 00 00
            00 +3 00
        """.trimIndent(), 0)

        assertEquals(-1, state[0, 1])
        assertEquals(3, state[1, 0])
    }

    @Test
    fun `parses all corners correctly`() {
        val state = generateState("""
            +3 00 +4
            00 00 00
            +1 00 +2
        """.trimIndent(), 0)

        assertEquals(1, state[0, 0])
        assertEquals(2, state[2, 0])
        assertEquals(3, state[0, 2])
        assertEquals(4, state[2, 2])
    }

    @Test
    fun `generates more complex state`() {
        val state = generateState("""
            00 00 00 -4
            -1 00 00 00
            +2 +3 +1 +1
            +1 +2 +3 +4
        """.trimIndent(), 1)
        // row 1
        assertEquals(-4, state[3, 3])
        // row 2
        assertEquals(-1, state[0, 2])
        // row 3
        assertEquals(2, state[0, 1])
        assertEquals(3, state[1, 1])
        assertEquals(1, state[2, 1])
        assertEquals(1, state[3, 1])
        // row 4
        assertEquals(1, state[0, 0])
        assertEquals(2, state[1, 0])
        assertEquals(3, state[2, 0])
        assertEquals(4, state[3, 0])
    }
}