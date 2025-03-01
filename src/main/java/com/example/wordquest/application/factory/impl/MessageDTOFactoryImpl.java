package com.example.wordquest.application.factory.impl;

import com.example.wordquest.application.dto.message.*;
import com.example.wordquest.application.factory.MessageDTOFactory;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.PlayerRepository;
import com.example.wordquest.domain.repository.RoundRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class MessageDTOFactoryImpl implements MessageDTOFactory {

    private final RoundRepository roundRepository;
    private final PlayerRepository playerRepository;

    public MessageDTOFactoryImpl(RoundRepository roundRepository, PlayerRepository playerRepository) {
        this.roundRepository = roundRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Message createEntity(MessageCreateDTO createDto) {
        Round round = roundRepository.findById(UUID.fromString(createDto.getRoundId()))
                .orElseThrow(() -> new NoSuchElementException("Round not found"));
        Player player = playerRepository.findById(UUID.fromString(createDto.getPlayerId()))
                .orElseThrow(() -> new NoSuchElementException("Player not found"));

        return new Message(
                round,
                player,
                createDto.getMessage(),
                createDto.isCorrect());
    }

    @Override
    public MessageReadDTO createReadDto(Message entity) {
        return new MessageReadDTO(
                entity.getId(),
                entity.getRound().getId(),
                entity.getPlayer().getId(),
                entity.getMessage(),
                entity.isCorrect());
    }

    @Override
    public Message updateEntity(Message existingEntity, MessageUpdateDTO updateDto) {
        if (updateDto.getRoundId() != null) {
            Round round = roundRepository.findById(UUID.fromString(updateDto.getRoundId()))
                    .orElseThrow(() -> new NoSuchElementException("Round not found"));
            existingEntity.setRound(round);
        }
        if (updateDto.getPlayerId() != null) {
            Player player = playerRepository.findById(UUID.fromString(updateDto.getPlayerId()))
                    .orElseThrow(() -> new NoSuchElementException("Player not found"));
            existingEntity.setPlayer(player);
        }
        if (updateDto.getMessage() != null) {
            existingEntity.setMessage(updateDto.getMessage());
        }
        if (updateDto.getIsCorrect() != null) {
            existingEntity.setCorrect(updateDto.getIsCorrect());
        }
        return existingEntity;
    }
}
