package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.*
import com.github.pksokolowski.gameserver.engine.EngineQuery
import com.github.pksokolowski.gameserver.engine.search.pickBestMoveFrom
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@RestController
class PerfTestController {

    @GetMapping("/perf/{depth}")
    fun basicTest(@PathVariable("depth") depth: Int): String {
        val starter = startGame()
        val move = starter.possibleMoves[0]
        val query = EngineQuery(starter.state, move, depthAllowed = depth)

        val time = measureTimeMillis {
            val response = play(query)
        }

        return "Finding the best move with depth = <b>$depth</b> from initial game state took $time ms"
    }

    @GetMapping("/perfDeep/{depth}")
    fun deeperInGameTest(@PathVariable("depth") depth: Int): String {
        val state = """
            00 00 -1 00 00 00 00 00
            -1 -1 00 00 -3 -2 -5 -4
            00 00 -2 00 00 00 +3 00
            00 -2 00 -3 +1 +3 00 +2
            00 +3 00 00 +2 +1 00 00
            00 00 00 00 00 +4 00 00
            +1 00 00 00 00 00 00 00
            00 +1 00 00 00 00 00 00
        """.toGameState(10)

        val move = Move(4, 4, 4, 5, 0)
        val query = EngineQuery(state, move, depthAllowed = depth)

        val time = measureTimeMillis {
            val response = play(query)
        }

        return "Finding the best move with depth = <b>$depth</b> from mid game state took $time ms"
    }

    @GetMapping("/perfDense/{depth}")
    fun deeperInGameTest2(@PathVariable("depth") depth: Int): String {
        val state = """
            +5 00 -1 00 00 +5 00 +2
            -3 -3 00 00 -3 -2 -5 -4
            00 00 -2 -3 00 -2 +3 00
            00 -2 00 -3 +1 +3 00 +2
            00 +3 00 +3 +2 +1 00 00
            00 00 00 00 00 +4 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
        """.toGameState(10)

        val move = Move(4, 4, 4, 5, 0)
        val query = EngineQuery(state, move, depthAllowed = depth)

        val time = measureTimeMillis {
            val response = play(query)
        }

        return "Finding the best move with depth = <b>$depth</b> from a dense mid game state took $time ms"
    }

    @GetMapping("/perfFar/{depth}")
    fun deeperInGameTest3(@PathVariable("depth") depth: Int): String {
        val state = """
            00 00 00 00 00 00 00 00
            00 00 -2 00 00 -2 +3 00
            -4 -2 -1 -3 -2 -2 -2 -5
            00 -2 00 00 +1 00 00 +2
            00 00 +3 00 00 00 00 00
            00 +1 +1 00 +3 00 +4 +3
            00 00 +3 00 00 +4 00 00
            00 00 00 00 00 00 00 00
        """.toGameState(10)

        val move = Move(4, 4, 5, 5, -2)
        val query = EngineQuery(state, move, depthAllowed = depth)

        val time = measureTimeMillis {
            val response = play(query)
        }

        return "Finding the best move with depth = <b>$depth</b> from a dense mid game state took $time ms"
    }
}