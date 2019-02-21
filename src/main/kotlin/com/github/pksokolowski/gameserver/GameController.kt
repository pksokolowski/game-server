package com.github.pksokolowski.gameserver

import com.github.pksokolowski.gameserver.engine.GameState
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {

    @PostMapping("/")
    fun getBeginningState(
            @RequestParam state: GameState) {

    }
}