package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import com.example.wordquest.application.factory.PlayerDTOFactory;
import com.example.wordquest.application.service.PlayerService;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl
        extends BaseServiceImpl<Player, PlayerReadDTO, PlayerCreateDTO, PlayerUpdateDTO>
        implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerDTOFactory playerDTOFactory;

    protected PlayerServiceImpl(PlayerRepository repository,
            PlayerDTOFactory factory) {
        super(repository, factory);
        this.playerRepository = repository;
        this.playerDTOFactory = factory;
    }
    @Override
    public PlayerReadDTO save(PlayerCreateDTO createDTO) {
        if (playerRepository.existsByEmailEmailAddress(createDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + createDTO.getEmail());
        }
        if (playerRepository.existsByUsername(createDTO.getUsername())) {
            throw new IllegalArgumentException("Username already in use: " + createDTO.getUsername());
        }
        Player player = playerDTOFactory.createEntity(createDTO);
        return playerDTOFactory.createReadDto(playerRepository.save(player));
    }
    @Override
    public Optional<PlayerReadDTO> findByEmailEmailAddress(String email) {
      Player player = playerRepository.findByEmailEmailAddress(email).orElse(null);
      return Optional.ofNullable(player).map(playerDTOFactory::createReadDto);
    }

    @Override
    public Optional<PlayerReadDTO> findByUsername(String username) {
        Player player = playerRepository.findByUsername(username).orElse(null);
        return Optional.ofNullable(player).map(playerDTOFactory::createReadDto);
    }

}