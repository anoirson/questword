package com.example.wordquest.application.dto.message;

import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import io.micrometer.common.lang.Nullable;

public class MessageUpdateDTO extends BaseUpdateDTO {

    @Nullable
    private String roundId;

    @Nullable
    private String playerId;

    @Nullable
    private String message;

    @Nullable
    private Boolean isCorrect;

    public MessageUpdateDTO() {
    }

    public MessageUpdateDTO(String roundId, String playerId, String message, Boolean isCorrect) {
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

    public Boolean getIsCorrect() {
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

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
