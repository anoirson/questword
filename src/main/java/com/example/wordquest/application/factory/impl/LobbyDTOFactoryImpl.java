package com.example.wordquest.application.factory.impl;

import com.example.wordquest.application.dto.lobby.*;
import com.example.wordquest.application.factory.LobbyDTOFactory;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Component;


@Component
public class LobbyDTOFactoryImpl implements LobbyDTOFactory {

    private final PlayerRepository playerRepository;

    public LobbyDTOFactoryImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Lobby createEntity(LobbyCreateDTO createDto) {
        // Find host by ID
        Player host = playerRepository.findById(UUID.fromString(createDto.getHostId()))
                .orElseThrow(() -> new NoSuchElementException("Host not found"));
        // Construct the Lobby
        return new Lobby(createDto.getName(), host);
    }

    @Override
    public LobbyReadDTO createReadDto(Lobby entity) {
        return new LobbyReadDTO(
                entity.getId(),
                entity.getName(),
                entity.getHost().getId());
    }

    @Override
    public Lobby updateEntity(Lobby existingEntity, LobbyUpdateDTO updateDto) {
        // Partial update
        if (updateDto.getName() != null) {
            existingEntity.setName(updateDto.getName());
        }
        if (updateDto.getHostId() != null) {
            Player host = playerRepository.findById(UUID.fromString(updateDto.getHostId()))
                    .orElseThrow(() -> new NoSuchElementException("Host not found"));
            existingEntity.setHost(host);
        }
        return existingEntity;
    }
}