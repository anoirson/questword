// GameControllerImpl
package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.service.GameService;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.presentation.controller.GameController;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    @GetMapping("/lobby/{lobbyId}")
    public ResponseEntity<List<GameReadDTO>> findByLobbyId(@PathVariable UUID lobbyId) {
        List<GameReadDTO> games = gameService.findAllByLobbyId(lobbyId);
        return ResponseEntity.ok(games);
    }
}
