// GameReadDTO
package com.example.wordquest.application.dto.game;

import com.example.wordquest.application.dto.base.BaseReadDTO;
import com.example.wordquest.domain.valueObjects.GameStatus;

import java.util.UUID;

public class GameReadDTO extends BaseReadDTO {
    private GameStatus status;
    private UUID lobbyId;

    public GameReadDTO(UUID id, GameStatus status, UUID lobbyId) {
        this.id = id;
        this.status = status;
        this.lobbyId = lobbyId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }
}
