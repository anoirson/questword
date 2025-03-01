package com.example.wordquest.application.service;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import java.util.Optional;


public interface PlayerService extends BaseService<PlayerReadDTO, PlayerCreateDTO, PlayerUpdateDTO>  {
    Optional<PlayerReadDTO> findByEmailEmailAddress(String email);
    Optional<PlayerReadDTO> findByUsername(String username);
    
}
