package com.example.wordquest.domain.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isCorrect = false;

    public Message() {
        // JPA only
    }

    public Message(Round round, Player player, String message, boolean isCorrect) {
        this.round = round;
        this.player = player;
        this.message = message;
        this.isCorrect = isCorrect;
    }

    public Round getRound() {
        return round;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Message [message=" + message
                + ", isCorrect=" + isCorrect
                + ", roundId=" + (round != null ? round.getId() : "null")
                + ", playerId=" + (player != null ? player.getId() : "null") + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), round, player, message, isCorrect);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Message other))
            return false;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(round, other.round)
                && Objects.equals(player, other.player)
                && Objects.equals(message, other.message)
                && isCorrect == other.isCorrect;
    }
}
