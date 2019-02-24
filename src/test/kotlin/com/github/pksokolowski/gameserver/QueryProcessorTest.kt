package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.EngineQuery
import com.github.pksokolowski.gameserver.engine.play
import com.github.pksokolowski.gameserver.engine.startGame
import org.junit.Assert.fail
import org.junit.Test

class QueryProcessorTest{
    @Test
    fun `basic flow scenario`(){
        val initialResponse = startGame()
        val chosenMove = initialResponse.possibleMoves[0]

        val secondQuery = EngineQuery(initialResponse.state, chosenMove, depthAllowed = 3)
        val secondResponse = play(secondQuery)

        if(secondResponse.state[chosenMove.x1, chosenMove.y1] != 0) fail()
    }
}