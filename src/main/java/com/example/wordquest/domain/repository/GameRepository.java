package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Game;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends BaseRepository<Game, UUID> {
    List<Game> findAllByLobbyId(UUID lobbyId);
}
