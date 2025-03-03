// GameServiceImpl
package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.factory.GameDTOFactory;
import com.example.wordquest.application.service.GameService;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.domain.repository.GameRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl
        extends BaseServiceImpl<Game, GameReadDTO, GameCreateDTO, GameUpdateDTO>
        implements GameService {

    // Not used yet.
    private final GameRepository gameRepository;
    private final GameDTOFactory gameDTOFactory;

    public GameServiceImpl(GameRepository repository, GameDTOFactory factory) {
        super(repository, factory);
        this.gameRepository = repository;
        this.gameDTOFactory = factory;
    }

    @Override
    public List<GameReadDTO> findAllByLobbyId(UUID lobbyId) {
        List<Game> games = gameRepository.findAllByLobbyId(lobbyId);
        return games.stream()
                .map(gameDTOFactory::createReadDto)
                .toList();
    }
}
