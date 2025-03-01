package com.example.wordquest.presentation.controller.impl;

import com.example.wordquest.application.dto.lobby.*;
import com.example.wordquest.application.service.LobbyService;
import com.example.wordquest.presentation.controller.LobbyController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/lobbies")
public class LobbyControllerImpl
        extends BaseControllerImpl<LobbyReadDTO, LobbyCreateDTO, LobbyUpdateDTO>
        implements LobbyController {

    private final LobbyService lobbyService;

    public LobbyControllerImpl(LobbyService lobbyService) {
        super(lobbyService);
        this.lobbyService = lobbyService;
    }

    @Override
    public ResponseEntity<LobbyReadDTO> findLobbyByName(String name) {
        return lobbyService.findLobbyByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
