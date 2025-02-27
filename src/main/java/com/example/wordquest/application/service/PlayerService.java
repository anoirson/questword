package com.example.wordquest.application.service;

import java.util.Optional;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.domain.model.Player;

public interface PlayerService extends BaseService<Player, Long>  {
      PlayerDTO getPlayerDTO(Long id);

    // Example of another custom method
    Optional<Player> findByUsername(String username);
    
}
