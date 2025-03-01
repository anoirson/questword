// GameServiceImpl
package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.factory.GameDTOFactory;
import com.example.wordquest.application.service.GameService;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.domain.repository.GameRepository;

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
}
