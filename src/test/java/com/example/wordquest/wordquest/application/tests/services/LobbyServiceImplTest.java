package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.lobby.*;
import com.example.wordquest.application.factory.LobbyDTOFactory;
import com.example.wordquest.application.service.impl.LobbyServiceImpl;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.repository.LobbyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class LobbyServiceImplTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private LobbyDTOFactory lobbyDTOFactory;

    @InjectMocks
    private LobbyServiceImpl lobbyService;

    private LobbyCreateDTO createDTO;
    private LobbyReadDTO readDTO;
    private Lobby lobbyEntity;

    @BeforeEach
    void setUp() {
        createDTO = new LobbyCreateDTO("LobbyName", UUID.randomUUID().toString());
        lobbyEntity = new Lobby("LobbyName", null); // or pass a Player
        readDTO = new LobbyReadDTO(UUID.randomUUID(), "LobbyName", null);
    }

    @Test
    void save_Success() {
        when(lobbyDTOFactory.createEntity(createDTO)).thenReturn(lobbyEntity);
        when(lobbyRepository.save(lobbyEntity)).thenReturn(lobbyEntity);
        when(lobbyDTOFactory.createReadDto(lobbyEntity)).thenReturn(readDTO);

        LobbyReadDTO result = lobbyService.save(createDTO);

        assertNotNull(result);
        assertEquals("LobbyName", result.getName());
        verify(lobbyRepository).save(lobbyEntity);
    }

    @Test
    void findById_Success() {
        UUID id = UUID.randomUUID();
        when(lobbyRepository.findById(id)).thenReturn(Optional.of(lobbyEntity));
        when(lobbyDTOFactory.createReadDto(lobbyEntity)).thenReturn(readDTO);

        Optional<LobbyReadDTO> result = lobbyService.findById(id);

        assertTrue(result.isPresent());
        assertEquals("LobbyName", result.get().getName());
    }

    @Test
    void findById_NotFound() {
        UUID id = UUID.randomUUID();
        when(lobbyRepository.findById(id)).thenReturn(Optional.empty());

        Optional<LobbyReadDTO> result = lobbyService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteById_Success() {
        UUID id = UUID.randomUUID();
        when(lobbyRepository.findById(id)).thenReturn(Optional.of(lobbyEntity));

        lobbyService.deleteById(id);

        verify(lobbyRepository).delete(lobbyEntity);
    }

    @Test
    void update_Success() {
        UUID id = UUID.randomUUID();
        LobbyUpdateDTO updateDTO = new LobbyUpdateDTO("NewLobbyName", null);

        when(lobbyRepository.findById(id)).thenReturn(Optional.of(lobbyEntity));
        when(lobbyDTOFactory.updateEntity(lobbyEntity, updateDTO)).thenReturn(lobbyEntity);
        when(lobbyRepository.save(lobbyEntity)).thenReturn(lobbyEntity);
        when(lobbyDTOFactory.createReadDto(lobbyEntity)).thenReturn(readDTO);

        LobbyReadDTO updated = lobbyService.update(id, updateDTO);

        assertEquals("LobbyName", updated.getName()); // from readDTO
        verify(lobbyRepository).save(lobbyEntity);
    }
}
