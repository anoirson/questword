package com.example.wordquest.presentation.controller;

import com.example.wordquest.application.dto.lobby.LobbyCreateDTO;
import com.example.wordquest.application.dto.lobby.LobbyReadDTO;
import com.example.wordquest.application.dto.lobby.LobbyUpdateDTO;
import org.springframework.http.ResponseEntity;


public interface LobbyController
    extends BaseController<LobbyReadDTO, LobbyCreateDTO, LobbyUpdateDTO> {
    
    ResponseEntity<LobbyReadDTO> findLobbyByName(String name);
}
