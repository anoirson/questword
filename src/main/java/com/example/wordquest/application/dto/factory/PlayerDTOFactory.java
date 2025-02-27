package com.example.wordquest.application.dto.factory;

import org.springframework.stereotype.Component;

import com.example.wordquest.application.dto.PlayerDTO;
import com.example.wordquest.domain.model.Player;

@Component
public class PlayerDTOFactory implements BaseDTOFactory<Player, PlayerDTO> {
    @Override
    public PlayerDTO createDTO(Player entity) {
        return new PlayerDTO(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}