package com.example.wordquest.domain.model;

import com.example.wordquest.domain.valueObjects.GameStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "lobby_id", nullable = false)
    private Lobby lobby;

    @Enumerated(EnumType.STRING) // Use STRING to store the enum name in the DB
    @Column(nullable = false)
    private GameStatus status;

    protected Game() {
        // JPA Only
    }

    public Game(Lobby lobby, GameStatus status) {
        this.lobby = lobby;
        this.status = status;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}