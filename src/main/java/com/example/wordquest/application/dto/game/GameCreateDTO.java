// GameCreateDTO
package com.example.wordquest.application.dto.game;

import com.example.wordquest.application.dto.base.BaseCreateDTO;
import com.example.wordquest.domain.valueObjects.GameStatus;

public class GameCreateDTO extends BaseCreateDTO {
    private String lobbyId;
    private GameStatus status;

    public GameCreateDTO() {
    }

    public GameCreateDTO(String lobbyId, GameStatus status) {
        this.lobbyId = lobbyId;
        this.status = status;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
