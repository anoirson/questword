package com.example.wordquest.wordquest.application.tests.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.wordquest.application.dto.game.GameCreateDTO;
import com.example.wordquest.application.dto.game.GameReadDTO;
import com.example.wordquest.application.dto.game.GameUpdateDTO;
import com.example.wordquest.application.factory.GameDTOFactory;
import com.example.wordquest.application.factory.impl.GameDTOFactoryImpl;
import com.example.wordquest.domain.model.Game;
import com.example.wordquest.domain.model.Lobby;
import com.example.wordquest.domain.repository.LobbyRepository;
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
class GameDTOFactoryTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private GameDTOFactoryImpl gameDTOFactory;

    private Lobby testLobby;

    @BeforeEach
    void setUp() {
        testLobby = new Lobby("LobbyName", null); // or pass a Player host
        testLobby.setId(UUID.randomUUID());
    }

    @Test
    void testCreateEntity_Success() {
        // given
        GameCreateDTO createDTO = new GameCreateDTO(testLobby.getId().toString(), GameStatus.WAITING);

        when(lobbyRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(testLobby));

        // when
        Game game = gameDTOFactory.createEntity(createDTO);

        // then
        assertNotNull(game);
        assertEquals(testLobby, game.getLobby());
        assertEquals(GameStatus.WAITING, game.getStatus());
    }

    @Test
    void testCreateEntity_LobbyNotFound() {
        // given
        GameCreateDTO createDTO = new GameCreateDTO(UUID.randomUUID().toString(), GameStatus.WAITING);
        when(lobbyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class,
                () -> gameDTOFactory.createEntity(createDTO),
                "Expected NoSuchElementException for unknown lobby");
    }

    @Test
    void testCreateReadDto() {
        // given
        Game game = new Game(testLobby, GameStatus.ONGOING);
        game.setId(UUID.randomUUID());

        // when
        GameReadDTO readDTO = gameDTOFactory.createReadDto(game);

        // then
        assertNotNull(readDTO);
        assertEquals(game.getId(), readDTO.getId());
        assertEquals(GameStatus.ONGOING, readDTO.getStatus());
        assertEquals(testLobby.getId(), readDTO.getLobbyId());
    }

    @Test
    void testUpdateEntity() {
        // given
        Game game = new Game(testLobby, GameStatus.WAITING);
        game.setId(UUID.randomUUID());

        Lobby newLobby = new Lobby("NewLobby", null);
        newLobby.setId(UUID.randomUUID());

        when(lobbyRepository.findById(newLobby.getId())).thenReturn(Optional.of(newLobby));

        GameUpdateDTO updateDTO = new GameUpdateDTO(GameStatus.FINISHED, newLobby.getId().toString());

        // when
        Game updatedGame = gameDTOFactory.updateEntity(game, updateDTO);

        // then
        assertEquals(GameStatus.FINISHED, updatedGame.getStatus());
        assertEquals(newLobby, updatedGame.getLobby());
    }
}
