package com.example.wordquest.application.factory;

import com.example.wordquest.application.dto.player.PlayerCreateDTO;
import com.example.wordquest.application.dto.player.PlayerReadDTO;
import com.example.wordquest.application.dto.player.PlayerUpdateDTO;
import com.example.wordquest.domain.model.Player;


public interface PlayerDTOFactory extends BaseDTOFactory<Player, PlayerReadDTO, PlayerCreateDTO, PlayerUpdateDTO> {

}