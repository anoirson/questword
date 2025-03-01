package com.example.wordquest.wordquest.application.tests.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wordquest.application.dto.round.*;
import com.example.wordquest.application.factory.impl.RoundDTOFactoryImpl;
import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.GameRepository;
import com.example.wordquest.domain.repository.PlayerRepository;
import com.example.wordquest.domain.valueObjects.GameStatus;

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
class RoundDTOFactoryTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private RoundDTOFactoryImpl roundDTOFactory;

    private Game testGame;
    private Player testPlayer;
    private Round testRound;

    @BeforeEach
    void setUp() {
        testGame = new Game(null, GameStatus.WAITING);
        testGame.setId(UUID.randomUUID());

        testPlayer = new Player("drawerPlayer", null);
        testPlayer.setId(UUID.randomUUID());

        testRound = new Round(testGame, 1, testPlayer, "apple");
        testRound.setId(UUID.randomUUID());
    }

    @Test
    void testCreateEntity_Success() {
        RoundCreateDTO createDTO = new RoundCreateDTO(testGame.getId().toString(), 1, testPlayer.getId().toString(),
                "apple");

        when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(testGame));
        when(playerRepository.findById(any(UUID.class))).thenReturn(Optional.of(testPlayer));

        Round round = roundDTOFactory.createEntity(createDTO);

        assertNotNull(round);
        assertEquals(1, round.getRoundNumber());
        assertEquals("apple", round.getWord());
        assertEquals(testGame, round.getGame());
        assertEquals(testPlayer, round.getDrawer());
    }

    @Test
    void testCreateEntity_GameNotFound() {
        RoundCreateDTO createDTO = new RoundCreateDTO(UUID.randomUUID().toString(), 1, testPlayer.getId().toString(),
                "apple");
        when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> roundDTOFactory.createEntity(createDTO));
    }

    @Test
    void testCreateReadDto() {
        RoundReadDTO readDTO = roundDTOFactory.createReadDto(testRound);

        assertNotNull(readDTO);
        assertEquals(testRound.getId(), readDTO.getId());
        assertEquals("apple", readDTO.getWord());
        assertEquals(testRound.getGame().getId(), readDTO.getGameId());
        assertEquals(testRound.getDrawer().getId(), readDTO.getDrawerId());
    }

    @Test
    void testUpdateEntity() {
        RoundUpdateDTO updateDTO = new RoundUpdateDTO(null, 2, null, "banana");

        Round updatedRound = roundDTOFactory.updateEntity(testRound, updateDTO);

        assertNotNull(updatedRound);
        assertEquals(2, updatedRound.getRoundNumber());
        assertEquals("banana", updatedRound.getWord());
    }
}
