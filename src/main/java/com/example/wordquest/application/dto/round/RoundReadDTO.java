package com.example.wordquest.application.dto.round;

import com.example.wordquest.application.dto.base.BaseReadDTO;
import java.util.UUID;

public class RoundReadDTO extends BaseReadDTO {
    private int roundNumber;
    private String word;
    private UUID gameId;
    private UUID drawerId;

    public RoundReadDTO(UUID id, int roundNumber, String word, UUID gameId, UUID drawerId) {
        this.id = id; // from BaseReadDTO
        this.roundNumber = roundNumber;
        this.word = word;
        this.gameId = gameId;
        this.drawerId = drawerId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getWord() {
        return word;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getDrawerId() {
        return drawerId;
    }
}
