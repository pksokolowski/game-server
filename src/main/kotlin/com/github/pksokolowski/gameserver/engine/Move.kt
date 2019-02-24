package com.github.pksokolowski.gameserver.engine

data class Move(val x1: Int,
                val y1: Int,
                val x2: Int,
                val y2: Int,
                val capture: Int){
    override fun toString() = "($x1, $y1) >> ($x2, $y2) capturing: $capture"
}