// GameControllerImpl
package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.service.GameService;
import com.example.wordquest.presentation.controller.GameController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameControllerImpl
        extends BaseControllerImpl<GameReadDTO, GameCreateDTO, GameUpdateDTO>
        implements GameController {

    private final GameService gameService;

    public GameControllerImpl(GameService gameService) {
        super(gameService);
        this.gameService = gameService;
    }
}
