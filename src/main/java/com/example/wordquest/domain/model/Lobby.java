package com.example.wordquest.domain.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lobbies")
public class Lobby extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_player_id", nullable = false)
    private Player host;

    protected Lobby() {
        // JPA Only
    }

    public Lobby(String name, Player host) {
        this.name = name;
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public Player getHost() {
        return host;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Lobby [name=" + name + ", host=" + host + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), name, host);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Lobby other)) return false;
        return Objects.equals(this.getId(), other.getId())
               && Objects.equals(this.name, other.name)
               && Objects.equals(this.host, other.host);
    }
}
