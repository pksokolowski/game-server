package com.github.pksokolowski.gameserver.engine

import com.github.pksokolowski.gameserver.engine.motion.Move

class EngineResponse(val state: GameState, val possibleMoves: List<Move>)