package com.github.pksokolowski.gameserver.benchmark

import com.github.pksokolowski.gameserver.engine.EngineQuery
import com.github.pksokolowski.gameserver.engine.Move
import com.github.pksokolowski.gameserver.engine.utils.toGameState
import org.springframework.stereotype.Service

@Service
class BenchmarkService {

    val queryPreparers = listOf(
            ::prepareGameStartQuery,
            ::prepareMidGameQuery,
            ::prepareMidGameDenseQuery,
            ::prepareMidGameFarApartQuery,
            ::prepareLateGameQuery,
            ::prepareDispersedLateGameQuery,
            ::prepareBattleOf2sQuery
    )

    class QueryData(val query: EngineQuery, val info: String)

    fun prepareGameStartQuery(depth: Int = 8): QueryData {
        val state = """
            -1 -1 -1 -1 -1 -1 -1 -1
            -1 -1 -1 -1 -1 -1 -1 -1
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            +1 +1 +1 +1 +1 +1 +1 +1
            +1 +1 +1 +1 +1 +1 +1 +1
        """.toGameState()

        val move = Move(4, 1, 4, 2, 0)
        val query = EngineQuery(state, move, depthAllowed = depth)
        return QueryData(query, "[realistic load] Game beginning. (depth = $depth)")
    }

    fun prepareMidGameQuery(depth: Int = 7): QueryData {
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
        return QueryData(query, "Mid-game. (depth = $depth)")
    }

    fun prepareMidGameDenseQuery(depth: Int = 6): QueryData {
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
        return QueryData(query, "Mid game, dense. (depth = $depth)")
    }

    fun prepareMidGameFarApartQuery(depth: Int = 6): QueryData {
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
        return QueryData(query, "Mid game, pieces far apart. (depth = $depth)")
    }

    fun prepareLateGameQuery(depth: Int = 8): QueryData {
        val state = """
            00 00 00 00 00 -4 00 00
            00 00 +2 -4 00 00 -1 00
            00 00 00 -2 00 00 00 -2
            00 +4 00 00 +3 +2 00 00
            00 00 00 +5 00 00 00 -3
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
        """.toGameState(10)

        val move = Move(2, 6, 2, 7, 0)
        val query = EngineQuery(state, move, depthAllowed = depth)
        return QueryData(query, "[realistic load] Late game (depth = $depth)")
    }

    fun prepareDispersedLateGameQuery(depth: Int = 8): QueryData {
        val state = """
            +2 00 00 00 00 -4 00 00
            00 00 00 -1 00 00 00 -2
            00 00 00 00 00 -3 00 00
            -4 00 -2 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 +4 00 00 00 +2 00 00
            00 00 00 +5 00 00 00 00
            00 00 00 00 00 +3 00 00
        """.toGameState(10)

        val move = Move(5, 7, 2, 0, 0)
        val query = EngineQuery(state, move, depthAllowed = depth)
        return QueryData(query, "[realistic load] Late game with pieces dispersed (depth = $depth)")
    }

    fun prepareBattleOf2sQuery(depth: Int = 9): QueryData {
        val state = """
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
            00 00 -2 -2 -2 -2 00 00
            00 00 -2 -2 -2 -2 00 00
            00 00 +2 +2 +2 +2 00 00
            00 00 +2 +2 +2 +2 00 00
            00 00 00 00 00 00 00 00
            00 00 00 00 00 00 00 00
        """.toGameState(20)

        val move = Move(2, 3, 3, 4, -2)
        val query = EngineQuery(state, move, depthAllowed = depth)
        return QueryData(query, "[realistic load] Two rectangles of 2-energy pieces, zero distance (depth = $depth)")
    }
}