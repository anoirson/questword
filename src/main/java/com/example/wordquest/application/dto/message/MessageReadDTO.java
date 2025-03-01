package com.example.wordquest.application.dto.message;

import com.example.wordquest.application.dto.base.BaseReadDTO;
import java.util.UUID;

public class MessageReadDTO extends BaseReadDTO {
    private UUID roundId;
    private UUID playerId;
    private String message;
    private boolean isCorrect;

    public MessageReadDTO(UUID id, UUID roundId, UUID playerId, String message, boolean isCorrect) {
        this.id = id;
        this.roundId = roundId;
        this.playerId = playerId;
        this.message = message;
        this.isCorrect = isCorrect;
    }

    public UUID getRoundId() {
        return roundId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
