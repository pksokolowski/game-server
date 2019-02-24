package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.EngineQuery
import com.github.pksokolowski.gameserver.engine.play
import com.github.pksokolowski.gameserver.engine.startGame
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @GetMapping("/start")
    fun getBeginningState() = startGame()

    @PostMapping("/play")
    fun continueGame(@RequestParam engineQuery: EngineQuery) = play(engineQuery)
}