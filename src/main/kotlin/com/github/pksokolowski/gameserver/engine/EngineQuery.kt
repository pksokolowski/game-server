package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.motion.Move

class EngineQuery(val state: GameState,
                  val chosenMove: Move,
                  val timeAllowed: Long = 1000,
                  val depthAllowed: Int = Int.MAX_VALUE)