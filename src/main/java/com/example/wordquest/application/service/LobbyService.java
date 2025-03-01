package com.example.wordquest.application.service;

import com.example.wordquest.application.dto.lobby.LobbyCreateDTO;
import com.example.wordquest.application.dto.lobby.LobbyReadDTO;
import com.example.wordquest.application.dto.lobby.LobbyUpdateDTO;
import java.util.Optional;


public interface LobbyService extends BaseService<
        LobbyReadDTO,
        LobbyCreateDTO,
        LobbyUpdateDTO
    > {
    Optional<LobbyReadDTO> findLobbyByName(String name);
}
