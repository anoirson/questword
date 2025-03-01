package com.example.wordquest.application.dto.message;

import com.example.wordquest.application.dto.base.BaseCreateDTO;

public class MessageCreateDTO extends BaseCreateDTO {
    private String roundId;
    private String playerId;
    private String message;
    private boolean isCorrect;

    public MessageCreateDTO() {
    }

    public MessageCreateDTO(String roundId, String playerId, String message, boolean isCorrect) {
        this.roundId = roundId;
        this.playerId = playerId;
        this.message = message;
        this.isCorrect = isCorrect;
    }

    public String getRoundId() {
        return roundId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
