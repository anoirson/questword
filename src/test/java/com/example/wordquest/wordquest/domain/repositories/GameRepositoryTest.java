package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.GameRepository;
import com.example.wordquest.domain.repository.LobbyRepository;
import com.example.wordquest.domain.valueObjects.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LobbyRepository lobbyRepository;

    private Lobby savedLobby;
    private Game savedGame;
    private UUID lobbyId;
    private UUID game1Id;
    private UUID game2Id;

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
        lobbyRepository.deleteAll();
        lobbyId = UUID.randomUUID();

        // create a Lobby
        Lobby lobby = new Lobby("LobbyForGame", null);
        lobby.setId(lobbyId);
        savedLobby = lobbyRepository.save(lobby);

        // create a Game
        Game game = new Game(savedLobby, GameStatus.WAITING);
        savedGame = gameRepository.save(game);

        Game game1 = new Game(savedLobby, GameStatus.WAITING);
        game1.setId(game1Id);

        Game game2 = new Game(savedLobby, GameStatus.WAITING);
        game2.setId(game2Id);

        gameRepository.save(game1);
        gameRepository.save(game2);
    }

    @Test
    void testFindById() {
        Optional<Game> found = gameRepository.findById(savedGame.getId());
        assertTrue(found.isPresent());
        assertEquals(GameStatus.WAITING, found.get().getStatus());
        assertEquals(savedLobby.getId(), found.get().getLobby().getId());
    }

    @Test
    void testDelete() {
        gameRepository.delete(savedGame);
        Optional<Game> found = gameRepository.findById(savedGame.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void findAllByLobbyId_ShouldReturnGames() {
        // when
        List<Game> games = gameRepository.findAllByLobbyId(lobbyId);

        // then
        assertNotNull(games);
        assertEquals(game1Id, games.get(0).getId());
        assertEquals(game2Id, games.get(1).getId());
    }

}
