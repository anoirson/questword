// GameController
package com.example.wordquest.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.example.wordquest.application.dto.game.*;

public interface GameController
        extends BaseController<GameReadDTO, GameCreateDTO, GameUpdateDTO> {
    ResponseEntity<List<GameReadDTO>> findByLobbyId(UUID lobbyId);
}
