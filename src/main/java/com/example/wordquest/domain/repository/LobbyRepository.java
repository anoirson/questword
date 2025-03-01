package com.example.wordquest.domain.repository;

import com.example.wordquest.domain.model.Lobby;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;


@Repository
public interface LobbyRepository extends BaseRepository<Lobby, UUID> {
    Optional<Lobby> findByName(String name);
}