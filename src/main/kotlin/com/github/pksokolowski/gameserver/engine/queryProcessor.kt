package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.search.possibleMovesFrom
import com.github.pksokolowski.gameserver.engine.search.pickBestMoveFrom
import com.github.pksokolowski.gameserver.engine.utils.getInitialGameState

fun play(query: EngineQuery) = with(query){
    state.applyMove(chosenMove)

    // todo consider better way to handle end game - include points and a flag?
    val bestMove = pickBestMoveFrom(state, depthAllowed, timeAllowed) ?: return EngineResponse(state, listOf())
    state.applyMove(bestMove)

    val possibleMoves = possibleMovesFrom(state)
    EngineResponse(state, possibleMoves)
}

fun startGame(): EngineResponse {
    val state = getInitialGameState()
    val possibleMoves = possibleMovesFrom(state)
    return EngineResponse(state, possibleMoves)
}