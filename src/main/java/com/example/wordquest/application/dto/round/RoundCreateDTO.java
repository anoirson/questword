package com.example.wordquest.application.dto.round;

import com.example.wordquest.application.dto.base.BaseCreateDTO;

public class RoundCreateDTO extends BaseCreateDTO {
    private String gameId; // we'll parse to UUID
    private int roundNumber;
    private String drawerId; // parse to UUID
    private String word;

    public RoundCreateDTO() {
    }

    public RoundCreateDTO(String gameId, int roundNumber, String drawerId, String word) {
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.drawerId = drawerId;
        this.word = word;
    }

    public String getGameId() {
        return gameId;
    }

    public int getRoundNumber() {
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

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setDrawerId(String drawerId) {
        this.drawerId = drawerId;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
