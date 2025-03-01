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
import java.util.Optional;

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

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
        lobbyRepository.deleteAll();

        // create a Lobby
        Lobby lobby = new Lobby("LobbyForGame", null);
        savedLobby = lobbyRepository.save(lobby);

        // create a Game
        Game game = new Game(savedLobby, GameStatus.WAITING);
        savedGame = gameRepository.save(game);
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
}
