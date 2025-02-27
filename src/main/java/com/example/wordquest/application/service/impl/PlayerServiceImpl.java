package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.application.dto.factory.PlayerDTOFactory;
import com.example.wordquest.application.service.PlayerService;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl extends BaseServiceImpl<Player, Long> implements PlayerService {

    private final PlayerDTOFactory playerDTOFactory;
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository repository, PlayerDTOFactory playerDTOFactory) {
        super(repository);
        this.playerDTOFactory = playerDTOFactory;
        this.playerRepository = repository;
    }

    @Override
    public PlayerDTO getPlayerDTO(Long id) {
        return playerRepository.findById(id)
                .map(playerDTOFactory::createDTO)
                .orElse(null);
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        // Example custom finder
        return playerRepository.findAll()
                .stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }
}