package com.github.pksokolowski.gameserver.engine.search

import com.github.pksokolowski.gameserver.engine.Move
import kotlin.math.max
import kotlin.math.min

private const val MAX_ENERGY = 4

fun MutableList<Move>.orderMoves(player: Int): MutableList<Move> {
    return intSort(player)
}

private fun MutableList<Move>.intSort(player: Int): MutableList<Move> {
    val captures = Array<MutableList<Move>?>(MAX_ENERGY * 2 + 1) { null }
    forEach {
        val priority = -player * it.capture
        val assignedIndex = priority.bound(0, MAX_ENERGY)

        if (captures[assignedIndex] == null) captures[assignedIndex] = mutableListOf()
        captures[assignedIndex]?.add(it)
    }

    clear()
    for (i in captures.lastIndex downTo 0) {
        val level = captures[i]
        if (level != null) {
            addAll(level)
        }
    }

    return this
}

private fun Int.bound(minValue: Int, maxValue: Int) = max(minValue, min(maxValue, this))