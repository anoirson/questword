package com.example.wordquest.wordquest.domain.repositories;

import com.example.wordquest.domain.model.*;
import com.example.wordquest.domain.repository.*;
import com.example.wordquest.domain.valueObjects.EmailVO;
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
class RoundRepositoryTest {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LobbyRepository lobbyRepository;

    private UUID gameId;
    private Round savedRound;

    @BeforeEach
    void setUp() {
        // Cleanup repositories
        roundRepository.deleteAll();
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        lobbyRepository.deleteAll();

        // Create & Save a Host Player
        Player host = new Player("hostUser", new EmailVO("locah@gmail.com"));
        host = playerRepository.save(host);

        // Create & Save a Lobby
        Lobby lobby = new Lobby();
        lobby.setName("Test Lobby");
        lobby.setHost(host);
        lobby = lobbyRepository.save(lobby);

        // Create & Save a Game referencing the Lobby
        Game game = new Game();
        game.setLobby(lobby);
        game.setStatus(GameStatus.WAITING);
        game = gameRepository.save(game);
        this.gameId = game.getId(); // Store for later tests

        // Create & Save Rounds referencing the Game
        Round round1 = new Round(game, 1, host, "apple");
        roundRepository.save(round1);

        Round round2 = new Round(game, 2, host, "banana");
        roundRepository.save(round2);

        // Store one round for individual lookup tests
        savedRound = round1;
    }

    @Test
    void testFindById_ShouldReturnRound() {
        // Act
        Optional<Round> found = roundRepository.findById(savedRound.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("apple", found.get().getWord());
    }

    @Test
    void testDelete_ShouldRemoveRound() {
        // Act
        roundRepository.delete(savedRound);
        Optional<Round> found = roundRepository.findById(savedRound.getId());

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    void findAllByGameId_ShouldReturnRounds() {
        // Act
        List<Round> rounds = roundRepository.findAllByGameId(gameId);

        // Assert
        assertNotNull(rounds);
        assertEquals(2, rounds.size());
        assertEquals(1, rounds.get(0).getRoundNumber());
        assertEquals(2, rounds.get(1).getRoundNumber());
    }
}
