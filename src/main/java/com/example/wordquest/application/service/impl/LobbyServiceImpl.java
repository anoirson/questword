package com.example.wordquest.application.service.impl;

import com.example.wordquest.application.dto.lobby.*;
import com.example.wordquest.application.factory.LobbyDTOFactory;
import com.example.wordquest.application.service.LobbyService;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.repository.LobbyRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class LobbyServiceImpl
    extends BaseServiceImpl<Lobby, LobbyReadDTO, LobbyCreateDTO, LobbyUpdateDTO>
    implements LobbyService {

    private final LobbyRepository lobbyRepository;
    private final LobbyDTOFactory lobbyDTOFactory;

    public LobbyServiceImpl(LobbyRepository repository, LobbyDTOFactory factory) {
        super(repository, factory);
        this.lobbyRepository = repository;
        this.lobbyDTOFactory = factory;
    }
    @Override
    public Optional<LobbyReadDTO> findLobbyByName(String name) {
       Lobby lobby = lobbyRepository.findByName(name).orElse(null);
       return Optional.ofNullable(lobby).map(lobbyDTOFactory::createReadDto);
    }
}
