package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.*
import com.github.pksokolowski.gameserver.engine.EngineQuery
import com.github.pksokolowski.gameserver.engine.search.pickBestMoveFrom
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

    @GetMapping("/perfP/{depth}")
    fun basicTestP(@PathVariable("depth") depth: Int): String {
        val starter = startGame()
        val move = starter.possibleMoves[0]
        val query = EngineQuery(starter.state, move, depthAllowed = depth)

        starter.state.applyMove(move)
        val time = measureTimeMillis {
            val response = pickBestMoveFrom(starter.state, depth)
        }

        return "[Pick best move only] Finding the best move with depth = <b>$depth</b> from initial game state took $time ms"
    }
}