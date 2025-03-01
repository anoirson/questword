// GameUpdateDTO
package com.example.wordquest.application.dto.game;

import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import com.example.wordquest.domain.valueObjects.GameStatus;

import io.micrometer.common.lang.Nullable;

public class GameUpdateDTO extends BaseUpdateDTO {
    @Nullable
    private GameStatus status;
    @Nullable
    private String lobbyId;

    public GameUpdateDTO() {
    }

    public GameUpdateDTO(GameStatus status, String lobbyId) {
        this.status = status;
        this.lobbyId = lobbyId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }
}
