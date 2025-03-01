package com.example.wordquest.application.factory.impl;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.factory.GameDTOFactory;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.repository.LobbyRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class GameDTOFactoryImpl implements GameDTOFactory {

    private final LobbyRepository lobbyRepository;

    public GameDTOFactoryImpl(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    @Override
    public Game createEntity(GameCreateDTO createDto) {
        Lobby lobby = lobbyRepository.findById(UUID.fromString(createDto.getLobbyId()))
                .orElseThrow(() -> new NoSuchElementException("Lobby not found"));
        return new Game(lobby, createDto.getStatus());
    }

    @Override
    public GameReadDTO createReadDto(Game entity) {
        return new GameReadDTO(
                entity.getId(),
                entity.getStatus(),
                entity.getLobby().getId());
    }

    @Override
    public Game updateEntity(Game existingEntity, GameUpdateDTO updateDto) {
        if (updateDto.getStatus() != null) {
            existingEntity.setStatus(updateDto.getStatus());
        }
        if (updateDto.getLobbyId() != null) {
            Lobby newLobby = lobbyRepository.findById(UUID.fromString(updateDto.getLobbyId()))
                    .orElseThrow(() -> new NoSuchElementException("Lobby not found"));
            existingEntity.setLobby(newLobby);
        }
        return existingEntity;
    }
}
