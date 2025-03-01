package com.example.wordquest.wordquest.application.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.game.*;
import com.example.wordquest.application.factory.GameDTOFactory;
import com.example.wordquest.application.service.impl.GameServiceImpl;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.domain.repository.GameRepository;
import com.example.wordquest.domain.valueObjects.GameStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameDTOFactory gameDTOFactory;

    @InjectMocks
    private GameServiceImpl gameService;

    private GameCreateDTO createDTO;
    private GameReadDTO readDTO;
    private Game gameEntity;

    @BeforeEach
    void setUp() {
        createDTO = new GameCreateDTO(UUID.randomUUID().toString(), GameStatus.WAITING);
        gameEntity = new Game(null, GameStatus.WAITING);
        gameEntity.setId(UUID.randomUUID());

        readDTO = new GameReadDTO(gameEntity.getId(), GameStatus.WAITING, null);
    }

    @Test
    void save_Success() {
        when(gameDTOFactory.createEntity(createDTO)).thenReturn(gameEntity);
        when(gameRepository.save(gameEntity)).thenReturn(gameEntity);
        when(gameDTOFactory.createReadDto(gameEntity)).thenReturn(readDTO);

        GameReadDTO result = gameService.save(createDTO);

        assertNotNull(result);
        assertEquals(GameStatus.WAITING, result.getStatus());
        verify(gameRepository).save(gameEntity);
    }

    @Test
    void findById_Success() {
        when(gameRepository.findById(gameEntity.getId())).thenReturn(Optional.of(gameEntity));
        when(gameDTOFactory.createReadDto(gameEntity)).thenReturn(readDTO);

        Optional<GameReadDTO> found = gameService.findById(gameEntity.getId());

        assertTrue(found.isPresent());
        assertEquals(GameStatus.WAITING, found.get().getStatus());
    }

    @Test
    void findById_NotFound() {
        UUID unknownId = UUID.randomUUID();
        when(gameRepository.findById(unknownId)).thenReturn(Optional.empty());

        Optional<GameReadDTO> found = gameService.findById(unknownId);

        assertFalse(found.isPresent());
    }

    @Test
    void deleteById_Success() {
        when(gameRepository.findById(gameEntity.getId())).thenReturn(Optional.of(gameEntity));

        gameService.deleteById(gameEntity.getId());
        verify(gameRepository).delete(gameEntity);
    }

    @Test
    void update_Success() {
        GameUpdateDTO updateDTO = new GameUpdateDTO(GameStatus.FINISHED, null);

        when(gameRepository.findById(gameEntity.getId())).thenReturn(Optional.of(gameEntity));
        when(gameDTOFactory.updateEntity(gameEntity, updateDTO)).thenReturn(gameEntity);
        when(gameRepository.save(gameEntity)).thenReturn(gameEntity);
        when(gameDTOFactory.createReadDto(gameEntity)).thenReturn(readDTO);

        GameReadDTO updated = gameService.update(gameEntity.getId(), updateDTO);
        assertEquals(GameStatus.WAITING, updated.getStatus(), "ReadDTO has WAITING? Adjust if needed.");
    }
}
