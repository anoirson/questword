package com.example.wordquest.application.dto.round;

import com.example.wordquest.application.dto.base.BaseUpdateDTO;
import io.micrometer.common.lang.Nullable;

public class RoundUpdateDTO extends BaseUpdateDTO {

    @Nullable
    private String gameId; // optional

    @Nullable
    private Integer roundNumber;

    @Nullable
    private String drawerId; // optional

    @Nullable
    private String word;

    public RoundUpdateDTO() {
    }

    public RoundUpdateDTO(String gameId, Integer roundNumber, String drawerId, String word) {
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.drawerId = drawerId;
        this.word = word;
    }

    public String getGameId() {
        return gameId;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public String getDrawerId() {
        return drawerId;
    }

    public String getWord() {
        return word;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setDrawerId(String drawerId) {
        this.drawerId = drawerId;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
