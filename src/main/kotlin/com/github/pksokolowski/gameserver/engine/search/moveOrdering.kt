package com.github.pksokolowski.gameserver.engine.search

import com.github.pksokolowski.gameserver.engine.Move

fun List<Move>.orderMoves(player: Int): List<Move> {
    return sortedByDescending { -player * it.capture }
}