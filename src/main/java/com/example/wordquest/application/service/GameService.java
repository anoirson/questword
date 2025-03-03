// GameService Interface
package com.example.wordquest.application.service;

import java.util.List;
import java.util.UUID;

import com.example.wordquest.application.dto.game.GameCreateDTO;
import com.example.wordquest.application.dto.game.GameReadDTO;
import com.example.wordquest.application.dto.game.GameUpdateDTO;

public interface GameService extends BaseService<GameReadDTO, GameCreateDTO, GameUpdateDTO> {
    List<GameReadDTO> findAllByLobbyId(UUID lobbyId);
}
