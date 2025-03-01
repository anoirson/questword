package com.example.wordquest.wordquest.application.tests.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.wordquest.application.dto.lobby.LobbyCreateDTO;
import com.example.wordquest.application.dto.lobby.LobbyReadDTO;
import com.example.wordquest.application.dto.lobby.LobbyUpdateDTO;
import com.example.wordquest.application.factory.LobbyDTOFactory;
import com.example.wordquest.application.factory.impl.LobbyDTOFactoryImpl;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.model.Player;
import com.example.wordquest.domain.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LobbyDTOFactoryTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private LobbyDTOFactoryImpl lobbyDTOFactory;

    private Player hostPlayer;

    @BeforeEach
    void setUp() {

        hostPlayer = new Player("hostUsername", null);
        hostPlayer.setId(UUID.randomUUID());
    }

    @Test
    void testCreateEntity_Success() {
        // given
        LobbyCreateDTO createDTO = new LobbyCreateDTO("MyLobby", hostPlayer.getId().toString());

        when(playerRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(hostPlayer));

        // when
        Lobby lobby = lobbyDTOFactory.createEntity(createDTO);

        // then
        assertNotNull(lobby);
        assertEquals("MyLobby", lobby.getName());
        assertEquals(hostPlayer, lobby.getHost());
    }

    @Test
    void testCreateEntity_HostNotFound() {
        // given
        LobbyCreateDTO createDTO = new LobbyCreateDTO("MyLobby", UUID.randomUUID().toString());
        when(playerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when & then
        org.junit.jupiter.api.function.Executable executable = () -> lobbyDTOFactory.createEntity(createDTO);
        NoSuchElementException exception = org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchElementException.class, executable);

        assertEquals("Host not found", exception.getMessage());
    }

    @Test
    void testCreateReadDto() {
        // given
        Lobby lobby = new Lobby("MyLobby", hostPlayer);
        lobby.setId(UUID.randomUUID());

        // when
        LobbyReadDTO readDTO = lobbyDTOFactory.createReadDto(lobby);

        // then
        assertNotNull(readDTO);
        assertEquals("MyLobby", readDTO.getName());
        assertEquals(lobby.getId(), readDTO.getId());
        assertEquals(lobby.getHost().getId(), readDTO.getHostId());
    }

    @Test
    void testUpdateEntity() {
        // given
        Lobby lobby = new Lobby("OldLobbyName", hostPlayer);
        LobbyUpdateDTO updateDTO = new LobbyUpdateDTO("NewLobbyName", null);

        // when
        Lobby updatedLobby = lobbyDTOFactory.updateEntity(lobby, updateDTO);

        // then
        assertNotNull(updatedLobby);
        assertEquals("NewLobbyName", updatedLobby.getName());
    }

    @Test
    void testUpdateEntity_ChangeHost() {
        // given
        Player newHost = new Player("newHost", null);
        newHost.setId(UUID.randomUUID());

        Lobby lobby = new Lobby("LobbyName", hostPlayer);
        lobby.setId(UUID.randomUUID());

        LobbyUpdateDTO updateDTO = new LobbyUpdateDTO(null, newHost.getId().toString());

        when(playerRepository.findById(newHost.getId())).thenReturn(Optional.of(newHost));

        // when
        Lobby updatedLobby = lobbyDTOFactory.updateEntity(lobby, updateDTO);

        // then
        assertNotNull(updatedLobby);
        assertEquals("LobbyName", updatedLobby.getName()); // unchanged
        assertEquals(newHost, updatedLobby.getHost());
    }
}
