package com.github.pksokolowski.gameserver.engine.motion

data class Move(val x1: Int,
                val y1: Int,
                val x2: Int,
                val y2: Int,
                val capture: Int)