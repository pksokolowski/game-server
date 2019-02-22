package com.github.pksokolowski.gameserver.engine

fun evaluate(state: GameState): Int {
    var sum = 0
    for (x in 0 until state.width) for (y in 0 until state.height) {
        sum += state[x, y]
    }
    return sum
}

fun evaluateForActivePlayer(state: GameState) = evaluate(state) * state.playerActive