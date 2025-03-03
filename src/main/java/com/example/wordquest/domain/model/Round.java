package com.example.wordquest.domain.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rounds")
public class Round extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "round_number", nullable = false)
    private int roundNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drawer_player_id", nullable = false)
    private Player drawer;

    @Column(nullable = false)
    private String word;

    public Round() {
        // JPA only
    }

    public Round(Game game, int roundNumber, Player drawer, String word) {
        this.game = game;
        this.roundNumber = roundNumber;
        this.drawer = drawer;
        this.word = word;
    }

    public Game getGame() {
        return game;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Player getDrawer() {
        return drawer;
    }

    public String getWord() {
        return word;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setDrawer(Player drawer) {
        this.drawer = drawer;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Round [roundNumber=" + roundNumber + ", word=" + word +
                ", drawer=" + (drawer != null ? drawer.getUsername() : "null") + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), game, roundNumber, drawer, word);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Round other))
            return false;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(game, other.game)
                && roundNumber == other.roundNumber
                && Objects.equals(drawer, other.drawer)
                && Objects.equals(word, other.word);
    }
}
