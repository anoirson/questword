package com.example.wordquest.application.factory.impl;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.factory.RoundDTOFactory;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.GameRepository;
import com.example.wordquest.domain.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class RoundDTOFactoryImpl implements RoundDTOFactory {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public RoundDTOFactoryImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Round createEntity(RoundCreateDTO createDto) {
        Game game = gameRepository.findById(UUID.fromString(createDto.getGameId()))
                .orElseThrow(() -> new NoSuchElementException("Game not found"));

        Player drawer = playerRepository.findById(UUID.fromString(createDto.getDrawerId()))
                .orElseThrow(() -> new NoSuchElementException("Drawer player not found"));

        return new Round(game, createDto.getRoundNumber(), drawer, createDto.getWord());
    }

    @Override
    public RoundReadDTO createReadDto(Round entity) {
        return new RoundReadDTO(
                entity.getId(),
                entity.getRoundNumber(),
                entity.getWord(),
                entity.getGame().getId(),
                entity.getDrawer().getId());
    }

    @Override
    public Round updateEntity(Round existingEntity, RoundUpdateDTO updateDto) {
        // Partial update
        if (updateDto.getGameId() != null) {
            Game game = gameRepository.findById(UUID.fromString(updateDto.getGameId()))
                    .orElseThrow(() -> new NoSuchElementException("Game not found"));
            existingEntity.setGame(game);
        }
        if (updateDto.getRoundNumber() != null) {
            existingEntity.setRoundNumber(updateDto.getRoundNumber());
        }
        if (updateDto.getDrawerId() != null) {
            Player drawer = playerRepository.findById(UUID.fromString(updateDto.getDrawerId()))
                    .orElseThrow(() -> new NoSuchElementException("Drawer player not found"));
            existingEntity.setDrawer(drawer);
        }
        if (updateDto.getWord() != null) {
            existingEntity.setWord(updateDto.getWord());
        }
        return existingEntity;
    }
}
